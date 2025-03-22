package com.spring.minipot.setup.services;

import com.spring.minipot.setup.DTO.LoginUserDTO;
import com.spring.minipot.setup.DTO.RegisterUserDTO;
import com.spring.minipot.setup.DTO.RequestResetPasswordDTO;
import com.spring.minipot.setup.DTO.ResetPasswordDTO;
import com.spring.minipot.setup.exceptions.ExistingAccountException;
import com.spring.minipot.setup.exceptions.NotVerifiedException;
import com.spring.minipot.setup.exceptions.ResetPasswordException;
import com.spring.minipot.setup.exceptions.TokenExpiredException;
import com.spring.minipot.setup.models.Role;
import com.spring.minipot.setup.models.User;
import com.spring.minipot.setup.repositories.RoleRepository;
import com.spring.minipot.setup.repositories.UserRepository;
import com.spring.minipot.setup.responses.LoginResponse;
import com.spring.minipot.setup.security.DatabaseUserDetails;
import com.spring.minipot.setup.utilities.EmailManager;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final EmailManager emailManager;

    public AuthService(UserRepository repository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository,
                       AuthenticationManager authManager,
                       JwtService jwtService,
                       EmailManager emailManager){

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.emailManager = emailManager;
    }


    /*
    PERSONAL TOKEN & REFRESH TOKEN GENERATION
     */
    public String generatePersonalToken(){
        //Generate a random 8 character token in Base64
        SecureRandom secureRandom = new SecureRandom();
        byte [] randomnesses = new byte[6];
        secureRandom.nextBytes(randomnesses);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomnesses);
    }

    public String generateRefreshToken(){
        //Generate a random 8 character token in Base64
        SecureRandom secureRandom = new SecureRandom();
        byte [] randomnesses = new byte[32];
        secureRandom.nextBytes(randomnesses);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomnesses);
    }

    /*
     REGISTER
     */
    public User register(@Valid RegisterUserDTO request){
        // Controllo se l'email è già stata utilizzata
        Optional<User> requestedEmail = repository.findByEmail(request.getEmail());
        if (requestedEmail.isPresent()) {
            throw new ExistingAccountException("Email già associata ad un account.");
        }
        //Registro l'utente.
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); //Encode password with BCrypt
        user.setPersonalToken(generatePersonalToken());
        user.setPTokenCreationDate(LocalDateTime.now());

        //Setting del ruolo
        Role baseRole = roleRepository.findByName("User");
        user.setRole(baseRole);
        repository.save(user);

        //Mando mail di verifica account
        emailManager.sendRegistrationEmail(user,user.getPersonalToken());
        return user;
    }

    /*
    LOGIN
     */
    public LoginResponse login (@Valid LoginUserDTO userDTO){
        //Controllo se l'utente è esistente e verificato, in caso autentico.
        Optional<User> userByEmail = repository.findByEmail(userDTO.getEmail());

        if(userByEmail.isEmpty()){
            throw new UsernameNotFoundException("Utente non trovato.");
        }
        else if (!userByEmail.get().getIsVerified()) {
            throw new NotVerifiedException("Account non verificato. Controlla la tua mail per verificare l'account");
        }
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()
                )
        );
        //Recupero i dati dell'utente a cui emettere il token JWT
        UserDetails userDetails = repository.findByEmail(userDTO.getEmail())
                .map(DatabaseUserDetails::new)
                .orElseThrow();
        String jwtoken =  jwtService.generateToken(userDetails);

        //Costruisco la risposta HTTP
        LoginResponse response = new LoginResponse();
        response.setToken(jwtoken);
        response.setExpiresIn(jwtService.getExpirationTime());

        return response;
    }

    /*
    VERIFY METHOD
    */
    public void verify(String token) {
        //Find User by token, set verified True and its verification date
        Optional<User> user = repository.findByPersonalToken(token);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Utente non trovato.");
        }
        user.get().setIsVerified(true);
        user.get().setVerificationDate(LocalDateTime.now());
        user.get().setPersonalToken(null); //Don't store it on db for security reasons
        repository.save(user.get());
    }

    /*
    RESET PASSWORD
     */
    public void sendResetEmail(RequestResetPasswordDTO requestReset){
        Optional<User> user = repository.findByEmail(requestReset.getEmail());
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Utente non trovato.");
        }
        //Genero un nuovo token personale, lo uso per mandare la mail di reset password
        user.get().setPersonalToken(generatePersonalToken());
        user.get().setPTokenCreationDate(LocalDateTime.now());
        repository.save(user.get());

        emailManager.sendResetPasswordEmail(user.get(),user.get().getPersonalToken());
    }


    public void resetPassword(@Valid ResetPasswordDTO resetPasswordRequest){

        String newPassword = resetPasswordRequest.getNewPassword();
        String confirmPassword = resetPasswordRequest.getConfirmPassword();

        //Controllo se l'utente esiste e le password inserite coincidono
        Optional<User> user = repository.findByPersonalToken(resetPasswordRequest.getToken());
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Utente non trovato");
        }
        if(!confirmPassword.equals(newPassword)){
            throw new ResetPasswordException("Le password non coincidono, ricontrolla per favore.");
        }
        //Controllo se l'url utilizzato per il reset password è scaduto.
        else if (user.get().getPTokenCreationDate().plusMinutes(5).isBefore(LocalDateTime.now()) ) {
            throw new TokenExpiredException("Il link utilizzato è scaduto, richiedi un nuovo reset della password.");
        }

        //Imposto la nuova password con encoding
        user.get().setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        user.get().setPersonalToken(null); //Don't store it on db for security reason
        repository.save(user.get());
    }
}

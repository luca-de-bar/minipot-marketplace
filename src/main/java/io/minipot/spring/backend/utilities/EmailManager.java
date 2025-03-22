package io.minipot.spring.backend.utilities;

import io.minipot.spring.backend.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailManager {

    @Value("${app.url}")
    private String appUrl;
    private final SpringTemplateEngine templateEngine;
    private final SesEmailClient sesEmailClient;

    //Class Constructor
    public EmailManager(SpringTemplateEngine templateEngine,
                        SesEmailClient sesEmailClient){

        this.templateEngine = templateEngine;
        this.sesEmailClient = sesEmailClient;
    }

    public void sendRegistrationEmail(User user, String token){

        String verificationUrl = appUrl + "auth/verify-account/" + token;

        Context templateContext = new Context();
        templateContext.setVariable("name", user.getUsername());
        templateContext.setVariable("verificationUrl", verificationUrl);

        //Invio la mail costruita tramite AWS SES
        String emailContent = templateEngine.process("registration",templateContext);
        sesEmailClient.sendEmail(user.getEmail(), "Verifica il tuo account Crewdox", emailContent);
    }


    public void sendResetPasswordEmail (User user, String token){

        String resetPasswordUrl = appUrl + "auth/reset-password?token=" + token;

        Context templateContext = new Context();
        templateContext.setVariable("name", user.getUsername());
        templateContext.setVariable("resetPasswordUrl", resetPasswordUrl);

        //Invio la mail costruita tramite AWS SES
        String emailContent = templateEngine.process("password-reset",templateContext);
        sesEmailClient.sendEmail(user.getEmail(), "Reset della password CrewDox", emailContent);
    }
}


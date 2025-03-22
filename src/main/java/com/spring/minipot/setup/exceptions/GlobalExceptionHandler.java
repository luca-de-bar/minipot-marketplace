package com.spring.minipot.setup.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
//import io.sentry.Sentry;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;
        //Sentry.captureException(exception); //TODO Uncomment this line whenever you go to production, this help send errors to Sentry

        //STATUS CODE : 409 Already Registered
        if(exception instanceof ExistingAccountException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(409), exception.getMessage());
            errorDetail.setProperty("description", "Already existing account");
        }

        //STATUS CODE : 400 Empty Field
        if(exception instanceof MethodArgumentNotValidException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(400), ((MethodArgumentNotValidException) exception).getFieldError().getDefaultMessage());
            errorDetail.setProperty("description", "Field not filled in correctly");
        }

        //STATUS CODE : 404 Username not found
        if(exception instanceof UsernameNotFoundException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(404), exception.getMessage());
            errorDetail.setProperty("description", "User not found");
        }

        //STATUS CODE : 403 Not Verified Account
        if(exception instanceof NotVerifiedException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Not verified account");
        }

        //STATUS CODE : 400 Password reset not valid
        if(exception instanceof ResetPasswordException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(400), exception.getMessage());
            errorDetail.setProperty("description", "Password reset not valid");
        }

        //STATUS CODE : 401 Token expired
        if(exception instanceof TokenExpiredException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "URL Expired");
        }

        //STATUS CODE : 401 Bad Credentials
        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "Bad Credentials");
            return errorDetail;
        }

        //STATUS CODE : 403 Locked Account
        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");
        }

        //STATUS CODE : 403 Not Authorized
        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");
        }

        //STATUS CODE : 401 Invalid Signature
        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");
        }

        //STATUS CODE : 401 JWT Token Expired
        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");
        }

        //STATUS CODE : 500 AWS SES Error
        if(exception instanceof SesException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Error during SES client usage");
        }

        //STATUS CODE : 500 Internal Server Error
        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unknown internal server error.");
        }

        return errorDetail;
    }
}

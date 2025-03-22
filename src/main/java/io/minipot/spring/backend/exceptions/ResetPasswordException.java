package io.minipot.spring.backend.exceptions;

public class ResetPasswordException extends RuntimeException{

    public ResetPasswordException(String message){
        super(message);
    }
}

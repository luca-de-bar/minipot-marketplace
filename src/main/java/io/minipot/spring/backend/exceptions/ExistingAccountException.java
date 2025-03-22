package io.minipot.spring.backend.exceptions;

public class ExistingAccountException extends RuntimeException{

    public ExistingAccountException (String message){
        super(message);
    }
}
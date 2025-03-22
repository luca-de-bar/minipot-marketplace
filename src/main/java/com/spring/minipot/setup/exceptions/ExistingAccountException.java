package com.spring.minipot.setup.exceptions;

public class ExistingAccountException extends RuntimeException{

    public ExistingAccountException (String message){
        super(message);
    }
}
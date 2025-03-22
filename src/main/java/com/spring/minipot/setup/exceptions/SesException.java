package com.spring.minipot.setup.exceptions;

//AWS Ses Exception
public class SesException extends RuntimeException {
    public SesException(String message){
        super(message);
    }
}

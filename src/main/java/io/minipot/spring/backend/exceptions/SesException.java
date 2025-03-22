package io.minipot.spring.backend.exceptions;

//AWS Ses Exception
public class SesException extends RuntimeException {
    public SesException(String message){
        super(message);
    }
}

package io.minipot.spring.backend.exceptions;

public class NotVerifiedException extends RuntimeException{

    public NotVerifiedException(String message){
        super(message);
    }
}

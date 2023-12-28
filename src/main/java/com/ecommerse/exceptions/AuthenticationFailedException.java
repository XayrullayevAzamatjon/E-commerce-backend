package com.ecommerse.exceptions;

public class AuthenticationFailedException extends IllegalArgumentException{
    public AuthenticationFailedException(String message){
        super(message);
    }
}

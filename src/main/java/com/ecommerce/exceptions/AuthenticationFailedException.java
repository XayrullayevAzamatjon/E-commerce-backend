package com.ecommerce.exceptions;

public class AuthenticationFailedException extends IllegalArgumentException{
    public AuthenticationFailedException(String message){
        super(message);
    }
}

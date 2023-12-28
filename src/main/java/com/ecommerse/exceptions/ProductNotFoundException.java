package com.ecommerse.exceptions;

public class ProductNotFoundException extends IllegalArgumentException{
    public ProductNotFoundException(String message){
        super(message);
    }
}

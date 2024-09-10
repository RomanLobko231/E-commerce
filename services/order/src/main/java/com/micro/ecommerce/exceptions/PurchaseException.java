package com.micro.ecommerce.exceptions;

public class PurchaseException extends RuntimeException{

    public PurchaseException(String message){
        super(message);
    }
}

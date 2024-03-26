package com.crud.backend.exceptions;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String errorMessage){
        super(errorMessage);
    }
}

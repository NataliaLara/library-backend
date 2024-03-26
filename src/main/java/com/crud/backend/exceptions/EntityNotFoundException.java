package com.crud.backend.exceptions;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String errorMessage){
        super(errorMessage);
    }
}

package com.freelance.exception;

public class BaseException extends RuntimeException{

    private final ErrorMessage errorMessage;

    public BaseException(ErrorMessage errorMessage){
        super(errorMessage.prepareErrorMessage());
        this.errorMessage = errorMessage;
    }
}

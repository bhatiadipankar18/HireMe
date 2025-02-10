package com.dipankarbhatia.HireMe.postServices.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}

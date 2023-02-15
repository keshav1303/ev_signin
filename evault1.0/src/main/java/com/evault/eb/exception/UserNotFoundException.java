package com.evault.eb.exception;

public class UserNotFoundException extends Exception{
    private String message="";
    public UserNotFoundException(String message) {
        super(message);
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

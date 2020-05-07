package com.domen.custom_exception;

public class UsernameNotFound extends RuntimeException {
    public UsernameNotFound(String message){
   super(message);
    }
}

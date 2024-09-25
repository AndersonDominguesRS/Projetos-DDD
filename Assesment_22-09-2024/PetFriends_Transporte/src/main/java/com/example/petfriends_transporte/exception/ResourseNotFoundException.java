package com.example.petfriends_transporte.exception;

public class ResourseNotFoundException extends RuntimeException {
    public ResourseNotFoundException(String mensagem) {
        super(mensagem);
    }
}

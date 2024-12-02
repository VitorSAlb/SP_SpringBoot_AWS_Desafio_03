package com.compasspb.vitorsalb.estoque.infra.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

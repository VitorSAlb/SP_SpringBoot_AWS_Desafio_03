package com.compasspb.vitorsalb.estoque.infra.exceptions;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }
}

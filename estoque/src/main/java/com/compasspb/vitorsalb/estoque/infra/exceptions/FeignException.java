package com.compasspb.vitorsalb.estoque.infra.exceptions;

public class FeignException extends RuntimeException {
    public FeignException(String message) {
        super(message);
    }
}

package com.compasspb.vitorsalb.client.infra.exceptions;

public class FeignException extends RuntimeException {
    public FeignException(String message) {
        super(message);
    }
}

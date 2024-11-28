package com.compasspb.vitorsalb.pedido.infra.exceptions;

public class FeignException extends RuntimeException {
    public FeignException(String message) {
        super(message);
    }
}

package com.compasspb.vitorsalb.pedido.infra.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}

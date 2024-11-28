package com.compasspb.vitorsalb.pedido.infra.exceptions;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }
}

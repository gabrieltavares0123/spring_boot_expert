package io.github.gabriel.core.base.exception.service;

import io.github.gabriel.core.base.exception.RuntimeBaseException;

public class PedidoNotFoundException extends RuntimeBaseException {
    public PedidoNotFoundException(String code, String message) {
        super(code, message);
    }
}

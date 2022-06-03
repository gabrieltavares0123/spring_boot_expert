package io.github.gabriel.core.base.exception.service;

import io.github.gabriel.core.base.exception.RuntimeBaseException;

public class InvalidCodeException extends RuntimeBaseException {
    public InvalidCodeException(String code, String message) {
        super(code, message);
    }
}

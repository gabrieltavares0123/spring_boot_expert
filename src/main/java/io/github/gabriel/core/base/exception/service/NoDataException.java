package io.github.gabriel.core.base.exception.service;

import io.github.gabriel.core.base.exception.RuntimeBaseException;

public class NoDataException extends RuntimeBaseException {
    public NoDataException(String code, String message) {
        super(code, message);
    }
}

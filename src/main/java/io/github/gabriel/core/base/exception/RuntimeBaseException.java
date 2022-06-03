package io.github.gabriel.core.base.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RuntimeBaseException extends RuntimeException {
    protected String code;
    protected String message;

    public RuntimeBaseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}

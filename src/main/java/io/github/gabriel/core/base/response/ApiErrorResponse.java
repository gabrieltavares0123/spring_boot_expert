package io.github.gabriel.core.base.response;

import io.github.gabriel.core.base.model.ApiError;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class ApiErrorResponse {
    private List<ApiError> errors;

    public ApiErrorResponse(String code, String message) {
        this.errors = Collections.singletonList(new ApiError(code, message));
    }
}

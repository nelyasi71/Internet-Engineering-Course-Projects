package org.miobook.services;

import org.miobook.Exception.ErrorData;
import org.miobook.Exception.MioBookException;
import org.miobook.responses.BaseResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MioBookException.class)
    public ResponseEntity<BaseResponse<ErrorData>> handleMioBookException(MioBookException ex) {
        ErrorData errorData = new ErrorData(ex.getFieldErrors(), ex.isFieldError() ? null : ex.getLogicError());
        BaseResponse<ErrorData> response = new BaseResponse<>(false, ex.getMessage(), errorData);
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<ErrorData>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage,
                        (msg1, msg2) -> msg1 // in case of duplicate fields
                ));

        ErrorData errorData = new ErrorData(fieldErrors, null);
        BaseResponse<ErrorData> response = new BaseResponse<>(false, "Validation failed", errorData);
        return new ResponseEntity<>(response, response.status());
    }
}

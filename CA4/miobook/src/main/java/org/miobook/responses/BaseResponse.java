package org.miobook.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.miobook.Exception.ErrorData;
import org.miobook.Exception.MioBookException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record BaseResponse<T> (
        boolean success,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T data,
        HttpStatus status,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp
) {
    public BaseResponse(boolean success, String message, T data) {
        this(success, message, data, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        ObjectNode response = objectMapper.createObjectNode();
        response.put("success", success);
        response.put("message", message);
        response.put("status", status.value());
        response.put("timestamp", timestamp.toString());

        if (data != null) {
            response.set("data", objectMapper.valueToTree(data));
        }

        return response.toString();
    }

    public static <T> BaseResponse<T> fromMioBookException(MioBookException ex) {
        ErrorData errorData = new ErrorData(ex.getFieldErrors(), ex.isFieldError() ? null : ex.getLogicError());

        @SuppressWarnings("unchecked")
        T data = (T) errorData;
        return new BaseResponse<>(false, ex.getMessage(), data);
    }
}

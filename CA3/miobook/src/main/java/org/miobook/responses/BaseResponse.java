package org.miobook.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.ToString;

import java.time.LocalDateTime;


public record BaseResponse<T> (
        boolean success,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T data,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp
) {

    public BaseResponse(boolean success, String message, T data) {
        this(success, message, data, LocalDateTime.now());
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        response.put("success", success);
        response.put("message", message);
        if(data != null) {
            objectMapper.registerModule(new JavaTimeModule());
            response.put("data", objectMapper.valueToTree(data));
        }
        return response.toString();
    }
}

package org.miobook.responses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.ToString;


public class BaseResponse {
    private boolean success;
    private String message;
    private ObjectNode data;

    public BaseResponse(boolean success, String message, ObjectNode data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        response.put("success", success);
        response.put("message", message);
        if(data != null) {
            response.put("data", data);
        }
        return response.toString();
    }
}

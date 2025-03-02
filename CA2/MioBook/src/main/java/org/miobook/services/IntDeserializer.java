package org.miobook.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class IntDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (!p.isExpectedNumberIntToken()) {
            throw new IllegalArgumentException("Invalid type: Expected an integer.");
        }
        return p.getIntValue();
    }
}

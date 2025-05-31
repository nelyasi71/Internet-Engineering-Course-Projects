package org.miobook.infrastructure;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.miobook.Exception.MioBookException;

import java.io.IOException;

public class IntDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (!p.isExpectedNumberIntToken()) {
            throw new MioBookException("Invalid type: Expected an integer.");
        }
        return p.getIntValue();
    }
}

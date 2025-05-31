package org.miobook.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.miobook.models.Address;

public record UserRecord (
        String username,
        String role,
        String email,
        Address address,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer balance
) {}

package org.miobook.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

public record AuthorRecord(
        String name,
        String penName,
        String nationality,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate born,
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDate death
) {
}

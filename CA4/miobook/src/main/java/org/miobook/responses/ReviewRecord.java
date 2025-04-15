package org.miobook.responses;

public record ReviewRecord(
        String username,
        int rate,
        String comment
) {
}

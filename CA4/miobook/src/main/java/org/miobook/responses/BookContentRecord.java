package org.miobook.responses;

public record BookContentRecord(
        String title,
        String author,
        String content
) {
}

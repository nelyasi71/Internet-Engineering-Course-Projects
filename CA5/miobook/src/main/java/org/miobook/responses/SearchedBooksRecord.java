package org.miobook.responses;

import java.util.List;

public record SearchedBooksRecord(
        String search,
        List<SearchedBookItemRecord> books
) {
}

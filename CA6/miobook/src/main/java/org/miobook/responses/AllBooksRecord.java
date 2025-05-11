package org.miobook.responses;

import java.util.List;

public record AllBooksRecord (
    List<BookRecord> books
) {}

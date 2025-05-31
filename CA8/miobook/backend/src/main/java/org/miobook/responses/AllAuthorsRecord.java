package org.miobook.responses;

import java.util.List;

public record AllAuthorsRecord (
    List<AuthorRecord> authors
) {}

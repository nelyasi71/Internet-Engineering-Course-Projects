package org.miobook.responses;

import org.miobook.models.Review;

import java.util.List;

public record BookReviewRecord(
        String title,
        List<ReviewRecord> reviews,
        double averageRating
) {
}

package org.miobook.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record PurchaseRecord(
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime purchaseDate,
        List<PurchaseItemRecord> items,
        int totalCost
) {
}

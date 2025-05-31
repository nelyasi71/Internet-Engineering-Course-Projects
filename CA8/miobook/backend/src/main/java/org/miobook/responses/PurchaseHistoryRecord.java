package org.miobook.responses;

import java.util.List;

public record PurchaseHistoryRecord(
        String username,
        List<PurchaseRecord> items
) {
}

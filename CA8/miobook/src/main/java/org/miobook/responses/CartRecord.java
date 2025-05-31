package org.miobook.responses;
import java.util.List;

public record CartRecord(
        String username,
        int totalCost,
        List<PurchaseItemRecord> items
) {
}

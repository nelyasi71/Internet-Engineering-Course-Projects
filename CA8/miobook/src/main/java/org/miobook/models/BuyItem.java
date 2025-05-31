package org.miobook.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.miobook.responses.PurchaseItemRecord;


@Entity
@DiscriminatorValue("BUY")
@NoArgsConstructor
public class BuyItem extends PurchaseItem {
    public BuyItem(Book book) {
        super(book);
        this.price = book.getPrice();
    }

    public BuyItem(BuyItem other) {
        super(other.book);
        this.price = other.price;
    }

    @Override
    public PurchaseItemRecord createRecord() {
        return new PurchaseItemRecord(
                this.book.getTitle(),
                this.book.getAuthor().getName(),
                this.book.getPublisher(),
                this.book.getGenres(),
                this.book.getPublishedYear(),
                this.book.getPrice(),
                false,
                this.price,
                null
        );
    }
}

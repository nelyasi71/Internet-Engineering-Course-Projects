package org.miobook.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.miobook.responses.PurchaseItemRecord;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type")
@Getter @Setter
@NoArgsConstructor
public abstract class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    protected Book book;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    protected Cart cart;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    protected Purchase purchase;

    int price;

    public PurchaseItem(Book book) {
        this.book = book;
    }

    public abstract PurchaseItemRecord createRecord();
}

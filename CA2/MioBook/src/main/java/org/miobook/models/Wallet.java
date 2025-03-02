package org.miobook.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wallet {

    private int credit;

    public Wallet() {
        this.credit = 0;
    }

    public void addCredit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("not aaa");
        }
        this.credit += amount;
    }

    public void decreaseCredit(int amount) {
        credit -= amount;
    }
}

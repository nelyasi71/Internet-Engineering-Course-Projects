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
        this.credit += amount;
    }
}

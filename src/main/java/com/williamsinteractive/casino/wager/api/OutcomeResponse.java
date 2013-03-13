package com.williamsinteractive.casino.wager.api;

import com.google.common.base.Objects;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class OutcomeResponse {
    private final int balance;

    public OutcomeResponse(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(balance);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final OutcomeResponse other = (OutcomeResponse) obj;
        return Objects.equal(this.balance, other.balance);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .add("balance", balance)
                      .toString();
    }
}

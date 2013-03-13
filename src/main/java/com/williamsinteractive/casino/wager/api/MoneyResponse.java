package com.williamsinteractive.casino.wager.api;

import java.util.Objects;

/**
 * TODO: this belongs in the money service API library!
 *
 * @author Petter Måhlén
 */
public class MoneyResponse {
    private final int balance;

    public MoneyResponse(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final MoneyResponse other = (MoneyResponse) obj;
        return Objects.equals(this.balance, other.balance);
    }
}

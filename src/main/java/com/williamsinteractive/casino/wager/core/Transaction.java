package com.williamsinteractive.casino.wager.core;

import com.google.common.base.Objects;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class Transaction {
    private final long wagerRoundId;

    public Transaction(long wageRoundId, int betAmount) {
        this.wagerRoundId = wageRoundId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(wagerRoundId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        return Objects.equal(this.wagerRoundId, other.wagerRoundId);
    }
}

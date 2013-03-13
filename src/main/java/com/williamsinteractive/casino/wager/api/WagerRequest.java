package com.williamsinteractive.casino.wager.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;


/**
 * TODO: should live in separate API library!
 *
 * @author Petter Måhlén
 */
public class WagerRequest {
    // TODO: nice to use the Id<T> pattern from wealthfront
    // TODO: who should generate the wage round id?
    // TODO: should probably/certainly include information about player, game and partner ids as well
    private final long wageRoundId;
    private final long transactionId;
    private final int amount;

    public WagerRequest(@JsonProperty("wageRoundId") long wageRoundId,
                        @JsonProperty("transactionId") long transactionId,
                        @JsonProperty("amount") int amount) {
        this.wageRoundId = wageRoundId;
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public long getWageRoundId() {
        return wageRoundId;
    }

    public int getAmount() {
        return amount;
    }

    public long getTransactionId() {
        return transactionId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(wageRoundId, transactionId, amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final WagerRequest other = (WagerRequest) obj;
        return Objects.equal(this.wageRoundId, other.wageRoundId) &&
               Objects.equal(this.transactionId, other.transactionId) &&
               Objects.equal(this.amount, other.amount);
    }
}

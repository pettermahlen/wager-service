package com.williamsinteractive.casino.wager.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;


/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class OutcomeRequest {
    private final long wageRoundId;
    private final long transactionId; // TODO: needed??!
    private final long amount;

    public OutcomeRequest(@JsonProperty long wageRoundId, @JsonProperty long transactionId, @JsonProperty long amount) {
        this.wageRoundId = wageRoundId;
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public long getWageRoundId() {
        return wageRoundId;
    }

    public long getAmount() {
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
        final OutcomeRequest other = (OutcomeRequest) obj;
        return Objects.equal(this.wageRoundId, other.wageRoundId) && Objects.equal(this.transactionId,
                                                                                   other.transactionId) && Objects.equal(
            this.amount,
            other.amount);
    }
}

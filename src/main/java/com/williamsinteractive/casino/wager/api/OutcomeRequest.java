package com.williamsinteractive.casino.wager.api;

import java.util.Objects;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class OutcomeRequest {
    private final long wageRoundId;
    private final int amount;

    public OutcomeRequest(long wageRoundId, int amount) {
        this.wageRoundId = wageRoundId;
        this.amount = amount;
    }

    public long getWageRoundId() {
        return wageRoundId;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wageRoundId, amount);
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
        return Objects.equals(this.wageRoundId, other.wageRoundId) && Objects.equals(this.amount, other.amount);
    }
}

package com.williamsinteractive.casino.wager.core;

import com.google.common.base.Objects;
import com.williamsinteractive.casino.wager.model.Id;
import com.williamsinteractive.casino.wager.model.Wager;
import org.joda.time.DateTime;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class CompletedWager {
    // TODO: should maybe have wagerRoundId - skipping that for now, as we're tracking that via direct object references.
    private final Id<Wager> wagerId;
    private final long wagerAmount;
    private final DateTime dateInitiated;
    private final DateTime dateConfirmed;

    public CompletedWager(Id<Wager> wagerId, long wagerAmount, DateTime dateInitiated, DateTime dateConfirmed) {
        this.wagerId = wagerId;
        this.wagerAmount = wagerAmount;
        this.dateInitiated = dateInitiated;
        this.dateConfirmed = dateConfirmed;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(wagerId, wagerAmount, dateInitiated, dateConfirmed);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final CompletedWager other = (CompletedWager) obj;
        return Objects.equal(this.wagerId, other.wagerId) && Objects.equal(this.wagerAmount, other.wagerAmount) && Objects.equal(this.dateInitiated,
                                                                                                                                 other.dateInitiated) && Objects
                   .equal(this.dateConfirmed, other.dateConfirmed);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .add("wagerId", wagerId)
                      .add("wagerAmount", wagerAmount)
                      .add("dateInitiated", dateInitiated)
                      .add("dateConfirmed", dateConfirmed)
                      .toString();
    }
}

package com.williamsinteractive.casino.wager.core;

import com.google.common.base.Objects;
import com.williamsinteractive.casino.wager.model.ExchangeRate;
import com.williamsinteractive.casino.wager.model.Game;
import com.williamsinteractive.casino.wager.model.Id;
import com.williamsinteractive.casino.wager.model.WagerRound;

import java.util.List;

/**
 * TODO: document!
 * NOTE: this is a separate class from the (currently empty) WagerRound class for two reasons:
 * 1. This should represent the data for a completed (surprise!) wager round, as opposed to the general concept of a wager round.
 * 2. The general wager round concept probably belongs in a shared (between many services) model library, whereas this is specific
 * to the wager service alone.
 *
 * @author Petter Måhlén
 */
public class CompletedWagerRound {
    private final Id<WagerRound> wagerRoundId;
    private final List<CompletedWager> wagers;
    private final Id<Game> gameId;
    private final Id<ExchangeRate> exchangeRateId;
    private final long winAmount;


    public CompletedWagerRound(Id<WagerRound> wagerRoundId, List<CompletedWager> wagers, Id<Game> gameId, Id<ExchangeRate> exchangeRateId, long winAmount) {
        this.wagerRoundId = wagerRoundId;
        this.wagers = wagers;
        this.gameId = gameId;
        this.exchangeRateId = exchangeRateId;
        this.winAmount = winAmount;
    }

    public Id<WagerRound> getWagerRoundId() {
        return wagerRoundId;
    }

    public List<CompletedWager> getWagers() {
        return wagers;
    }

    public Id<Game> getGameId() {
        return gameId;
    }

    public Id<ExchangeRate> getExchangeRateId() {
        return exchangeRateId;
    }

    public long getWinAmount() {
        return winAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(wagerRoundId, wagers, gameId, exchangeRateId, winAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final CompletedWagerRound other = (CompletedWagerRound) obj;
        return Objects.equal(this.wagerRoundId, other.wagerRoundId) && Objects.equal(this.wagers, other.wagers) && Objects.equal(this.gameId,
                                                                                                                                 other.gameId) && Objects.equal(
            this.exchangeRateId,
            other.exchangeRateId) && Objects.equal(this.winAmount, other.winAmount);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .add("wagerRoundId", wagerRoundId)
                      .add("wagers", wagers)
                      .add("gameId", gameId)
                      .add("exchangeRateId", exchangeRateId)
                      .add("winAmount", winAmount)
                      .toString();
    }
}

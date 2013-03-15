package com.williamsinteractive.casino.wager.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.validation.constraints.Min;


/**
 * TODO: should live in separate API library!
 *
 * @author Petter Måhlén
 */
public class WagerRequest {
    // TODO: who should generate the wage round id?
    // TODO: should probably/certainly include information about player, game and partner ids as well
    private final long wageRoundId;
    private final long wagerId;
    private final int amount;
    private final long gameId;
    private final long exchangeRateId;

    public WagerRequest(@JsonProperty("wagerRoundId") @Min(1) long wageRoundId,
                        @JsonProperty("wagerId") @Min(1) long wagerId,
                        @JsonProperty("amount") int amount,
                        @JsonProperty("gameId") long gameId,
                        @JsonProperty("exchangeRateId") long exchangeRateId) {
        this.wageRoundId = wageRoundId;
        this.wagerId = wagerId;
        this.amount = amount;
        this.gameId = gameId;
        this.exchangeRateId = exchangeRateId;
    }

    public long getWageRoundId() {
        return wageRoundId;
    }

    public int getAmount() {
        return amount;
    }

    public long getWagerId() {
        return wagerId;
    }

    public long getGameId() {
        return gameId;
    }

    public long getExchangeRateId() {
        return exchangeRateId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(wageRoundId, wagerId, amount, gameId, exchangeRateId);
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
               Objects.equal(this.wagerId, other.wagerId) &&
               Objects.equal(this.amount, other.amount) &&
               Objects.equal(this.gameId, other.gameId) &&
               Objects.equal(this.exchangeRateId, other.exchangeRateId);
    }
}

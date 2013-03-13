package com.williamsinteractive.casino.wager.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.google.common.base.Objects;

/**
 * TODO: should live in a separate API library!
 *
 * @author Petter Måhlén
 */
public class WagerResponse {
    private final BetResult betResult;
    private final int balance;

    public WagerResponse(@JsonProperty("result") BetResult betResult, @JsonProperty("balance") int balance) {
        this.betResult = betResult;
        this.balance = balance;
    }

    public BetResult getBetResult() {
        return betResult;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(betResult, balance);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final WagerResponse other = (WagerResponse) obj;
        return Objects.equal(this.betResult, other.betResult) && Objects.equal(this.balance, other.balance);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .add("betResult", betResult)
                      .add("balance", balance)
                      .toString();
    }
}

package com.williamsinteractive.casino.wager.core;

import com.williamsinteractive.casino.wager.model.ExchangeRate;
import com.williamsinteractive.casino.wager.model.Game;
import com.williamsinteractive.casino.wager.model.Id;
import com.williamsinteractive.casino.wager.model.Wager;
import com.williamsinteractive.casino.wager.model.WagerRound;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public interface WagerRoundStateStore {
    void recordWager(Id<WagerRound> wagerRoundId, Id<Wager> wagerId, long wagerAmount, Id<Game> gameId, Id<ExchangeRate> exchangeRateId);

    void confirmWager(Id<WagerRound> wagerRoundId, Id<Wager> wagerId);

    void recordOutcome(Id<WagerRound> wagerRoundId, long winAmount);

    CompletedWagerRound confirmOutcome(Id<WagerRound> wagerRoundId, long winAmount);

    void recordArchival(Id<WagerRound> wagerRoundId);
}

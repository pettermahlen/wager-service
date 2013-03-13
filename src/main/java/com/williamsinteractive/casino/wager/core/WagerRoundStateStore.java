package com.williamsinteractive.casino.wager.core;

import com.williamsinteractive.casino.wager.model.Id;
import com.williamsinteractive.casino.wager.model.Wager;
import com.williamsinteractive.casino.wager.model.WagerRound;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public interface WagerRoundStateStore {
    void record(Id<WagerRound> wagerRoundId, Id<Wager> transactionId, WageRoundState state, long amount);
}

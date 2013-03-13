package com.williamsinteractive.casino.wager.core;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public interface WagerRoundStateStore {
    void record(long wageRoundId, WageRoundState state);
}

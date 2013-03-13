package com.williamsinteractive.casino.wager.core;

import com.williamsinteractive.casino.wager.api.WagerRequest;
import com.williamsinteractive.casino.wager.api.WagerResponse;
import com.williamsinteractive.casino.wager.api.OutcomeRequest;
import com.williamsinteractive.casino.wager.api.OutcomeResponse;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public interface WagerRoundManager {
    WagerResponse wager(WagerRequest request);

    OutcomeResponse outcome(OutcomeRequest request);
}

package com.williamsinteractive.casino.wager.core;

import com.williamsinteractive.casino.wager.api.OutcomeRequest;
import com.williamsinteractive.casino.wager.api.OutcomeResponse;
import com.williamsinteractive.casino.wager.api.WagerRequest;
import com.williamsinteractive.casino.wager.api.WagerResponse;

/**
 * TODO: note that these guys should not just be reusing the JSON API!
 * TODO: also, this should possibly be asynchronous for improved performance in VoltDB API
 *
 * @author Petter Måhlén
 */
public interface WagerRoundManager {
    WagerResponse wager(WagerRequest request);

    OutcomeResponse outcome(OutcomeRequest request);
}

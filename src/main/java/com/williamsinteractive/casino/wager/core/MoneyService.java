package com.williamsinteractive.casino.wager.core;

import com.williamsinteractive.casino.wager.api.MoneyResponse;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public interface MoneyService {
    // TODO: should use some internal 'money' type here instead of a long
    MoneyResponse request(long amount);

    MoneyResponse win(long amount);
}

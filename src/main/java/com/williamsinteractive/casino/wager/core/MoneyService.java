package com.williamsinteractive.casino.wager.core;

import com.williamsinteractive.casino.wager.api.MoneyResponse;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public interface MoneyService {
    MoneyResponse request(int amount);
    MoneyResponse win(int amount);
}

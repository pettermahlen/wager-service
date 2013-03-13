package com.williamsinteractive.casino.wager.core;

import com.williamsinteractive.casino.wager.api.MoneyResponse;

import javax.inject.Inject;
import javax.inject.Named;
import java.security.SecureRandom;
import java.util.Random;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class FakeMoneyService implements MoneyService {
    private static final Random RANDOM = new SecureRandom();

    private final long maxDelay;

    @Inject
    public FakeMoneyService(@Named("MAX_DELAY") long maxDelay) {
        this.maxDelay = maxDelay;
    }

    @Override
    public MoneyResponse request(int amount) {
        delay();

        return new MoneyResponse(8767);
    }

    @Override
    public MoneyResponse win(int amount) {
        delay();

        return new MoneyResponse(8767);
    }

    private void delay() {
        try {
            long timeToSleep = Math.abs(RANDOM.nextLong() % maxDelay);

            Thread.sleep(timeToSleep);
        }
        catch (InterruptedException e) {
            // restore interrupt status
            Thread.currentThread().interrupt();
        }
    }
}

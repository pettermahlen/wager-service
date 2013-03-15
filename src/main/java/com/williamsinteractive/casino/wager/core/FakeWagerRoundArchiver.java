package com.williamsinteractive.casino.wager.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class FakeWagerRoundArchiver implements WagerRoundArchiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(FakeWagerRoundArchiver.class);

    @Override
    public void archive(CompletedWagerRound completedWagerRound) {
        LOGGER.info("Archiving completedWagerRound: {}", completedWagerRound);
    }
}

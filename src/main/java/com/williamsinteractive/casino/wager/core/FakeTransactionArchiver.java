package com.williamsinteractive.casino.wager.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class FakeTransactionArchiver implements TransactionArchiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(FakeTransactionArchiver.class);

    @Override
    public void archive(Transaction transaction) {
        LOGGER.info("Archiving transaction: {}", transaction);
    }
}

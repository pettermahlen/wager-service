package com.williamsinteractive.casino.wager.acceptance;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;

import java.util.List;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class TransactionLoggerStepdefs {
    @Then("^the transaction logger has a completed wager round with id (\\d+) and wagers:$")
    public void the_transaction_logger_has_a_completed_wager_round_with_id_and_wagers(long wagerRoundId, List<WagerData> wagers) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }
}

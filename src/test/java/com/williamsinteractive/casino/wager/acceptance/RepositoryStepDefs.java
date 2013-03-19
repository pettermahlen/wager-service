package com.williamsinteractive.casino.wager.acceptance;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;

import java.util.List;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class RepositoryStepDefs {
    @Given("^an empty live wagers repository$")
    public void an_empty_live_wagers_repository() throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Then("^the live wagers repository contains a confirmed wager for round (\\d+) with id (\\d+)$")
    public void the_live_wagers_repository_contains_a_confirmed_wager_for_round_with_id(long wagerRoundId, long wagerId) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Given("^a live wager repository with the following wager data$")
    public void a_live_wager_repository_with_the_following_wager_data(List<WagerData> wagers) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Then("^the live wagers repository contains a confirmed outcome for round (\\d+)$")
    public void the_live_wagers_repository_contains_a_confirmed_outcome_for_round(long wagerRoundId) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Then("^the live wagers repository contains an unconfirmed wager for round (\\d+) with id (\\d+)$")
    public void the_live_wagers_repository_contains_an_unconfirmed_wager_for_round_with_id(int arg1, int arg2) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }
}

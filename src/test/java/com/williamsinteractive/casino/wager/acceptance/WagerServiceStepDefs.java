package com.williamsinteractive.casino.wager.acceptance;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.PendingException;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class WagerServiceStepDefs {
    @When("^a place wager request for wager round id (\\d+) and wager id (\\d+) arrives$")
    public void a_place_wager_request_for_wager_round_id_and_wager_id_arrives(long wagerRoundId, long wagerId) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Then("^the response indicates \\\"([^\\\"]*)\\\"$")
    public void the_response_indicates(String result) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @When("^an outcome request for wager round id (\\d+) arrives$")
    public void an_outcome_request_for_wager_round_id_arrives(long wagerRoundId) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }
}

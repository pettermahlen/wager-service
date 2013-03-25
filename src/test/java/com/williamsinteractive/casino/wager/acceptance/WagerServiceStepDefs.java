package com.williamsinteractive.casino.wager.acceptance;

import com.williamsinteractive.casino.wager.api.OutcomeRequest;
import com.williamsinteractive.casino.wager.api.OutcomeResponse;
import com.williamsinteractive.casino.wager.api.WagerRequest;
import com.williamsinteractive.casino.wager.api.WagerResponse;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.PendingException;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class WagerServiceStepDefs {
    JsonClient<WagerRequest, WagerResponse> wagerClient;
    JsonClient<OutcomeRequest, OutcomeResponse> outcomeClient;

    WagerRequest wagerRequest;
    OutcomeRequest outcomeRequest;

    @Before
    public void setupWagerClient() throws Exception {
        // TODO: there's value in not hard-coding the 8080 - in particular, for continuous integration builds, where it's a good
        // idea to use an environment variable value for the port and the port allocator plugin for Jenkins. Otherwise, builds
        // of different projects are going to interfere with each other.
        wagerClient = new JsonClient<>(WagerResponse.class, "http://localhost:8080/wager/place");
        outcomeClient = new JsonClient<>(OutcomeResponse.class, "http://localhost:8080/wager/outcome");
    }

    @When("^a place wager request for wager round id (\\d+) and wager id (\\d+) arrives$")
    public void a_place_wager_request_for_wager_round_id_and_wager_id_arrives(long wagerRoundId, long wagerId) throws Throwable {
        wagerRequest = new WagerRequest(wagerRoundId, wagerId, 87, 921, 89);
        outcomeRequest = null;
    }

    @When("^an outcome request for wager round id (\\d+) arrives$")
    public void an_outcome_request_for_wager_round_id_arrives(long wagerRoundId) throws Throwable {
        wagerRequest = null;
        outcomeRequest = new OutcomeRequest(wagerRoundId, 76234, 8778);
    }

    @Then("^the response indicates \\\"([^\\\"]*)\\\"$")
    public void the_response_indicates(String result) throws Throwable {
        // TODO: ugly way to decide which endpoint to call, it should probably be two different methods
        if (wagerRequest != null) {
            // TODO: should ensure that the response matches the required result - today, all failures will lead to 500 errors,
            // so this code won't work.
            wagerClient.call(wagerRequest);
        }
        else {
            outcomeClient.call(outcomeRequest);
        }
    }

}

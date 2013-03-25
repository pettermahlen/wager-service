package com.williamsinteractive.casino.wager.acceptance;

import cucumber.api.java.en.Given;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class MoneyServiceStepDefs {
    @Given("^a money service that always succeeds$")
    public void a_money_service_that_always_succeeds() throws Throwable {
        // empty implementation - the FakeMoneyService will in fact always succeed, so it's fine. In a real situation,
        // there would be something like an HttpMoneyService that would call out to a downstream service. The way to implement
        // this method then becomes:
        // 1. Make sure that the WagerService is configured to look for the MoneyService in a location like http://localhost:<port>/money
        // 2. Start up something (maybe a com.sun.net.httpserver.HttpServer) that listens to that URL.
        // 3. Configure that something to respond as desired.
    }

    @Given("^a money service that always fails$")
    public void a_money_service_that_always_fails() throws Throwable {
        // empty implementation - NOTE: that means this test is broken!
        // see above for explanation of how to implement this in some future.
    }
}

package com.williamsinteractive.casino.wager.acceptance;

import com.google.common.base.Function;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ProcCallException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class RepositoryStepDefs {
    private static final long GAME_ID = 88L;
    private static final long EXCHANGE_RATE_ID = 786435;
    private static final long AMOUNT = 87345;

    protected Client client;

    @Before
    public final void connectToVolt() throws Exception {
        ClientConfig config = new ClientConfig("test", "test");
        client = ClientFactory.createClient(config);
        client.createConnection("localhost");

        clearTable("wager_round", new FirstLong());
        clearTable("wager", new WagerPK());
    }

    @After
    public final void closeVolt() throws Exception {
        client.close();
    }

    protected void clearTable(String tableName,
                              Function<VoltTable, Object[]> primaryKeyFunction) throws IOException, ProcCallException {
        String upperCaseTable = tableName.toUpperCase();

        VoltTable table = client.callProcedure(upperCaseTable + "_SELECT_ALL").getResults()[0];

        while (table.advanceRow()) {
            client.callProcedure(upperCaseTable + ".delete", primaryKeyFunction.apply(table));
        }
    }

    @Given("^an empty live wagers repository$")
    public void an_empty_live_wagers_repository() throws Throwable {
        // empty implementation - this happens by default thanks to the @Before method above
    }

    @Given("^a live wager repository with the following wager data$")
    public void a_live_wager_repository_with_the_following_wager_data(List<WagerData> wagers) throws Throwable {
        // TODO: tests become more flexible if the exact details of what is inserted is hidden behind some abstract that provides defaults.
        // So, it would be better to have some method like insertWagerRound(WagerRoundBuilder builder), and to be able to instantiate
        // a WagerRoundBuilder something like WagerRoundBuilder.id(wagerRoundId).gameId(gameId). That way, the builder can have sensible
        // defaults for everything that isn't explicitly needed for a test case. With the approach below, a change in the schema means
        // that every place where a wager round is inserted needs to change. But this is just a prototype... :)

        for (WagerData wagerData: wagers) {
            client.callProcedure("WAGER_ROUND.insert", wagerData.wagerRoundId, GAME_ID, EXCHANGE_RATE_ID, null, null, null);
            client.callProcedure("WAGER.insert", wagerData.wagerRoundId, wagerData.wagerId, AMOUNT, new Date(), wagerData.confirmed);
        }
    }

    @Then("^the live wagers repository contains a confirmed wager for round (\\d+) with id (\\d+)$")
    public void the_live_wagers_repository_contains_a_confirmed_wager_for_round_with_id(long wagerRoundId, long wagerId) throws Throwable {
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

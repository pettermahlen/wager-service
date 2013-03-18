package com.williamsinteractive.casino.wager.core;

import com.google.common.collect.ImmutableList;
import com.williamsinteractive.casino.wager.model.ExchangeRate;
import com.williamsinteractive.casino.wager.model.Game;
import com.williamsinteractive.casino.wager.model.Id;
import com.williamsinteractive.casino.wager.model.Wager;
import com.williamsinteractive.casino.wager.model.WagerRound;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.voltdb.VoltTable;
import org.voltdb.VoltType;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcedureCallback;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class VoltWagerRoundStateStoreTest {
    public static final byte SUCCESS = 0;
    VoltWagerRoundStateStore store;

    Client client;

    Id<WagerRound> wagerRoundId;
    Id<Wager> wagerId;
    Id<Game> gameId;
    Id<ExchangeRate> exchangeRateId;

    ClientResponse response;
    DateTime requestDate1;
    DateTime confirmDate1;
    DateTime requestDate2;
    DateTime confirmDate2;
    DateTime outcomeConfirmDate;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

        client = mock(Client.class);

        store = new VoltWagerRoundStateStore(client);

        wagerRoundId = Id.of(82374);
        wagerId = Id.of(543);
        gameId = Id.of(635487);
        exchangeRateId = Id.of(876345);

        response = mock(ClientResponse.class);

        when(client.callProcedure(any(ProcedureCallback.class), anyString(), anyVararg())).thenAnswer(new VoltCallbackAnswer(response));

        requestDate1 = DateTime.parse("2013-03-15T11:03:00");
        confirmDate1 = DateTime.parse("2013-03-15T11:03:00");
        requestDate2 = DateTime.parse("2013-03-15T11:04:00");
        confirmDate2 = DateTime.parse("2013-03-15T11:04:10");
        outcomeConfirmDate = DateTime.parse("2013-03-15T11:04:12");
    }

    @Test
    public void shouldCallRecordWagerForRecordWager() throws Exception {
        when(response.getStatus()).thenReturn(ClientResponse.SUCCESS);
        when(response.getAppStatus()).thenReturn(SUCCESS); // TODO: should link with repository catalogue jar so as to not duplicate?!

        store.recordWager(wagerRoundId, wagerId, 234666, gameId, exchangeRateId);

        // TODO: in a way, it's nicer to verify all the args rather than using anyVararg(), but...
        // It's almost looking too deeply into the API, possibly making tests harder to maintain
        verify(client).callProcedure(any(ProcedureCallback.class), eq("RecordWager"), anyVararg());
    }

    @Test
    public void shouldCallConfirmWagerForConfirmWager() throws Exception {
        when(response.getStatus()).thenReturn(ClientResponse.SUCCESS);
        when(response.getAppStatus()).thenReturn(SUCCESS); // TODO: should link with repository catalogue jar so as to not duplicate?!

        store.confirmWager(wagerRoundId, wagerId);

        verify(client).callProcedure(any(ProcedureCallback.class), eq("ConfirmWager"), eq(wagerRoundId.id()), eq(wagerId.id()));
    }

    @Test
    public void shouldCallRecordOutcomeForRecordOutcome() throws Exception {
        when(response.getStatus()).thenReturn(ClientResponse.SUCCESS);
        when(response.getAppStatus()).thenReturn(SUCCESS); // TODO: should link with repository catalogue jar so as to not duplicate?!

        store.recordOutcome(wagerRoundId, 985436);

        // TODO: in a way, it's nicer to verify all the args rather than using anyVararg(), but...
        // It's almost looking too deeply into the API, possibly making tests harder to maintain
        verify(client).callProcedure(any(ProcedureCallback.class), eq("RecordOutcome"), anyVararg());
    }

    @Test
    public void shouldCallConfirmOutcomeForConfirmOutcome() throws Exception {
        when(response.getStatus()).thenReturn(ClientResponse.SUCCESS);
        when(response.getAppStatus()).thenReturn(SUCCESS); // TODO: should link with repository catalogue jar so as to not duplicate?!
        when(response.getResults()).thenReturn(setupClientResponseForWagerRoundData());

        store.confirmOutcome(wagerRoundId, 985436L);

        verify(client).callProcedure(any(ProcedureCallback.class), eq("ConfirmOutcome"), eq(wagerRoundId.id()), eq(985436L));
    }

    @Test
    public void shouldReturnWagerRoundDataWhenConfirmingOutcome() throws Exception {
        CompletedWagerRound expected = setupExpectedWagerRound();
        when(response.getResults()).thenReturn(setupClientResponseForWagerRoundData());
        when(response.getStatus()).thenReturn(ClientResponse.SUCCESS);
        when(response.getAppStatus()).thenReturn(SUCCESS);

        assertThat(store.confirmOutcome(wagerRoundId, 123), equalTo(expected));
    }


    @Test
    public void shouldCallRecordArchivalForRecordArchival() throws Exception {
        when(response.getStatus()).thenReturn(ClientResponse.SUCCESS);
        when(response.getAppStatus()).thenReturn(SUCCESS); // TODO: should link with repository catalogue jar so as to not duplicate?!

        store.recordArchival(wagerRoundId);

        verify(client).callProcedure(any(ProcedureCallback.class), eq("RecordArchival"), eq(wagerRoundId.id()));
    }

    @Test
    public void shouldFailRecordWagerIfAppStatusInvalid() throws Exception {
        when(response.getStatus()).thenReturn(ClientResponse.SUCCESS);
        when(response.getAppStatus()).thenReturn((byte) 14);
        when(response.getAppStatusString()).thenReturn("a failure message");

        thrown.expect(RuntimeException.class);
        thrown.expectMessage(containsString("a failure message"));

        store.recordWager(wagerRoundId, wagerId, 8273, gameId, exchangeRateId);
    }

    @Test
    public void shouldFailConfirmWagerIfAppStatusInvalid() throws Exception {
        when(response.getStatus()).thenReturn(ClientResponse.SUCCESS);
        when(response.getAppStatus()).thenReturn((byte) 14);
        when(response.getAppStatusString()).thenReturn("a failure message");

        thrown.expect(RuntimeException.class);
        thrown.expectMessage(containsString("a failure message"));

        store.confirmWager(wagerRoundId, wagerId);
    }

    @Test
    public void shouldFailRecordOutcomeIfAppStatusInvalid() throws Exception {
        when(response.getStatus()).thenReturn(ClientResponse.SUCCESS);
        when(response.getAppStatus()).thenReturn((byte) 14);
        when(response.getAppStatusString()).thenReturn("a failure message");

        thrown.expect(RuntimeException.class);
        thrown.expectMessage(containsString("a failure message"));

        store.recordOutcome(wagerRoundId, 8273);
    }

    @Test
    public void shouldFailConfirmOutcomeIfAppStatusInvalid() throws Exception {
        when(response.getStatus()).thenReturn(ClientResponse.SUCCESS);
        when(response.getAppStatus()).thenReturn((byte) 14);
        when(response.getAppStatusString()).thenReturn("a failure message");

        thrown.expect(RuntimeException.class);
        thrown.expectMessage(containsString("a failure message"));

        store.confirmOutcome(wagerRoundId, 8273);
    }

    @Test
    public void shouldFailRecordArchivalIfAppStatusInvalid() throws Exception {
        when(response.getStatus()).thenReturn(ClientResponse.SUCCESS);
        when(response.getAppStatus()).thenReturn((byte) 14);
        when(response.getAppStatusString()).thenReturn("a failure message");

        thrown.expect(RuntimeException.class);
        thrown.expectMessage(containsString("a failure message"));

        store.recordArchival(wagerRoundId);
    }


    private CompletedWagerRound setupExpectedWagerRound() {
        CompletedWager wager1 = new CompletedWager(wagerId, 27866, requestDate1, confirmDate1);
        CompletedWager wager2 = new CompletedWager(Id.<Wager>of(7634), 667, requestDate2, confirmDate2);
        List<CompletedWager> completedWagers = ImmutableList.of(wager1, wager2);

        // TODO: add dates to wager round
        return new CompletedWagerRound(wagerRoundId, completedWagers, gameId, exchangeRateId, 65);
    }

    private static class VoltCallbackAnswer implements Answer<Boolean> {
        private final ClientResponse response;

        private VoltCallbackAnswer(ClientResponse response) {
            this.response = response;
        }

        @Override
        public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
            ProcedureCallback callback = (ProcedureCallback) invocationOnMock.getArguments()[0];

            callback.clientCallback(response);

            return true;
        }
    }

    private VoltTable[] setupClientResponseForWagerRoundData() {
        VoltTable wagers = new VoltTable(WAGER_COLUMNS);
        wagers.addRow(wagerRoundId.id(), wagerId.id(), 27866, requestDate1.toDate(), confirmDate1.toDate());
        wagers.addRow(wagerRoundId.id(), 7634, 667, requestDate2.toDate(), confirmDate2.toDate());

        VoltTable wagerRounds = new VoltTable(WAGER_ROUND_COLUMNS);
        wagerRounds.addRow(wagerRoundId.id(), gameId.id(), exchangeRateId.id(), 65, outcomeConfirmDate.toDate(), null);

        return new VoltTable[]{wagers,
                               wagerRounds};
    }


    private static final VoltTable.ColumnInfo[] WAGER_COLUMNS = new VoltTable.ColumnInfo[]{
        new VoltTable.ColumnInfo("wager_round_id", VoltType.BIGINT),
        new VoltTable.ColumnInfo("wager_id", VoltType.BIGINT),
        new VoltTable.ColumnInfo("amount", VoltType.BIGINT),
        new VoltTable.ColumnInfo("created", VoltType.TIMESTAMP),
        new VoltTable.ColumnInfo("confirmed", VoltType.TIMESTAMP),
    };

    private static final VoltTable.ColumnInfo[] WAGER_ROUND_COLUMNS = new VoltTable.ColumnInfo[]{
        new VoltTable.ColumnInfo("wager_round_id", VoltType.BIGINT),
        new VoltTable.ColumnInfo("game_id", VoltType.BIGINT),
        new VoltTable.ColumnInfo("exchange_rate_id", VoltType.BIGINT),
        new VoltTable.ColumnInfo("outcome_amount", VoltType.BIGINT),
        new VoltTable.ColumnInfo("outcome_timestamp", VoltType.TIMESTAMP),
        new VoltTable.ColumnInfo("archive_timestamp", VoltType.TIMESTAMP),
    };
}

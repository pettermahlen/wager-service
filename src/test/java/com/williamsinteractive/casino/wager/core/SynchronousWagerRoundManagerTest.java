package com.williamsinteractive.casino.wager.core;

import com.williamsinteractive.casino.wager.api.WagerRequest;
import com.williamsinteractive.casino.wager.api.WagerResponse;
import com.williamsinteractive.casino.wager.api.BetResult;
import com.williamsinteractive.casino.wager.api.MoneyResponse;
import com.williamsinteractive.casino.wager.api.OutcomeRequest;
import com.williamsinteractive.casino.wager.api.OutcomeResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static com.williamsinteractive.casino.wager.core.WageRoundState.GOT_MONEY;
import static com.williamsinteractive.casino.wager.core.WageRoundState.GOT_OUTCOME;
import static com.williamsinteractive.casino.wager.core.WageRoundState.OUTCOME_CONFIRMED;
import static com.williamsinteractive.casino.wager.core.WageRoundState.REQUEST_MONEY;
import static com.williamsinteractive.casino.wager.core.WageRoundState.ARCHIVED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class SynchronousWagerRoundManagerTest {
    SynchronousWagerRoundManager manager;

    WagerRoundStateStore stateStore;
    MoneyService moneyService;
    TransactionArchiver archiver;

    WagerRequest wagerRequest;
    OutcomeRequest outcomeRequest;
    MoneyResponse moneyResponse;

    @Before
    public void setUp() throws Exception {

        stateStore = mock(WagerRoundStateStore.class);
        moneyService = mock(MoneyService.class);
        archiver = mock(TransactionArchiver.class);

        manager = new SynchronousWagerRoundManager(stateStore, moneyService, archiver);
    }

    @Test
    public void shouldStoreStateBeforeAndAfterAskingForMoneyForBet() throws Exception {
        wagerRequest = new WagerRequest(8726435, 7685);
        moneyResponse = new MoneyResponse(78623);

        when(moneyService.request(7685)).thenReturn(moneyResponse);

        manager.wager(wagerRequest);

        InOrder order = inOrder(stateStore, moneyService, archiver);

        order.verify(stateStore).record(8726435L, REQUEST_MONEY);
        order.verify(moneyService).request(7685);
        order.verify(stateStore).record(8726435L, GOT_MONEY);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void shouldReturnBalanceFromResponse() throws Exception {
        wagerRequest = new WagerRequest(76435, 43);
        moneyResponse = new MoneyResponse(57);

        when(moneyService.request(43)).thenReturn(moneyResponse);

        assertThat(manager.wager(wagerRequest), equalTo(new WagerResponse(BetResult.OK, 57)));
    }

    @Test
    public void shouldStoreStateBeforeAndAfterOutcome() throws Exception {
        outcomeRequest = new OutcomeRequest(8766521, 654);
        moneyResponse = new MoneyResponse(78623);

        when(moneyService.win(654)).thenReturn(moneyResponse);

        manager.outcome(outcomeRequest);

        InOrder order = inOrder(stateStore, moneyService, archiver);

        order.verify(stateStore).record(8766521, GOT_OUTCOME);
        order.verify(moneyService).win(654);
        order.verify(stateStore).record(8766521, OUTCOME_CONFIRMED);
        order.verify(archiver).archive(new Transaction(8766521, 999));
        order.verify(stateStore).record(8766521, ARCHIVED);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void shouldReturnBalanceFromMoneyServiceForOutcome() throws Exception {
        outcomeRequest = new OutcomeRequest(626521, 4654);
        moneyResponse = new MoneyResponse(765);

        when(moneyService.win(4654)).thenReturn(moneyResponse);

        assertThat(manager.outcome(outcomeRequest), equalTo(new OutcomeResponse(765)));
    }
}

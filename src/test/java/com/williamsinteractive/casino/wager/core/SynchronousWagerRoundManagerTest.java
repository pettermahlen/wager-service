package com.williamsinteractive.casino.wager.core;

import com.google.common.collect.ImmutableList;
import com.williamsinteractive.casino.wager.api.BetResult;
import com.williamsinteractive.casino.wager.api.MoneyResponse;
import com.williamsinteractive.casino.wager.api.OutcomeRequest;
import com.williamsinteractive.casino.wager.api.OutcomeResponse;
import com.williamsinteractive.casino.wager.api.WagerRequest;
import com.williamsinteractive.casino.wager.api.WagerResponse;
import com.williamsinteractive.casino.wager.model.ExchangeRate;
import com.williamsinteractive.casino.wager.model.Game;
import com.williamsinteractive.casino.wager.model.Id;
import com.williamsinteractive.casino.wager.model.Wager;
import com.williamsinteractive.casino.wager.model.WagerRound;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

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
    WagerRoundArchiver archiver;

    WagerRequest wagerRequest;
    OutcomeRequest outcomeRequest;
    MoneyResponse moneyResponse;

    Id<WagerRound> wagerRoundId;
    Id<Wager> wagerId;
    Id<Game> gameId;
    Id<ExchangeRate> exchangeRateId;
    CompletedWagerRound completedWagerRound;

    @Before
    public void setUp() throws Exception {

        stateStore = mock(WagerRoundStateStore.class);
        moneyService = mock(MoneyService.class);
        archiver = mock(WagerRoundArchiver.class);

        manager = new SynchronousWagerRoundManager(stateStore, moneyService, archiver);

        wagerRoundId = Id.of(76452145);
        wagerId = Id.of(6354);
        gameId = Id.of(4356);
        exchangeRateId = Id.of(76534);
    }

    @Test
    public void shouldStoreStateBeforeAndAfterAskingForMoneyForBet() throws Exception {
        wagerRequest = new WagerRequest(wagerRoundId.id(), wagerId.id(), 7685, gameId.id(), exchangeRateId.id());
        moneyResponse = new MoneyResponse(78623);

        when(moneyService.request(7685)).thenReturn(moneyResponse);

        manager.wager(wagerRequest);

        InOrder order = inOrder(stateStore, moneyService, archiver);

        order.verify(stateStore).recordWager(wagerRoundId, wagerId, 7685, gameId, exchangeRateId);
        order.verify(moneyService).request(7685);
        order.verify(stateStore).confirmWager(wagerRoundId, wagerId);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void shouldReturnBalanceFromResponse() throws Exception {
        wagerRequest = new WagerRequest(wagerRoundId.id(), wagerId.id(), 43, gameId.id(), exchangeRateId.id());
        moneyResponse = new MoneyResponse(57);

        when(moneyService.request(43)).thenReturn(moneyResponse);

        assertThat(manager.wager(wagerRequest), equalTo(new WagerResponse(BetResult.OK, 57)));
    }

    @Test
    public void shouldStoreStateBeforeAndAfterOutcome() throws Exception {
        outcomeRequest = new OutcomeRequest(wagerRoundId.id(), wagerId.id(), 654);
        moneyResponse = new MoneyResponse(78623);
        completedWagerRound = new CompletedWagerRound(wagerRoundId, ImmutableList.<CompletedWager>of(), gameId, exchangeRateId, 654);

        when(moneyService.win(654)).thenReturn(moneyResponse);
        when(stateStore.confirmOutcome(wagerRoundId, 654)).thenReturn(completedWagerRound);

        manager.outcome(outcomeRequest);

        InOrder order = inOrder(stateStore, moneyService, archiver);

        order.verify(stateStore).recordOutcome(wagerRoundId, 654);
        order.verify(moneyService).win(654);
        order.verify(stateStore).confirmOutcome(wagerRoundId, 654);
        order.verify(archiver).archive(completedWagerRound);
        order.verify(stateStore).recordArchival(wagerRoundId);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void shouldReturnBalanceFromMoneyServiceForOutcome() throws Exception {
        outcomeRequest = new OutcomeRequest(wagerRoundId.id(), wagerId.id(), 4654);
        moneyResponse = new MoneyResponse(765);

        when(moneyService.win(4654)).thenReturn(moneyResponse);

        assertThat(manager.outcome(outcomeRequest), equalTo(new OutcomeResponse(765)));
    }
}

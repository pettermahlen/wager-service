package com.williamsinteractive.casino.wager.core;

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
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;

import javax.inject.Inject;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class SynchronousWagerRoundManager implements WagerRoundManager {
    private static final Timer WAGER_TIMER = Metrics.newTimer(SynchronousWagerRoundManager.class, "wager");
    private static final Timer OUTCOME_TIMER = Metrics.newTimer(SynchronousWagerRoundManager.class, "outcome");

    private final WagerRoundStateStore wagerRoundStateStore;
    private final MoneyService moneyService;
    private final WagerRoundArchiver wagerRoundArchiver;

    @Inject
    public SynchronousWagerRoundManager(WagerRoundStateStore wagerRoundStateStore, MoneyService moneyService, WagerRoundArchiver wagerRoundArchiver) {
        this.wagerRoundStateStore = wagerRoundStateStore;
        this.moneyService = moneyService;
        this.wagerRoundArchiver = wagerRoundArchiver;
    }

    @Override
    public WagerResponse wager(WagerRequest request) {
        // TODO: this extraction should really be done in the Jersey layer
        Id<WagerRound> wagerRoundId = Id.of(request.getWageRoundId());
        Id<Wager> wagerId = Id.of(request.getWagerId());
        Id<Game> gameId = Id.of(request.getGameId());
        Id<ExchangeRate> exchangeRateId = Id.of(request.getExchangeRateId());

        // TODO: would be nice to have an easy way of defining Metrics Timers at this level as well. It's not too
        // hard to do explicitly, so here's an example of that.
        TimerContext context = WAGER_TIMER.time();

        try {
            return placeWager(wagerRoundId, wagerId, gameId, exchangeRateId, request.getAmount());
        }
        finally {
            context.stop();
        }
    }

    private WagerResponse placeWager(Id<WagerRound> wagerRoundId, Id<Wager> wagerId, Id<Game> gameId, Id<ExchangeRate> exchangeRateId, int amount) {
        wagerRoundStateStore.recordWager(wagerRoundId, wagerId, amount, gameId, exchangeRateId);
        MoneyResponse response = moneyService.request(amount);
        wagerRoundStateStore.confirmWager(wagerRoundId, wagerId);

        // TODO: at some point, the money service should be able to reject requests
        return new WagerResponse(BetResult.OK, response.getBalance());
    }

    @Override
    public OutcomeResponse outcome(OutcomeRequest request) {
        Id<WagerRound> wagerRoundId = Id.of(request.getWagerRoundId());

        TimerContext context = OUTCOME_TIMER.time();

        try {
            return handleOutcome(request, wagerRoundId);
        }
        finally {
            context.stop();
        }
    }

    private OutcomeResponse handleOutcome(OutcomeRequest request, Id<WagerRound> wagerRoundId) {
        wagerRoundStateStore.recordOutcome(wagerRoundId, request.getAmount());
        MoneyResponse response = moneyService.win(request.getAmount());
        CompletedWagerRound completedWagerRound = wagerRoundStateStore.confirmOutcome(wagerRoundId, request.getAmount());
        wagerRoundArchiver.archive(completedWagerRound);
        wagerRoundStateStore.recordArchival(wagerRoundId);

        return new OutcomeResponse(response.getBalance());
    }
}

package com.williamsinteractive.casino.wager.core;

import com.williamsinteractive.casino.wager.api.BetResult;
import com.williamsinteractive.casino.wager.api.MoneyResponse;
import com.williamsinteractive.casino.wager.api.WagerRequest;
import com.williamsinteractive.casino.wager.api.WagerResponse;
import com.williamsinteractive.casino.wager.api.OutcomeRequest;
import com.williamsinteractive.casino.wager.api.OutcomeResponse;
import com.yammer.metrics.annotation.Timed;

import javax.inject.Inject;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class SynchronousWagerRoundManager implements WagerRoundManager {

    private final WagerRoundStateStore wagerRoundStateStore;
    private final MoneyService moneyService;
    private final TransactionArchiver transactionArchiver;

    @Inject
    public SynchronousWagerRoundManager(WagerRoundStateStore wagerRoundStateStore,
                                        MoneyService moneyService,
                                        TransactionArchiver transactionArchiver) {
        this.wagerRoundStateStore = wagerRoundStateStore;
        this.moneyService = moneyService;
        this.transactionArchiver = transactionArchiver;
    }

    @Override
    @Timed
    public WagerResponse wager(WagerRequest request) {
        wagerRoundStateStore.record(request.getWageRoundId(), WageRoundState.REQUEST_MONEY);
        MoneyResponse response = moneyService.request(request.getAmount());
        wagerRoundStateStore.record(request.getWageRoundId(), WageRoundState.GOT_MONEY);

        // TODO: at some point, the money service should be able to reject requests
        return new WagerResponse(BetResult.OK, response.getBalance());
    }

    @Override
    @Timed
    public OutcomeResponse outcome(OutcomeRequest request) {
        wagerRoundStateStore.record(request.getWageRoundId(), WageRoundState.GOT_OUTCOME);
        MoneyResponse response = moneyService.win(request.getAmount());
        wagerRoundStateStore.record(request.getWageRoundId(), WageRoundState.OUTCOME_CONFIRMED);
        // TODO: need to get transaction data from wagerRoundStateStore instead of faking it like this
        transactionArchiver.archive(new Transaction(request.getWageRoundId(), 0));
        wagerRoundStateStore.record(request.getWageRoundId(), WageRoundState.ARCHIVED);

        return new OutcomeResponse(response.getBalance());
    }
}

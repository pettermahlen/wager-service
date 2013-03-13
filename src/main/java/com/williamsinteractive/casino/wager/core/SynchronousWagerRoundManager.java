package com.williamsinteractive.casino.wager.core;

import com.williamsinteractive.casino.wager.api.BetResult;
import com.williamsinteractive.casino.wager.api.MoneyResponse;
import com.williamsinteractive.casino.wager.api.OutcomeRequest;
import com.williamsinteractive.casino.wager.api.OutcomeResponse;
import com.williamsinteractive.casino.wager.api.WagerRequest;
import com.williamsinteractive.casino.wager.api.WagerResponse;
import com.williamsinteractive.casino.wager.model.Id;
import com.williamsinteractive.casino.wager.model.Wager;
import com.williamsinteractive.casino.wager.model.WagerRound;
import com.yammer.metrics.annotation.Timed;

import javax.inject.Inject;

import static com.williamsinteractive.casino.wager.core.WageRoundState.ARCHIVED;
import static com.williamsinteractive.casino.wager.core.WageRoundState.GOT_MONEY;
import static com.williamsinteractive.casino.wager.core.WageRoundState.GOT_OUTCOME;
import static com.williamsinteractive.casino.wager.core.WageRoundState.OUTCOME_CONFIRMED;
import static com.williamsinteractive.casino.wager.core.WageRoundState.REQUEST_MONEY;

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
    @Timed // TODO: this doesn't work, it needs something a little more complex..
    public WagerResponse wager(WagerRequest request) {
        Id<WagerRound> wagerRoundId = Id.of(request.getWageRoundId());
        Id<Wager> wagerId = Id.of(request.getTransactionId());

        wagerRoundStateStore.record(wagerRoundId, wagerId, REQUEST_MONEY, request.getAmount());
        MoneyResponse response = moneyService.request(request.getAmount());
        wagerRoundStateStore.record(wagerRoundId, wagerId, GOT_MONEY, request.getAmount());

        // TODO: at some point, the money service should be able to reject requests
        return new WagerResponse(BetResult.OK, response.getBalance());
    }

    @Override
    @Timed
    public OutcomeResponse outcome(OutcomeRequest request) {
        Id<WagerRound> wagerRoundId = Id.of(request.getWageRoundId());
        Id<Wager> wagerId = Id.of(request.getTransactionId());

        wagerRoundStateStore.record(wagerRoundId, wagerId, GOT_OUTCOME, request.getAmount());
        MoneyResponse response = moneyService.win(request.getAmount());
        wagerRoundStateStore.record(wagerRoundId, wagerId, OUTCOME_CONFIRMED, request.getAmount());
        // TODO: need to get transaction data from wagerRoundStateStore instead of faking it like this
        transactionArchiver.archive(new Transaction(request.getWageRoundId(), 0));
        wagerRoundStateStore.record(wagerRoundId, wagerId, ARCHIVED, request.getAmount());

        return new OutcomeResponse(response.getBalance());
    }
}

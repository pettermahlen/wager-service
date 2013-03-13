package com.williamsinteractive.casino.wager.core;

import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.Uninterruptibles;
import com.williamsinteractive.casino.wager.model.Id;
import com.williamsinteractive.casino.wager.model.Wager;
import com.williamsinteractive.casino.wager.model.WagerRound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcedureCallback;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class VoltWagerRoundStateStore implements WagerRoundStateStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoltWagerRoundStateStore.class);

    private final Client client;

    @Inject
    public VoltWagerRoundStateStore(Client client) {
        this.client = client;
    }

    @Override
    public void record(Id<WagerRound> wagerRoundId, Id<Wager> transactionId, WageRoundState state, long amount) {
        final SettableFuture<Boolean> resultFuture = callVolt(wagerRoundId, transactionId, state, amount);

        verifySuccess(resultFuture);
    }

    private SettableFuture<Boolean> callVolt(Id<WagerRound> wagerRoundId,
                                             Id<Wager> transactionId,
                                             WageRoundState state,
                                             long amount) {
        final SettableFuture<Boolean> resultFuture = SettableFuture.create();

        try {
            client.callProcedure(new WageRoundTransitionCallback(resultFuture), "RecordTransaction", wagerRoundId.getId(), transactionId.getId(), state.name(), amount);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultFuture;
    }

    private void verifySuccess(Future<Boolean> resultFuture) {
        try {
            boolean success = Uninterruptibles.getUninterruptibly(resultFuture);

            if (!success) {
                throw new RuntimeException("Call failed");
            }
        }
        catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static class WageRoundTransitionCallback implements ProcedureCallback {
        private final SettableFuture<Boolean> resultFuture;

        public WageRoundTransitionCallback(SettableFuture<Boolean> resultFuture) {
            this.resultFuture = resultFuture;
        }

        @Override
        public void clientCallback(ClientResponse clientResponse) throws Exception {
            if (isFailure(clientResponse)) {
                LOGGER.error("Procedure call failed {}", clientResponse.getStatusString());
                resultFuture.set(false);
            }
            else {
                resultFuture.set(true);
            }
        }

        private boolean isFailure(ClientResponse clientResponse) {
            // TODO: needs some kind of consistency check, too - probably best using the app status and app status string
            return clientResponse.getStatus() != ClientResponse.SUCCESS;
        }
    }
}

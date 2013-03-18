package com.williamsinteractive.casino.wager.resources;

import com.google.common.util.concurrent.SettableFuture;
import com.williamsinteractive.casino.wager.api.BetResult;
import com.williamsinteractive.casino.wager.api.OutcomeRequest;
import com.williamsinteractive.casino.wager.api.OutcomeResponse;
import com.williamsinteractive.casino.wager.api.WagerRequest;
import com.williamsinteractive.casino.wager.api.WagerResponse;
import com.williamsinteractive.casino.wager.core.WagerRoundManager;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class WagerResourceTest {
    WagerResource resource;

    WagerRoundManager wagerRoundManager;

    WagerRequest request;
    WagerResponse response;
    SettableFuture<WagerResponse> responseFuture;
    OutcomeRequest outcomeRequest;
    OutcomeResponse outcomeResponse;
    SettableFuture<OutcomeResponse> outcomeResponseFuture;

    @Before
    public void setUp()
        throws Exception {

        wagerRoundManager = mock(WagerRoundManager.class);

        resource = new WagerResource(wagerRoundManager);

        request = new WagerRequest(23487, 7658776, 764, 33434, 65523);
        response = new WagerResponse(BetResult.OK, 76);
        responseFuture = SettableFuture.create();

        outcomeRequest = new OutcomeRequest(732645, 7632, 8974);
        outcomeResponse = new OutcomeResponse(7865);
        outcomeResponseFuture = SettableFuture.create();

        when(wagerRoundManager.wager(request)).thenReturn(response);
        when(wagerRoundManager.outcome(outcomeRequest)).thenReturn(outcomeResponse);
    }

    @Test
    public void shouldCallWageRoundManagerWithWagerRequest()
        throws Exception {

        responseFuture.set(response);

        resource.place(request);

        verify(wagerRoundManager).wager(request);
    }

    @Test
    public void shouldReturnResponseFromWageRoundManager()
        throws Exception {

        responseFuture.set(response);

        assertThat(resource.place(request), equalTo(response));
    }

    @Test
    public void shouldCallWageRoundManagerWithOutcomeRequest()
        throws Exception {

        outcomeResponseFuture.set(outcomeResponse);

        resource.outcome(outcomeRequest);

        verify(wagerRoundManager).outcome(outcomeRequest);
    }

    @Test
    public void shouldReturnOutcomeResponseFromWageRoundManager()
        throws Exception {

        outcomeResponseFuture.set(outcomeResponse);

        assertThat(resource.outcome(outcomeRequest), equalTo(outcomeResponse));
    }
}

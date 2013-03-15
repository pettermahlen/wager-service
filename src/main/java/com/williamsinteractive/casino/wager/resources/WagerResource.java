package com.williamsinteractive.casino.wager.resources;

import com.williamsinteractive.casino.wager.api.OutcomeRequest;
import com.williamsinteractive.casino.wager.api.OutcomeResponse;
import com.williamsinteractive.casino.wager.api.WagerRequest;
import com.williamsinteractive.casino.wager.api.WagerResponse;
import com.williamsinteractive.casino.wager.core.WagerRoundManager;
import com.yammer.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * TODO: this class should actually convert the JSON API stuff into more proper internal API stuff - so rather than
 * exposing the longs, etc., used in the over-the-wire communication, the WagerRoundManager API should take the Id&lt;T&gt;
 * style parameters.
 *
 * @author Petter Måhlén
 */
@Path("/wager")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON})
public class WagerResource {
    private final WagerRoundManager wagerRoundManager;

    @Inject
    public WagerResource(WagerRoundManager wagerRoundManager) {
        this.wagerRoundManager = wagerRoundManager;
    }

    @POST
    @Timed
    @Path("/place")
    public WagerResponse place(@Valid WagerRequest wagerRequest) {
        return wagerRoundManager.wager(wagerRequest);
    }

    @POST
    @Timed
    @Path("/outcome")
    public OutcomeResponse outcome(OutcomeRequest request) {
        return wagerRoundManager.outcome(request);
    }
}

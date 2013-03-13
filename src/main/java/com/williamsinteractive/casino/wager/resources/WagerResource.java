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
 * TODO: document!
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
        return null;
    }
}

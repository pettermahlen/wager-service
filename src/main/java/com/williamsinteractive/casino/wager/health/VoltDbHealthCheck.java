package com.williamsinteractive.casino.wager.health;

import com.yammer.metrics.core.HealthCheck;
import org.voltdb.client.Client;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class VoltDbHealthCheck extends HealthCheck {
    private final Client client;

    public VoltDbHealthCheck(Client client) {
        super("voltdb");
        this.client = client;
    }

    @Override
    protected Result check() throws Exception {
        throw new UnsupportedOperationException();
    }
}

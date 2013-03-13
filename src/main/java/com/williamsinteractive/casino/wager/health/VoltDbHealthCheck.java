package com.williamsinteractive.casino.wager.health;

import com.yammer.metrics.core.HealthCheck;
import org.voltdb.client.Client;

import javax.inject.Inject;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class VoltDbHealthCheck extends HealthCheck {
    private final Client client;

    @Inject
    public VoltDbHealthCheck(Client client) {
        super("voltdb");
        this.client = client;
    }

    @Override
    protected Result check() throws Exception {
        if (client.getConnectedHostList().isEmpty()) {
            return Result.unhealthy("Not connected to VoltDB!");
        }

        // TODO: the SZ Volt health checks actually called a stored procedure to check that the deployed
        // catalogue version was a supported one. That's a bit better than this.
        return Result.healthy();
    }
}

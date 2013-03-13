package com.williamsinteractive.casino.wager;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class WagerService extends Service<WagerServiceConfiguration> {
    @Override
    public void initialize(Bootstrap<WagerServiceConfiguration> betServiceConfigurationBootstrap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run(WagerServiceConfiguration wagerServiceConfiguration, Environment environment)
        throws Exception {
        throw new UnsupportedOperationException();
        // TODO: instantiate object graph
        // TODO: add resource/s
        // TODO: add health check
    }
}

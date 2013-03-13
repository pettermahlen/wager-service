package com.williamsinteractive.casino.wager;

import com.williamsinteractive.casino.wager.resources.WagerResource;
import com.williamsinteractive.casino.wager.wiring.WagerServiceModule;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import dagger.ObjectGraph;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class WagerService extends Service<WagerServiceConfiguration> {
    @Override
    public void initialize(Bootstrap<WagerServiceConfiguration> betServiceConfigurationBootstrap) {
        betServiceConfigurationBootstrap.setName("wager-service");
    }

    @Override
    public void run(WagerServiceConfiguration wagerServiceConfiguration, Environment environment)
        throws Exception {
        // TODO: instantiate object graph
        // TODO: add resource/s
        // TODO: add health check

        ObjectGraph graph = ObjectGraph.create(new WagerServiceModule(wagerServiceConfiguration));

        environment.addResource(graph.get(WagerResource.class));
    }

    public static void main(String[] args) throws Exception{
        new WagerService().run(args);
    }
}

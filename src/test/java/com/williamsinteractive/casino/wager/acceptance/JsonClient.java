package com.williamsinteractive.casino.wager.acceptance;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;

/**
 * TODO: this class is duplicated in the load generator - it should live in a library!
 *
 * @author Petter Måhlén
 */
public class JsonClient<Q, S> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonClient.class);

    private final Class<S> responseType;
    private final WebResource resource;

    public JsonClient(Class<S> responseType, String url) {
        this.responseType = responseType;
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);

        resource = client.resource(url);
    }

    public S call(final Q request) {
        LOGGER.debug("calling {} with message: {}",  resource, request);

        S response = resource
            .type(MediaType.APPLICATION_JSON_TYPE)
            .accept(MediaType.APPLICATION_JSON_TYPE)
            .post(responseType, request);

        LOGGER.debug("got response: {}", response);

        return response;
    }
}

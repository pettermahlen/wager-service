package com.williamsinteractive.casino.wager;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class WagerServiceConfiguration extends Configuration {
    @NotNull
    @Max(65535)
    @JsonProperty
    private int voltPort = 21212;

    @NotEmpty
    @JsonProperty
    private String voltHost;

    public int getVoltPort() {
        return voltPort;
    }

    public String getVoltHost() {
        return voltHost;
    }
}

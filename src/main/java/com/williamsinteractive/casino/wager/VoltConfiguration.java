package com.williamsinteractive.casino.wager;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class VoltConfiguration {
    @NotNull
    @Max(65535)
    @JsonProperty
    private int port = 21212;

    @NotEmpty
    @JsonProperty
    private String host;

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }
}

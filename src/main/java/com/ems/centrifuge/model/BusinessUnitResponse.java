package com.ems.centrifuge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusinessUnitResponse {

    private final Boolean authorized;

    public BusinessUnitResponse(@JsonProperty("authorized") Boolean authorized) {
        this.authorized = authorized;
    }

    public Boolean isAuthorized() {
        return authorized;
    }
}

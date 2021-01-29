package com.ems.centrifuge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NumberPlateResponse {

    private final String registrationNumber;

    public NumberPlateResponse(@JsonProperty("registrationNumber") String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
}

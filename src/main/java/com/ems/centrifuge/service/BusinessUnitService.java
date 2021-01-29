package com.ems.centrifuge.service;

import com.ems.centrifuge.model.BusinessUnitResponse;
import com.ems.centrifuge.model.NumberPlateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class BusinessUnitService {

    @Value("${server.url.businesslogicunit}")
    private String businessLogicUnitURL;

    public BusinessUnitResponse send(NumberPlateResponse numberPlateResponse) {

        WebClient webClient = WebClient.builder().build();

        try {
            return webClient
                    .post()
                    .uri(businessLogicUnitURL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(numberPlateResponse)
                    .retrieve()
                    .bodyToMono(BusinessUnitResponse.class)
                    .block();
        }
        catch(WebClientResponseException err) {
            return new BusinessUnitResponse(false);
        }


    }

    private MultiValueMap<String, HttpEntity<?>> bodyBuilder(String registrationNumber) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("registrationNumber", registrationNumber);
        return builder.build();
    }

}

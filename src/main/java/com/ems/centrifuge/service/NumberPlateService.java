package com.ems.centrifuge.service;

import com.ems.centrifuge.model.NumberPlateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.io.File;

@Service
public class NumberPlateService {

    @Value("${server.url.numberplate}")
    private String numberPlateServerURL;

    public NumberPlateResponse send(File vehicleImage) {

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(
                HttpClient.create().followRedirect(true)
        )).build();

        NumberPlateResponse response = webClient.post()
                .uri(numberPlateServerURL)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(fromFile(vehicleImage)))
                .retrieve()
                .bodyToMono(NumberPlateResponse.class)
                .block();

        return response;
    }

    private MultiValueMap<String, HttpEntity<?>> fromFile(File file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new FileSystemResource(file));
        return builder.build();
    }
}

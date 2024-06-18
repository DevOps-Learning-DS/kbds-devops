package com.kbds.devops.webclient.app.rest;

import com.kbds.devops.webclient.app.clients.WebMvcFeignClient;
import com.kbds.devops.webflux.app.model.Member;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.util.List;

public class FeignTemplateClient extends AbstractHttpClient {

    private WebMvcFeignClient webMvcFeignClient;

    public FeignTemplateClient() {
        webMvcFeignClient = (WebMvcFeignClient) Feign.builder()
                .decoder(new JacksonDecoder(objectMapper))
                .encoder(new JacksonEncoder(objectMapper))
                .contract(new SpringMvcContract())
                .target(WebMvcFeignClient.class,"http://localhost:8080");
    }
    @Override
    public Member getMember() {
        return webMvcFeignClient.getMember(1l);
    }

    @Override
    public List<Member> findBySurname() {
        return webMvcFeignClient.findBySurname("P");
    }
}

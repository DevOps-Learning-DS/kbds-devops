package com.kbds.devops.webclient.app.clients;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.kbds.devops.webflux.app.model.Member;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import static org.slf4j.LoggerFactory.getLogger;



class WebMvcFeignClientTest {
    private static final Logger logger = getLogger(WebMvcFeignClientTest.class);
    @Test
    void getMember() {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy.SNAKE_CASE);
        WebMvcFeignClient webMvcFeignClient = Feign.builder()
                .decoder(new JacksonDecoder(objectMapper))
                .encoder(new JacksonEncoder(objectMapper))
                .contract(new SpringMvcContract())
                .target(WebMvcFeignClient.class,"http://localhost:8080");

        Member member = webMvcFeignClient.getMember(1l);
        logger.info("Member={}",member.toString() );
    }
}
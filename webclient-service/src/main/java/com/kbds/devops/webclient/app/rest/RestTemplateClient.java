package com.kbds.devops.webclient.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbds.devops.webflux.app.config.CommonConfig;
import com.kbds.devops.webflux.app.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestTemplateClient extends HttpClientTemplate {
    private RestTemplate restTemplate;

    public RestTemplateClient() {
        restTemplate = new RestTemplate();
        setHttpClientName("RestTemplate");
        List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();
        messageConverterList.add( new ByteArrayHttpMessageConverter());
        messageConverterList.add( new StringHttpMessageConverter());
        messageConverterList.add( new FormHttpMessageConverter());
        messageConverterList.add( new MappingJackson2HttpMessageConverter(objectMapper));
        restTemplate.setMessageConverters(messageConverterList);
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080/webmvc"));
    }

    @Override
    public Member getMember() {
        String id ="1";
        try {
            ResponseEntity<Member> responseEntity = restTemplate.getForEntity("/{id}", Member.class,id);
            return responseEntity.getBody();
        }
        catch(HttpClientErrorException httpClientEx) {
            if( httpClientEx.getStatusCode() == HttpStatus.NOT_FOUND ){
                logger.info("Failed to get id {}", id);
            }
            else {
                logger.error("Error is = {}", httpClientEx.getResponseBodyAsString());
            }
        }
            catch(HttpServerErrorException httpServerEx) {
            logger.error("Error is = {}", httpServerEx.getResponseBodyAsString());
        }

        return null;
    }


    public void runGetForEntity() {
        String id ="1";
        try {

            Member member = restTemplate.getForObject("/{id}", Member.class, id);
            ResponseEntity<?> responseEntityFind= restTemplate.getForEntity(
                    UriComponentsBuilder.fromPath("/find").queryParam("surname", "P").build().toString()
                    , List.class );
            List<Member> memberList = (List<Member>)responseEntityFind.getBody();
            logger.info("memberList={}",memberList);
        }
        catch(HttpClientErrorException httpClientEx) {
            if( httpClientEx.getStatusCode() == HttpStatus.NOT_FOUND ){
                logger.info("Failed to get id {}", id);
            }
            else {
                logger.error("Error is = {}", httpClientEx.getResponseBodyAsString());
            }
        }
        catch(HttpServerErrorException httpServerEx) {
            logger.error("Error is = {}", httpServerEx.getResponseBodyAsString());
        }
    }

    public void runPostForEntity() {
        String memberString ="1";
        try {
            memberString = objectMapper.writeValueAsString(
                    new Member(0l,"BK","Park22",34
                            , LocalDateTime.of(2024, 6, 10, 11, 24, 30))
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> postRequest = new HttpEntity<>(memberString, headers);
            ResponseEntity<Member> responseEntity =
                    restTemplate.postForEntity("/new", postRequest, Member.class);
            logger.info("Member={}",responseEntity.getBody());

            ResponseEntity<?> responseEntityFind= restTemplate.getForEntity(
                    UriComponentsBuilder.fromPath("/find").queryParam("surname", "P").build().toString()
                    , List.class );
            List<Member> memberList = (List<Member>)responseEntityFind.getBody();
            logger.info("memberList={}",memberList);
        }
        catch(HttpClientErrorException httpClientEx) {
            if( httpClientEx.getStatusCode() == HttpStatus.NOT_FOUND ){
                logger.info("Failed to get id {}");
            }
            else {
                logger.error("Error is = {}", httpClientEx.getResponseBodyAsString());
            }
        }
        catch(HttpServerErrorException httpServerEx) {
            logger.error("Error is = {}", httpServerEx.getResponseBodyAsString());
        }
        catch (Exception ce) {
            logger.error(ce.getMessage(), ce);
        }
    }

    public void runExchange() {
        String id ="4";
        try {
            ResponseEntity<Member> responseEntity = restTemplate.getForEntity("/{id}", Member.class, id);
            logger.info("Member={}", responseEntity.getBody());
        }
        catch(HttpClientErrorException httpClientEx) {
            if( httpClientEx.getStatusCode() == HttpStatus.NOT_FOUND ){
                logger.info("Failed to get id {}", id);
            }
            else {
                logger.error("Error is = {}", httpClientEx.getResponseBodyAsString());
            }
        }
        catch(HttpServerErrorException httpServerEx) {
            logger.error("Error is = {}", httpServerEx.getResponseBodyAsString());
        }
    }


}

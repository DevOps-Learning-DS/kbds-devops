package com.kbds.devops.webclient.app.rest;

import com.kbds.devops.webflux.app.model.Member;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

public class RestTemplateClient extends AbstractHttpClient {
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
            return restTemplate.getForObject("/{id}", Member.class,"1");
    }

    @Override
    public List<Member> findBySurname() {
        return (List<Member>)restTemplate.getForObject(
                    UriComponentsBuilder
                            .fromPath("/find")
                            .queryParam("surname", "P")
                            .build().toString()
                ,List.class );
    }

    @Override
    public Member createMember(Member member) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Member> postRequest = new HttpEntity<>(member, headers);
        return restTemplate.postForObject("/new", postRequest, Member.class);
    }
}

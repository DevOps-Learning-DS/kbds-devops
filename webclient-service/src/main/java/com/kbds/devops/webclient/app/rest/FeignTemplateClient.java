package com.kbds.devops.webclient.app.rest;

import com.kbds.devops.webclient.app.clients.WebMvcFeignClient;
import com.kbds.devops.webflux.app.model.Member;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.util.List;

public class FeignTemplateClient extends AbstractHttpClient {

    private final WebMvcFeignClient webMvcFeignClient;

    public FeignTemplateClient() {
        webMvcFeignClient = Feign.builder()
                .decoder(new JacksonDecoder(objectMapper))
                .encoder(new JacksonEncoder(objectMapper))
                .contract(new SpringMvcContract())
                .target(WebMvcFeignClient.class,"http://localhost:8080");
        setHttpClientName("FeignClient");
    }
    @Override
    public Member getMember() {
        return webMvcFeignClient.getMember(1l);
    }

    @Override
    public List<Member> findBySurname() {
        return webMvcFeignClient.findBySurname("P");
    }

    @Override
    public Member createMember(Member member) {
        return webMvcFeignClient.create(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return webMvcFeignClient.getAllMembers();
    }

    @Override
    public List<Member> getListMembers(List<Long> memberIdList) {
        return webMvcFeignClient.getListMembers(memberIdList);
    }
}

package com.kbds.devops.webclient.app.rest;

import com.kbds.devops.webflux.app.model.Member;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class WebTemplateClient extends AbstractHttpClient {
    private WebClient webClient;
    public WebTemplateClient() {
        webClient = WebClient.builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(
                            new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(
                            new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));

                })
                .baseUrl("http://localhost:8080/webflux")
                .build();
        setHttpClientName("WebClient");
    }
    @Override
    public Member getMember() {
        Mono<Member> memberMono = webClient.get().uri("/{id}", "1")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Member.class);
        return memberMono.block();
    }

    @Override
    public List<Member> findBySurname() {
        return webClient.get().uri( uriBuilder->uriBuilder.path("/find")
                .queryParam("surname", "P")
                .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Member>>() {})
                .block();
    }

    @Override
    public Member createMember(Member member) {
        return webClient.post()
                .uri("/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(member)
                .retrieve()
                .bodyToMono(Member.class)
                .block();
    }

    @Override
    public List<Member> getAllMembers() {
        return webClient.get().uri("/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Member>>() {})
                .block();
    }

    @Override
    public List<Member> getListMembers(List<Long> memberIdList) {
        return webClient.post().uri("/members")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(memberIdList)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Member>>() {})
                .block();
    }
}

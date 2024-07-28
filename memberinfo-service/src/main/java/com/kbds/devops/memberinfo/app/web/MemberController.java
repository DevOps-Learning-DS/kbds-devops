package com.kbds.devops.memberinfo.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class MemberController {

    @Autowired
    private WebClient webClient;

    @GetMapping("/api/hello")
    public String hello(Principal principal) {
        return "Hello " +principal.getName()+", Welcome to Daily Code Buffer!!";
    }

    @GetMapping("/api/events")
    public String[] users(
            @RegisteredOAuth2AuthorizedClient("client-oidc")
            OAuth2AuthorizedClient client){
        return this.webClient
                .get()
                .uri("http://localhost:8087/api/events")
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String[].class)
                .block();
    }
}

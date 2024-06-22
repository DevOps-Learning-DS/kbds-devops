package com.kbds.devops.webflux.app.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbds.devops.webflux.app.model.Member;
import com.kbds.devops.webflux.app.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = WebFluxController.class)
@Import(MemberService.class)
class WebFluxControllerTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WebFluxController webFluxController;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    WebTestClient webTestClient;

    private static final Logger logger = getLogger(WebFluxControllerTest.class);

    private static final String URL_PREFIX = "/webflux";

    @BeforeEach
    public void beforeEach() {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @Test
    void getMember() throws Exception {
        Member expected =
                new Member(1l,"BK","Park",50
                ,LocalDateTime.of(2024, 6, 10, 11, 24, 30));

        EntityExchangeResult<Member> result =
                webTestClient.get().uri(URL_PREFIX + "/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Member.class)
                .returnResult();

        assertThat(result.getResponseBody()).isEqualTo(expected);
    }

    @Test
    void findBySurname() {
        List<Member> expected = new ArrayList<>(memberService.getAllMembers());
        expected.remove(1);

        String searchWord = "P";
        EntityExchangeResult<List<Member>> result = webTestClient
                .get()
                .uri( uriBuilder->uriBuilder.path(URL_PREFIX+"/find")
                        .queryParam("surname", searchWord)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Member>>() {})
                .returnResult();

        assertThat(result.getResponseBody()).isEqualTo(expected);
    }

    @Test
    void createMember() throws Exception  {
        Member expected = new Member(4l,"BK","Park22",34
                ,LocalDateTime.of(2024, 6, 10, 11, 24, 30));

        Member member = new Member(0l,"BK","Park22",34
                ,LocalDateTime.of(2024, 6, 10, 11, 24, 30));

        EntityExchangeResult<Member> result = webTestClient
                .post()
                .uri(URL_PREFIX+"/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(member)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Member.class)
                .returnResult();

        assertThat(result.getResponseBody()).isEqualTo(expected);
    }

    @Test
    public void getAllMembers() throws Exception  {
        List<Member> expected =  memberService.getAllMembers();

        EntityExchangeResult<List<Member>> result = webTestClient
                .get()
                .uri(URL_PREFIX+"/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Member>>() {})
                .returnResult();

        assertThat(result.getResponseBody()).isEqualTo(expected);
    }


    @Test
    public void getListMembers() throws Exception {
        List<Long> memberIdList = Arrays.asList(1l, 2l);
        List<Member> expected = new ArrayList<>( memberService.getAllMembers());
        if( expected.size() > 2) expected.remove(2);

        EntityExchangeResult<List<Member>> result = webTestClient
                .post()
                .uri(URL_PREFIX+"/members")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(memberIdList)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Member>>() {})
                .returnResult();

        assertThat(result.getResponseBody()).isEqualTo(expected);
    }
}
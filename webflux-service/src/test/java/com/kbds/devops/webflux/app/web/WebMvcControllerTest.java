package com.kbds.devops.webflux.app.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbds.devops.webflux.app.config.CommonConfig;
import com.kbds.devops.webflux.app.model.Member;
import com.kbds.devops.webflux.app.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WebMvcController.class)
@Import({MemberService.class, CommonConfig.class})
class WebMvcControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberService memberService;

    private static final Logger logger = getLogger(WebMvcControllerTest.class);

    private static final String URL_PREFIX = "/webmvc";

    @Test
    void getMember() throws Exception {
        String expected = objectMapper.writeValueAsString(
                new Member(1l,"BK","Park",50
                        ,LocalDateTime.of(2024, 6, 10, 11, 24, 30)
                )) ;
        MvcResult result = mockMvc.perform(get(URL_PREFIX + "/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    void findBySurname() throws Exception {
        List<Member> memberList = memberService.getAllMembers();
        memberList.remove(1);
        String expected = objectMapper.writeValueAsString(memberList);
        MvcResult result = mockMvc.perform(
                        get(URL_PREFIX + "/find")
                                .queryParam("surname", "P")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).isEqualTo(expected);
    }

    @Test
    void createMember() throws Exception  {
        String memberString = "";
        try {
            memberString = objectMapper.writeValueAsString(
                    new Member(0l,"BK","Park22",34
                            ,LocalDateTime.of(2024, 6, 10, 11, 24, 30))
                    );
        }
        catch(Exception ce) {
            Assertions.fail(ce.getMessage());
        }

        MvcResult result = mockMvc.perform(
                    post(URL_PREFIX + "/new")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(memberString)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).isNotEmpty();
    }

    @Test
    public void getAllMembers() throws Exception  {
        String expected =  objectMapper.writeValueAsString(memberService.getAllMembers());

        MvcResult result = mockMvc.perform(get(URL_PREFIX+ "/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }


    @Test
    public void getListMembers() throws Exception {
        List<Member>  memberList = memberService.getAllMembers();
        memberList.remove(2);
        String expected =  objectMapper.writeValueAsString(memberList);

        MvcResult result = mockMvc.perform(post(URL_PREFIX + "/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( objectMapper.writeValueAsString( Arrays.asList(1,2)) )
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }
}
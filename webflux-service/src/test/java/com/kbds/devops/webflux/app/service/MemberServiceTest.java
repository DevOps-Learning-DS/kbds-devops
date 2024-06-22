package com.kbds.devops.webflux.app.service;

import com.kbds.devops.webflux.app.model.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;


@SpringBootTest
class MemberServiceTest {
    private static final Logger logger = getLogger(MemberServiceTest.class);

    @Autowired
    MemberService memberService;
    @BeforeAll
    public static void beforeAll() {
    }

    @Test
    void getMember() {
        Member expectedMember =
                new Member(1l,"BK","Park",50,LocalDateTime.of(2024, 6, 10, 11, 24, 30));

      //  when( memberService.getMember(1l)).thenReturn(Optional.of(expectedMember));

        Member member = memberService.getMember(1l).get();
        assertThat(member).isEqualTo(expectedMember);
    }

    @Test
    void create() {
        Member expectedMember =
                new Member(4l,"BK","Park11",50,LocalDateTime.of(2024, 6, 10, 11, 24, 30));
        ;

        Member newMember =
                new Member(0l,"BK","Park11",50,LocalDateTime.of(2024, 6, 10, 11, 24, 30));

        Member member = memberService.create(newMember);

        assertThat(expectedMember).isEqualTo(member);
    }
}
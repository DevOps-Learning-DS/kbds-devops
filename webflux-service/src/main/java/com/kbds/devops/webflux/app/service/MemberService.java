package com.kbds.devops.webflux.app.service;

import com.kbds.devops.webflux.app.model.Member;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MemberService {
    private static final Logger logger = getLogger(MemberService.class);

    public List<Member> memberList = new ArrayList<>();;
    @PostConstruct
    public void init()  {
        memberList.add(new Member(1l,"BK","Park",50,LocalDateTime.of(2024, 6, 10, 11, 24, 30)));
        memberList.add(new Member(2l,"sdf","df",11,LocalDateTime.of(2014, 6, 10, 11, 24, 30)));
        memberList.add(new Member(3l,"sfd","P12312ark",43,LocalDateTime.of(2023, 6, 10, 11, 24, 30)));
    }

    public Optional<Member> getMember(Long id) {
        return memberList.stream().filter(member->member.getId() == id).findFirst();
    }

    public Member create(Member member) {
        Optional<Member> optionalMember = memberList.stream()
                .max((member1, member2)->(int)member1.getId()-(int)member2.getId());

        if( optionalMember.isPresent()) {
            member.setId(optionalMember.get().getId()+1);
        }
        else {
            member.setId(1);
        }

        memberList.add(member);
        return member;
    }

    public List<Member> getAllMembers() {
        return memberList;
    }

    public List<Member> findBySurname(String surname) {
        return memberList.stream().filter(member->member.getSurname().contains(surname))
                .collect(Collectors.toList());

    }
}

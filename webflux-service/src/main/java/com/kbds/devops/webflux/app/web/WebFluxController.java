package com.kbds.devops.webflux.app.web;


import com.kbds.devops.webflux.app.model.Member;
import com.kbds.devops.webflux.app.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/webflux")
public class WebFluxController {
    private static Logger logger = LoggerFactory.getLogger(WebFluxController.class);

    @Autowired
    private MemberService memberService;

    @GetMapping("/{id}")
    @ResponseBody
    public Mono<ResponseEntity<Member>> getMember(@PathVariable Long id) {
        return Mono.just(id).map(memberId->{
            Optional<Member> memberOptional  = memberService.getMember(memberId);
            return memberOptional.isPresent()?ResponseEntity.ok(memberOptional.get())
                    :ResponseEntity.notFound().build();
        });
    }

    @GetMapping("/find")
    @ResponseBody
    public Mono<ResponseEntity<List<Member>>> findBySurname(@RequestParam("surname") String surname) {
        return Mono.just(surname).map(searchWord-> {
            List<Member> memberList = memberService.findBySurname(surname);
            return memberList.isEmpty() ? ResponseEntity.notFound().build()
                    : ResponseEntity.ok(memberList);
        });
    }

    @PostMapping("/new")
    @ResponseBody
    public Mono<ResponseEntity<Member>> createMember(@RequestBody Member member) {
        return Mono.just(member).map(newMember->ResponseEntity.ok(memberService.create(newMember)));
    }

    @GetMapping("/all")
    @ResponseBody
    public Mono<ResponseEntity<List<Member>>> getAllMembers() {
        return Mono.just("nodata").map(noData->ResponseEntity.ok(memberService.getAllMembers()));
    }

    @PostMapping("/members")
    @ResponseBody
    public Flux<Member> getListMembers(@RequestBody List<Long> memberIdList) {
        return Flux.just( memberIdList.toArray(new Long[0]))
                .map(memberId->memberService.getMember(memberId).orElseGet(Member::new));
    }
}

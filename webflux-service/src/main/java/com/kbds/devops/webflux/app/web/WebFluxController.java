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
        logger.info("id={}", id);
        return Mono.just(id).map(memberId->{
            Optional<Member> memberOptional  = memberService.getMember(memberId);
            return memberOptional.isPresent()?ResponseEntity.ok(memberOptional.get())
                    :ResponseEntity.notFound().build();
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

    @GetMapping("/members")
    @ResponseBody
    public Flux<ResponseEntity<Member>> getListMembers(@RequestBody List<Long> memberIdList) {
        return Flux.just( memberIdList.toArray(new Long[0])).map(memberId->{
            Optional<Member> memberOptional  = memberService.getMember(memberId);
            return memberOptional.isPresent()?ResponseEntity.ok(memberOptional.get())
                    :ResponseEntity.notFound().build();
        });
    }
}

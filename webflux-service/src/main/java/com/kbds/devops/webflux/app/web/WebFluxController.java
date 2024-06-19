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
    @Autowired
    private MemberService memberService;

    @GetMapping("/{id}")
    @ResponseBody
    public Mono<Member> getMember(@PathVariable Long id) {
        return Mono.just(id).map(memberId->memberService.getMember(memberId).orElseGet(null));
    }

    @GetMapping("/find")
    @ResponseBody
    public Mono<List<Member>> findBySurname(@RequestParam("surname") String surname) {
        return Mono.just(surname).map(memberService::findBySurname);
    }

    @PostMapping("/new")
    @ResponseBody
    public Mono<Member> createMember(@RequestBody Member member) {
        return Mono.just(member).map(memberService::create);
    }

    @GetMapping("/all")
    @ResponseBody
    public Mono<List<Member>> getAllMembers() {
        return Mono.just("nodata").map(nodata->memberService.getAllMembers());
    }

    @PostMapping("/members")
    @ResponseBody
    public Flux<Member> getListMembers(@RequestBody List<Long> memberIdList) {
        return Flux.just( memberIdList.toArray(new Long[0]))
                .map(memberService::getMember)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }
}

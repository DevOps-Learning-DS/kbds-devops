package com.kbds.devops.webflux.app.web;

import com.kbds.devops.webflux.app.model.Member;
import com.kbds.devops.webflux.app.service.MemberService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/webmvc")
public class WebMvcController {

    private static final Logger logger = getLogger(WebMvcController.class);
    @Autowired
    private MemberService memberService;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        Optional<Member> memberOptional  = memberService.getMember(id);
        return memberOptional.isPresent()?ResponseEntity.ok(memberOptional.get())
                        :ResponseEntity.notFound().build();
    }

    @GetMapping("/find")
    @ResponseBody
    public ResponseEntity<List<Member>> findBySurname(@RequestParam("surname") String surname) {
        List<Member> memberList = memberService.findBySurname(surname);
        return memberList.isEmpty()? ResponseEntity.notFound().build()
                :ResponseEntity.ok(memberList);
    }

    @PostMapping("/new")
    @ResponseBody
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.create(member));
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @PostMapping("/members")
    @ResponseBody
    public ResponseEntity<List<Member>> getListMembers(@RequestBody List<Long> memberIdList) {
        List<Member> memberList =
                memberIdList.stream()
                        .map(memberService::getMember)
                        .filter(Optional::isPresent)
                        .collect( ArrayList::new
                                , (list, optionalMember)->list.add(optionalMember.get())
                                , ArrayList::addAll
                        );

        return ResponseEntity.ok(memberList);
    }

    private AtomicInteger counter = new AtomicInteger();

    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    @GetMapping(value="/bench",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String bench() {
        try {
            Thread.sleep(500l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("Total={}, Active={}", threadMXBean.getThreadCount(), Thread.activeCount());
        return "dataCount["+ counter.getAndIncrement() +"]";
    }

}

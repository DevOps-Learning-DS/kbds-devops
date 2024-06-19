package com.kbds.devops.webflux.app.web;

import com.kbds.devops.webflux.app.model.Member;
import com.kbds.devops.webflux.app.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/webmvc")
public class WebMvcController {
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
}

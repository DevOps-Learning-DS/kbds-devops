package com.kbds.devops.webflux.app.web;

import com.kbds.devops.webflux.app.model.Member;
import com.kbds.devops.webflux.app.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/webmvc")
public class WebMvcController {
    private static Logger logger = LoggerFactory.getLogger(WebMvcController.class);

    @Autowired
    private MemberService memberService;

    @GetMapping("/{id}")
    @ResponseBody
    public Member getMember(@PathVariable Long id) {

        return null;
    }

    @PostMapping("/new")
    @ResponseBody
    public Object createMember(@RequestBody Member member) {
        logger.info("member={}", member.toString());
        return "test";
    }
}

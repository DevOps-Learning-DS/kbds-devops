package com.kbds.devops.webclient.app.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.kbds.devops.webflux.app.model.Member;

import java.util.List;

@FeignClient(value="webmvcClient")
@RequestMapping("/webmvc")
public interface WebMvcFeignClient {
    @GetMapping("/{id}")
    Member getMember(@PathVariable("id") Long id);

    @GetMapping("/find")
    List<Member> findBySurname(@RequestParam("surname") String surname);

    @PostMapping("/new")
    Member create(@RequestBody Member member);
}

package com.kbds.devops.webclient.app.clients;


import com.kbds.devops.webflux.app.model.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value="webmvcClient")
@RequestMapping("/webmvc")
public interface WebMvcFeignClient {
    @GetMapping("/{id}")
    Member getMember(@PathVariable("id") Long id);

    @GetMapping("/find")
    List<Member> findBySurname(@RequestParam("surname") String surname);

    @PostMapping(value="/new", consumes = "application/json")
    Member create(@RequestBody Member member);

    @GetMapping("/all")
    public List<Member> getAllMembers();

    @PostMapping(value="/members", consumes = "application/json")
    public List<Member> getListMembers(@RequestBody List<Long> memberIdList);
}

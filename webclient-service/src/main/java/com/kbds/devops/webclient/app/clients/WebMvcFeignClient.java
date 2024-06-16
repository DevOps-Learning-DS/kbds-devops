package com.kbds.devops.webclient.app.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kbds.devops.webflux.app.model.Member;



@FeignClient(value="webmvcClient")
@RequestMapping("/webmvc")
public interface WebMvcFeignClient {
    //@GetMapping("/{id}")
    @GetMapping("/{id}")
    Member getMember(@PathVariable("id") Long id);
}

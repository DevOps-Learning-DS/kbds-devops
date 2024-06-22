package com.kbds.devops.auth.app.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/auth")
    public String auth() {
        return "auth";
    }

    @GetMapping("/token")
    public String token() {
        return "token";
    }

    @GetMapping("/refresh")
    public String refresh() {
        return "refresh";
    }
}
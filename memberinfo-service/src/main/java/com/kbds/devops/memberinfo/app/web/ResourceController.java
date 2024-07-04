package com.kbds.devops.memberinfo.app.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    private static Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @GetMapping("/authPage")
    public String authorizedPage() {
        logger.info("authorizationCode");
        return "authorization code";
    }

    @GetMapping("/message")
    public String printMessage(@RequestParam String message) {
        return message;
    }
}

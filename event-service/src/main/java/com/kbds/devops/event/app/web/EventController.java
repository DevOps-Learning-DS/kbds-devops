package com.kbds.devops.event.app.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
    @GetMapping("/api/events")
    public String[] getUser() {
        return new String[]{"New Event 1", "New Event 2","New Event 3"};
    }
}

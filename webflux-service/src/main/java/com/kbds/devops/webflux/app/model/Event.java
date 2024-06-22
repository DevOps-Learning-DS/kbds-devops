package com.kbds.devops.webflux.app.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("event")
public class Event {

    @Id
    private String id;
    private String title;
    private String eventType;
    private String eventTarget;
    private String content;
}

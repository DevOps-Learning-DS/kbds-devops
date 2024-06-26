package com.kbds.devops.webflux.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "customer")
public class Customer
{
    @Id
    private String id;

    private String name;
    private String mobile;
}
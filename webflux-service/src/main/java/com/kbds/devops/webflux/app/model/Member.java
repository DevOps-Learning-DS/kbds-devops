package com.kbds.devops.webflux.app.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Member {

    private long id;
    private String firstName;
    private String surname;
    private int age;
    private LocalDateTime joinDateTime;

}

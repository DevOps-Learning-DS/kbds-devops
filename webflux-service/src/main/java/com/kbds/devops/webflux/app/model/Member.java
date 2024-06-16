package com.kbds.devops.webflux.app.model;


import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Member {
    private long id;
    private String firstName;
    private String surname;
    private int age;
    private LocalDateTime joinDateTime;

}

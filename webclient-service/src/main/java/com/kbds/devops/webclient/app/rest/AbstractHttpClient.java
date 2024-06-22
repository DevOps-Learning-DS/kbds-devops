package com.kbds.devops.webclient.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbds.devops.webflux.app.config.CommonConfig;
import com.kbds.devops.webflux.app.model.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.List;


@Getter
@Setter
@Log
public abstract class AbstractHttpClient {

    protected ObjectMapper objectMapper = new CommonConfig().objectMapper();
    private String httpClientName;

    public abstract Member getMember();
    public abstract List<Member> findBySurname();
    public abstract Member createMember(Member member);
    public abstract List<Member> getAllMembers();
    public abstract List<Member> getListMembers(List<Long> memberIdList);
}

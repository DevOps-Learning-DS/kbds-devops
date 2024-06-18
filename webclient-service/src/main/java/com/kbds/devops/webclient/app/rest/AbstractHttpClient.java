package com.kbds.devops.webclient.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbds.devops.webflux.app.config.CommonConfig;
import com.kbds.devops.webflux.app.model.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}

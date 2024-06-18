package com.kbds.devops.webclient.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbds.devops.webflux.app.config.CommonConfig;
import com.kbds.devops.webflux.app.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractHttpClient {

    protected ObjectMapper objectMapper = new CommonConfig().objectMapper();
    protected static Logger logger = LoggerFactory.getLogger(AbstractHttpClient.class);

    private String httpClientName;

    public void setHttpClientName(String  httpClientName) {
        this.httpClientName = httpClientName;
    }
    public String getHttpClientName() {
        return httpClientName;
    }

    abstract public Member getMember();
    abstract public List<Member> findBySurname();
}

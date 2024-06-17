package com.kbds.devops.webclient.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.kbds.devops.webflux.app.config.CommonConfig;
import com.kbds.devops.webflux.app.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public abstract class HttpClientTemplate {

    protected ObjectMapper objectMapper = new CommonConfig().objectMapper();
    protected static Logger logger = LoggerFactory.getLogger(HttpClientTemplate.class);

    private String httpClientName;

    public void setHttpClientName(String  httpClientName) {
        this.httpClientName = httpClientName;
    }
    public void runGetMember() {
        Stopwatch stopWatch = Stopwatch.createStarted();
        logger.info("getMember has started in {} at {}", httpClientName, stopWatch.elapsed(TimeUnit.MILLISECONDS));
        Member member = getMember();
        stopWatch.stop();
        stopWatch.reset();
        logger.info("getMember has stopped in {} at {}", httpClientName, stopWatch.elapsed(TimeUnit.MILLISECONDS));
        logger.info("Member is ={}",member.toString());

    }

    abstract protected Member getMember();
}

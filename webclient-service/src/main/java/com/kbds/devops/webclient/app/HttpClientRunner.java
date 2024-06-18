package com.kbds.devops.webclient.app;

import com.google.common.base.Stopwatch;
import com.kbds.devops.webclient.app.rest.AbstractHttpClient;
import com.kbds.devops.webclient.app.rest.FeignTemplateClient;
import com.kbds.devops.webclient.app.rest.RestTemplateClient;
import com.kbds.devops.webclient.app.rest.WebTemplateClient;
import com.kbds.devops.webflux.app.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpClientRunner {
    private static Logger logger = LoggerFactory.getLogger(HttpClientRunner.class);

    private List<AbstractHttpClient> httpClientList = new ArrayList<>();

    private static String SPERATED_LINE = "===========================================================";

    public void init() {
        httpClientList.add(new RestTemplateClient());
        httpClientList.add(new FeignTemplateClient());
        httpClientList.add(new WebTemplateClient());
    }

    public void runGetMember() {
        httpClientList.stream().forEach(httpClientTemplate ->{
            Stopwatch stopWatch = Stopwatch.createStarted();
            String httpClientName = httpClientTemplate.getHttpClientName();
            logger.info(SPERATED_LINE);
            logger.info("getMember has started in {} at {}", httpClientName, stopWatch.elapsed(TimeUnit.MILLISECONDS));
            Member member = httpClientTemplate.getMember();
            stopWatch.stop();
            stopWatch.reset();
            logger.info("getMember has stopped in {} at {}", httpClientName, stopWatch.elapsed(TimeUnit.MILLISECONDS));
            logger.info("Member is ={}", member.toString());
            logger.info(SPERATED_LINE);
        });
    }

    public void runFindBySurname() {
        httpClientList.stream().forEach(httpClientTemplate ->{
            Stopwatch stopWatch = Stopwatch.createStarted();
            String httpClientName = httpClientTemplate.getHttpClientName();
            logger.info(SPERATED_LINE);
            logger.info("findBySurname has started in {} at {}", httpClientName, stopWatch.elapsed(TimeUnit.MILLISECONDS));
            List<Member> memberList = httpClientTemplate.findBySurname();
            stopWatch.stop();
            stopWatch.reset();
            logger.info("findBySurname has stopped in {} at {}", httpClientName, stopWatch.elapsed(TimeUnit.MILLISECONDS));
            logger.info("Member is ={}", memberList.toString());
            logger.info(SPERATED_LINE);
        });
    }
}

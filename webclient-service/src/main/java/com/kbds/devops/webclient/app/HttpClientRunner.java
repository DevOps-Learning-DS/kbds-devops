package com.kbds.devops.webclient.app;

import com.google.common.base.Stopwatch;
import com.kbds.devops.webclient.app.rest.AbstractHttpClient;
import com.kbds.devops.webclient.app.rest.FeignTemplateClient;
import com.kbds.devops.webclient.app.rest.RestTemplateClient;
import com.kbds.devops.webclient.app.rest.WebTemplateClient;
import com.kbds.devops.webflux.app.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpClientRunner {
    private static Logger logger = LoggerFactory.getLogger(HttpClientRunner.class);

    private List<AbstractHttpClient> httpClientList = new ArrayList<>();

    private static String SPERATED_LINE = "===========================================================";

    private Stopwatch stopWatch;
    private String httpClientName;
    private String methodName;

    public void init() {
        httpClientList.add(new RestTemplateClient());
        httpClientList.add(new FeignTemplateClient());
        httpClientList.add(new WebTemplateClient());
    }

    public void runGetMember() {
        methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        httpClientList.stream().forEach(httpClientTemplate ->{
            startLine(httpClientTemplate);
            logger.info("getMember has started in {} at {}", httpClientName, stopWatch.elapsed(TimeUnit.MILLISECONDS));
            Member member = httpClientTemplate.getMember();
            endLine(member );
        });
    }

    public void runFindBySurname() {
        methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        httpClientList.stream().forEach(httpClientTemplate ->{
            startLine(httpClientTemplate);
            List<Member> memberList = httpClientTemplate.findBySurname();
            endLine(memberList);
        });
    }

    public void runCreateMember() {
        methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        httpClientList.stream().forEach(httpClientTemplate ->{
            startLine(httpClientTemplate);
            Member member = new Member(0l,"First","Surname",23,
                    LocalDateTime.of(2021, 7, 20, 11, 0, 0));
            Member resMember = httpClientTemplate.createMember(member);
            endLine(resMember );
        });
    }

    public void runGetAllMembers() {
        methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        httpClientList.stream().forEach(httpClientTemplate ->{
            startLine(httpClientTemplate);
            List<Member> memberList = httpClientTemplate.getAllMembers();
            endLine(memberList );
        });
    }

    public void runGetListMembers() {
        methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        final List<Long> memberIdList = Arrays.asList(1l, 9l);
        httpClientList.stream().forEach(httpClientTemplate ->{
            startLine(httpClientTemplate);
            List<Member> memberList = httpClientTemplate.getListMembers(memberIdList);
            endLine(memberList );
        });
    }

    private void startLine(AbstractHttpClient httpClient) {
        stopWatch = Stopwatch.createStarted();
        httpClientName = httpClient.getHttpClientName();
        logger.info(SPERATED_LINE);
        logger.info("{} has started in {} at {}", methodName, httpClientName, stopWatch.elapsed(TimeUnit.MILLISECONDS));
    }

    private <T> void endLine(T result) {
        stopWatch.stop();
        stopWatch.reset();
        logger.info("{} has stopped in {} at {}",methodName, httpClientName, stopWatch.elapsed(TimeUnit.MILLISECONDS));

        if( result instanceof List ) {
            logger.info("The size of reslut is ={}", ((List)result).size());
            ((List)result).stream().forEach(
                    member->logger.info("\tMember is ={}", member.toString())
            );
        }
        else if( result instanceof Member ) {
            logger.info("Member is ={}", result.toString());
        }

        logger.info(SPERATED_LINE);
    }
}

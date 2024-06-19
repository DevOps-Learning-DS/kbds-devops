package com.kbds.devops.webclient.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.kbds.devops.webflux.app.config.CommonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class BenchApplication {
    
    private static Logger logger = LoggerFactory.getLogger(BenchApplication.class);

    private static ObjectMapper objectMapper = new CommonConfig().objectMapper();

    private static final WebClient webClient = WebClient.builder()
            .codecs(clientDefaultCodecsConfigurer -> {
                clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(
                        new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(
                        new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));

            })
            .baseUrl("http://localhost:8080/webflux")
            .build();

    private static  ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Flux.range(1, 1).subscribe(seq->{
            webClient.get().uri("/bench")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();


            logger.info("Total Number={}, Active Number={}", threadMXBean.getThreadCount(),Thread.activeCount());
        });
    }
}

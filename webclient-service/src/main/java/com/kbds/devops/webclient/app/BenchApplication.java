package com.kbds.devops.webclient.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.kbds.devops.webflux.app.config.CommonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
            .baseUrl("http://localhost:8080/")
            .build();

    private static final RestTemplate restTemplate = restTemplateClient();

    public static RestTemplate restTemplateClient() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();
        messageConverterList.add( new ByteArrayHttpMessageConverter());
        messageConverterList.add( new StringHttpMessageConverter());
        messageConverterList.add( new FormHttpMessageConverter());
        messageConverterList.add( new MappingJackson2HttpMessageConverter(objectMapper));
        restTemplate.setMessageConverters(messageConverterList);
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080/"));

        return restTemplate;
    }

/*                   webClient.get().uri("/bench")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block()*/

    private static  ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    public static void main(String[] args) {
      //  runRestTemplateForWebMvc();
      //  runRestTemplateForWebflux();
        runWebClientForWebflux();

    }

    public static void runWebClientForWebflux() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Flux.range(1, 100)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(seq ->webClient.get().uri("/webflux/bench")
                        .retrieve()
                        .bodyToMono(String.class)
                        .subscribe(responseStr->{
                    logger.info("responseStr={}", responseStr);
                }))
                .subscribe();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runWebClientForWebMvc() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Flux.range(1, 100)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(seq ->restTemplate.getForObject("Method.../bench", String.class));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runRestTemplateForWebflux() {
        Supplier<String> supplier = ()->{
            String responseStr =  restTemplate.getForObject("/webflux/bench", String.class);
            logger.info("responseStr={}", responseStr);
            return responseStr;
        };

        List<CompletableFuture<String>> futures = new ArrayList<>();
        for(int i = 0; i < 100;i++) {
            futures.add(CompletableFuture.supplyAsync(supplier));
        }

        Void combinedFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    public static void runRestTemplateForWebMvc() {
        Supplier<String> supplier = ()->{
            String responseStr =  restTemplate.getForObject("/webmvc/bench", String.class);
            logger.info("responseStr={}", responseStr);
            return responseStr;
        };

        List<CompletableFuture<String>> futures = new ArrayList<>();
        for(int i = 0; i < 100;i++) {
            futures.add(CompletableFuture.supplyAsync(supplier));
        }

        Void combinedFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

}

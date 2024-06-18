package com.kbds.devops.webclient.app;


import com.kbds.devops.webclient.app.rest.HttpClientTemplate;
import com.kbds.devops.webclient.app.rest.RestTemplateClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.ArrayList;
import java.util.List;

public class WebClientApplication {
    public static void main(String[] args) {
        HttpClientRunner httpClientRunner = new HttpClientRunner();
        httpClientRunner.init();
       // httpClientRunner.runGetMember();
        httpClientRunner.runFindBySurname();
    }

}

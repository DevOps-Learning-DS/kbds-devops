package com.kbds.devops.webclient.app;


import lombok.extern.java.Log;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;


@Log
public class WebClientApplication {
    public static void main(String[] args) {
        HttpClientRunner httpClientRunner = new HttpClientRunner();
        httpClientRunner.init();
       // httpClientRunner.runGetMember();
        //httpClientRunner.runFindBySurname();
        //httpClientRunner.runCreateMember();
        // httpClientRunner.runGetAllMembers();
        httpClientRunner.runGetListMembers();

    }

}

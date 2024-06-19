package com.kbds.devops.webclient.app;


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

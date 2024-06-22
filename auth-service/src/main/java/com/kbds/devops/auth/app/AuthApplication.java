package com.kbds.devops.auth.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@SpringBootApplication
@EnableAuthorizationServer
public class AuthApplication implements ApplicationRunner {
    private static final Logger logger = getLogger(AuthApplication.class);

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Mapping> mappings = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : applicationContext
                .getBean(RequestMappingHandlerMapping.class).getHandlerMethods().entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            Mapping mapping = new Mapping();
            mapping.setMethods(requestMappingInfo.getMethodsCondition().getMethods()
                    .stream().map(Enum::name).collect(Collectors.toSet()));
            mapping.setPatterns(requestMappingInfo.getPatternsCondition().getPatterns());
            Arrays.stream(handlerMethod.getMethodParameters()).forEach(methodParameter -> {
                mapping.getParams().add(methodParameter.getParameter().getType().getSimpleName());
            });
            mappings.add(mapping);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        mappings.sort(Comparator.comparing(o -> o.getPatterns().stream().findFirst().orElse("")));
        mappings.stream().forEach(mappingInfo->{
            try {
                logger.info("Pattern={}, Method={}"
                        , objectMapper.writeValueAsString(mappingInfo.getPatterns())
                        , objectMapper.writeValueAsString(mappingInfo.getMethods()));
            } catch (Exception e) {

            }
        });
    }
}

@Data
class Mapping {
    private Set<String> patterns;
    private Set<String> methods;
    private List<String> params;

    public List<String> getParams(){
        if(params == null) {
            params = new ArrayList<>();
        }
        return params;

    }
}

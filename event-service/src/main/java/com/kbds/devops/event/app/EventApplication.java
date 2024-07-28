package com.kbds.devops.event.app;


import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Map;

@SpringBootApplication
public class EventApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(EventApplication.class, args);
    }

    @Autowired
    private ApplicationContext context;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, RequestMappingHandlerMapping> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(context,
                RequestMappingHandlerMapping.class, true, false);

        if (!matchingBeans.isEmpty()) {
            ArrayList<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>(matchingBeans.values());
            AnnotationAwareOrderComparator.sort(handlerMappings);

            RequestMappingHandlerMapping mappings = matchingBeans.get("requestMappingHandlerMapping");
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = mappings.getHandlerMethods();

            for (RequestMappingInfo requestMappingInfo : handlerMethods.keySet()) {
                RequestMethodsRequestCondition methods = requestMappingInfo.getMethodsCondition();

                // Get all requestMappingInfos with
                //  1) default request-method (which is none)
                //  2) or method=GET
                if (methods.getMethods().isEmpty() || methods.getMethods().contains(RequestMethod.GET)) {
                    System.out.println(requestMappingInfo.getPatternsCondition().getPatterns() + " -> produces " +
                            requestMappingInfo.getProducesCondition());
                }
            }
        }
    }
}

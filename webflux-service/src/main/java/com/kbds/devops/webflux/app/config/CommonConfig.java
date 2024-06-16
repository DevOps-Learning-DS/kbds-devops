package com.kbds.devops.webflux.app.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Configuration
public class CommonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(dateTimeformatter);
        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(dateTimeformatter);
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        module.addSerializer(LocalDateTime.class, localDateTimeSerializer);
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();

        return objectMapper;
    }
}

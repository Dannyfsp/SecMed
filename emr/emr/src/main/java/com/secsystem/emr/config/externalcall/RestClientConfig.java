package com.secsystem.emr.config.externalcall;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    @Bean
    public RestTemplate getTemplate(){
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter());
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return template;
    }
}

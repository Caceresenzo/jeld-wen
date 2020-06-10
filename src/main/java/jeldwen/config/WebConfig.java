package jeldwen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jeldwen.common.convert.spring.CustomDateConverter;
import jeldwen.common.convert.spring.SimpleDurationConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
 
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CustomDateConverter());
        registry.addConverter(new SimpleDurationConverter());
    }
    
}
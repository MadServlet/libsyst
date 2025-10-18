package com.proj.itstaym.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@Configuration
@ComponentScan("com.proj.itstaym")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class AppConfig {
    
}

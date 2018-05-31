package com.exo.ecommerce.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class ECommerceApplication extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(ECommerceApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ECommerceApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }
}

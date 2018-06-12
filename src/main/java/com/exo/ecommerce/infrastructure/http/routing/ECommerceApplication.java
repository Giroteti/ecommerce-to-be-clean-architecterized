package com.exo.ecommerce.infrastructure.http.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("com.exo.ecommerce")
@EnableJpaRepositories(basePackages = {"com.exo.ecommerce.infrastructure.bdd"})
@ImportResource({"classpath*:applicationContext.xml"})
@EntityScan("com.exo.ecommerce")
@SpringBootApplication
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


package com.freelance.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.freelance"})
@ComponentScan(basePackages = {"com.freelance"})
@EnableJpaRepositories(basePackages = {"com.freelance"})
@SpringBootApplication
public class FreelanceHubApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(FreelanceHubApplicationStarter.class, args);
    }

}

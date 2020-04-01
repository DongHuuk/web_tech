package com.springdata.web.web_tech;

import com.springdata.web.web_tech.post.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "accountAuditAware")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


    }

}

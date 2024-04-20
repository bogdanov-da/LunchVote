package org.bda.voteapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class LunchVoteApplication {
    public static void main(String[] args) {
        SpringApplication.run(LunchVoteApplication.class, args);
    }

}

package com.example.blps4;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableProcessApplication
@SpringBootApplication
public class Blps4Application {

    public static void main(String[] args) {
        SpringApplication.run(Blps4Application.class, args);
    }

}

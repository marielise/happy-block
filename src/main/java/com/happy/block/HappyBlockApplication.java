package com.happy.block;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.happy.block")
public class HappyBlockApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyBlockApplication.class, args);
    }

}
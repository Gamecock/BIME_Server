/**********************
 *
 *   Copyright 2018 Mike Finch part of the BIME project with liscence in LISCENCE.txt file
 *
 *   Application that runs the REST server on https://spring.io/guides/gs/rest-service/#scratch
 *
 *********************/
package com.bime.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
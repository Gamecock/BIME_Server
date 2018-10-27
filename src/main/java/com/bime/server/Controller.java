/**********************
 *
 *   Copyright 2018 Mike Finch part of the BIME project with liscence in LISCENCE.txt file
 *
 *   Server REST controller based on https://spring.io/guides/gs/rest-service/#scratch
 *
 *********************/
package com.bime.server;

import com.bime.server.hello.Greeting;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Controller {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @GetMapping("/api/authentication/authenticate")
    public String getLogin() {
        return "Login Endpoint Running.";
    }

    @PostMapping("api/authentication/authenticate")
    public Credentials postLogin() {
        return new Credentials();
    }

    public Controller() {
    }


}
/**********************
 *
 *   Copyright 2018 Mike Finch part of the BIME project with liscence in LISCENCE.txt file
 *
 *   Server REST controller based on https://spring.io/guides/gs/rest-service/#scratch
 *
 *********************/
package com.bime.server;

import com.bime.server.hello.Greeting;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Controller {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    public Controller() {
    }
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @GetMapping("/api/authentication/authenticate")
    public String getLogin() {
        return "Login Endpoint Running.";
    }

    @PostMapping(value = "api/authentication/authenticate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Credentials postLogin(@RequestBody String credentials) {
        System.out.println("Received credentials "+ credentials);
        JSONObject creds = new JSONObject(credentials);
        String BannerId = null;
        String Password = null;
        try {
            BannerId = creds.getString("BannerId");
            Password = creds.getString("Password");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("Received username "+ BannerId + ", and password "+ Password);
        if( Password == null || Password.equals("password")) {
            throw new UnauthorizedException("Fake Unauthorized");
        }
        return new Credentials();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String exception) {
            super(exception);
        }
    }


}



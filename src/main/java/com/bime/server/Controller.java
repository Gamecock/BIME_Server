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

import java.util.Map;
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
            BannerId = creds.getString("bannerID");
            Password = creds.getString("password");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("Received username "+ BannerId + ", and password "+ Password);
        if( Password == null || Password.equals("password")) {
            throw new UnauthorizedException("Fake Unauthorized");
        }
        return new Credentials(BannerId);
    }

    @PostMapping("/api/meal/getmealcount")
    public MealCount getCount(@RequestBody String content, @RequestHeader(value="Authorization", defaultValue = "") String token, @RequestHeader() Map<String, String> headers) {
        for(Map.Entry<String, String> header : headers.entrySet()){
            System.out.println("Received Header: "+ header.getKey()+ " with value: "+header.getValue());
        }

        System.out.println("token is: "+ token);
        //Not correct way to handle auth, just testing mobile app sends token given at login.
        if(!token.equals("Bearer: abcdefg12345")) throw new UnauthorizedException("Incorrect Token");
        JSONObject id = new JSONObject(content);
        String bannerID = null;
        try {
            bannerID = id.getString("BannerID");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return new MealCount(Integer.parseInt(bannerID.substring(2,3)), bannerID);
    }

    @PostMapping("/api/meal/usemeal")
    public MealCount getTicket(@RequestBody String content) {
        System.out.println("banner Id is: "+content);
        JSONObject id = new JSONObject(content);
        String bannerID = null;
        try {
            bannerID = id.getString("BannerID");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (bannerID.equals("B999999")) {
            throw  new NoContentException("No Meals Available");
        } else {
            return new MealCount(Integer.parseInt(bannerID.substring(2,3)), bannerID);
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String exception) {
            super(exception);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public class NoContentException extends RuntimeException {
        public NoContentException(String exception) {
            super(exception);
        }
    }



}



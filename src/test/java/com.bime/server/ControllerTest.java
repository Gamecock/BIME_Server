/**********************
 *
 *   Copyright 2018 Mike Finch part of the BIME project with liscence in LISCENCE.txt file
 *
 *   Unit test for  REST controller based on https://spring.io/guides/gs/testing-web/
 *
 *********************/
package com.bime.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.JsonPathResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefault() throws Exception {
        this.mockMvc.perform(get("/greeting")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World!")));
    }

    @Test
    public void shouldReturnName() throws Exception {
        this.mockMvc.perform(get("/greeting?name=Mike")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, Mike!")));
    }

    @Test
    public void getAuthorizationReturnsText() throws Exception {
        this.mockMvc.perform(get("/api/authentication/authenticate"))
                .andExpect(status().isOk()).andExpect(content().string("Login Endpoint Running."));
    }

    String goodCredentials = "{ \"bannerID\":\"jonesj13\", \"password\":\"GoPirates!\"}";

    @Test
    public void postAuthorizationSuccess() throws Exception {
        this.mockMvc.perform(post("/api/authentication/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(goodCredentials))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("abcdefg12345"))
                .andExpect(jsonPath("$.bannerID").value("jonesj13"));
    }

    String badCredentials = "{ \"bannerId\":\"smithj15\", \"password\":\"password\"}";

    @Test
    public void postAuthorizationUnauthorized() throws Exception {
        this.mockMvc.perform(post("/api/authentication/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(badCredentials)).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getLunchRemainingCount()throws Exception {
        this.mockMvc.perform(get("/api/B0111234/meals/plan")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.MealCount").value(1));
    }

    @Test
    public void getLunchRemainingCountDifferentNumber()throws Exception {
        this.mockMvc.perform(get("/api/B0011234/meals/plan")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.MealCount").value(0));
    }

    @Test
    public void getLunchTicket()throws Exception {
        this.mockMvc.perform(get("/api/B0011234/meals/ticket")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.MealTicket").value("This is a ticket for a meal"));
    }

    @Test
    public void getLunchTicketWithNoTicket()throws Exception {
        this.mockMvc.perform(get("/api/B999999/meals/ticket")).andDo(print())
                .andExpect(status().isNoContent());
    }



}


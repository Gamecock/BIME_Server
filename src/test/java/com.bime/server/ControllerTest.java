/**********************
 *
 *   Copyright 2018 Mike Finch part of the BIME project with liscence in LISCENCE.txt file
 *
 *   Unit test for  REST controller based on https://spring.io/guides/gs/testing-web/
 *
 *********************/
package com.bime.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    private HttpHeaders goodHeader;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setupHeader(){
        goodHeader = new HttpHeaders();
        goodHeader.add("Authorization: Bearer", "abcdefg12345");
    }

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

    String banner1 = "{ \"bannerID\":\"B0111234\"}";

    @Test
    public void getLunchRemainingCount()throws Exception {
        this.mockMvc.perform(post("/api/meal/getmealcount")
                .headers(goodHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(banner1)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mealCount").value(1));
    }

    String banner2 = "{ \"bannerID\":\"B0011234\"}";

    @Test
    public void getLunchRemainingCountDifferentNumber()throws Exception {
        this.mockMvc.perform(post("/api/meal/getmealcount")
                .headers(goodHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(banner2)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mealCount").value(0));
    }

    @Test
    public void getLunchRemainingWithoutcredentials()throws Exception {
        this.mockMvc.perform(post("/api/meal/getmealcount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(banner2)).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getLunchTicket()throws Exception {
        this.mockMvc.perform(post("/api/meal/usemeal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(banner1)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mealCount").exists())
                .andExpect(jsonPath("$.bannerID").value("B0111234"));
    }

    String banner3 = "{ \"bannerID\":\"B999999\"}";

    @Test
    public void getLunchTicketWithNoTicket()throws Exception {
        this.mockMvc.perform(post("/api/meal/usemeal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(banner3)).andDo(print())
                .andExpect(status().isNoContent());
    }



 }


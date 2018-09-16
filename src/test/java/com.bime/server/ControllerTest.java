/**********************
 *
 *   Copyright 2018 Mike Finch part of the BIME project with liscence in LISCENCE.txt file
 *
 *   Unit test for  REST controller based on https://spring.io/guides/gs/testing-web/
 *
 *********************/
package com.bime.server;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.bime.server.hello.Greeting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefault() throws Exception {
        this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World!")));
    }

    @Test
    public void shouldReturnName() throws Exception {
        this.mockMvc.perform(get("/greeting?name=Mike")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, Mike!")));
    }
}


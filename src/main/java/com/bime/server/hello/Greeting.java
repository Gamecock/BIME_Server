/**********************
 *
 *   Copyright 2018 Mike Finch part of the BIME project with liscence in LISCENCE.txt file
 *
 *   Sample file that demonstrates a REST resources based on https://spring.io/guides/gs/rest-service/#scratch
 *
 *********************/

package com.bime.server.hello;

public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}


package com.bime.server;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MealTicket {

    private final String MealTicket = "This is a meal Ticket";

    @JsonProperty("MealTicket")
    public String getMealTicket() {
        return "This is a ticket for a meal";
    }

}

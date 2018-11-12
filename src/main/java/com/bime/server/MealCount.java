package com.bime.server;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MealCount {

    private final int MealCount;

    public MealCount( ) {
        this.MealCount = 1;
    }

    public MealCount(int amount) {
        this.MealCount = amount;
    }

    @JsonProperty("MealCount")
    public int getMealCount() {
        return MealCount;
    }

}

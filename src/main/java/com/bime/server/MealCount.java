package com.bime.server;

public class MealCount {

    private final int mealCount;
    private final String bannerID;

    public MealCount( ) {

        this.mealCount = 1;
        this.bannerID = "B00775050";
    }

    public MealCount(int amount, String bannerId) {
        this.mealCount = amount;
        this.bannerID = bannerId;
    }

    public int getMealCount() {
        return mealCount;
    }

    public String getBannerID() { return bannerID; }

}

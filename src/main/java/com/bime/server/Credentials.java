package com.bime.server;

public class Credentials {

    private final String token;
    private final String bannerID;

    public Credentials( ) {
        this.token = "abcdefg12345";
        this.bannerID = "B12345";
    }

    public Credentials(String bannerID) {
        this.token = "abcdefg12345";
        this.bannerID = bannerID;
    }

    public String getToken() {
        return token;
    }

    public String getBannerID() {
        return bannerID;
    }
}

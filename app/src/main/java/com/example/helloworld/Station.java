package com.example.helloworld;

import java.util.List;

public class Station {
    private String name;
    private int imageResourceId; // Drawable resource ID for the image
    private List<String> arrivalTimes;

    public Station(String name, int imageResourceId, List<String> times) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.arrivalTimes = times;
    }

    public String getName() {
        return name;
    }

    public List<String> getTimes() {
        return arrivalTimes;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}

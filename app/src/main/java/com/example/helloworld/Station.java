package com.example.helloworld;

import java.util.List;

public class Station {
    private String name;
    private List<Integer> imageResourceIds;
    private List<String> arrivalTimes;

    public Station(String name, List<Integer> imageResourceIds, List<String> times) {
        this.name = name;
        this.imageResourceIds = imageResourceIds;
        this.arrivalTimes = times;
    }

    public String getName() {
        return name;
    }

    public List<String> getTimes() {
        return arrivalTimes;
    }

    public List<Integer> getImageResourceIds() {
        return imageResourceIds;
    }
}

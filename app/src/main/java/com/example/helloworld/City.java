package com.example.helloworld;

public class City {
    private String title;
    private int imageResource;
    private String description;
    private String category;

    public City(String title, int imageResource, String description, String category) {
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }
}

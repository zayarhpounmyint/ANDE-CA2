package com.example.helloworld;

// CardItem.java
public class Attraction {
    private int id;
    private String category;
    private int image;
    private String title;
    private String description;
    private double rating;
    private String location;
    private double distance;
    private String website;

    // Constructor without ID for new attractions
    public Attraction(String category, int image, String title, String description, double rating, String location, double distance, String website) {
        this.category = category;
        this.image = image;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.location = location;
        this.distance = distance;
        this.website = website;
    }

    // Constructor with ID for database operations
    public Attraction(int id, String category, int image, String title, String description, double rating, String location, double distance, String website) {
        this.id = id;
        this.category = category;
        this.image = image;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.location = location;
        this.distance = distance;
        this.website = website;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }
    public String getLocation() {
        return location;
    }

    public double getDistance() {
        return distance;
    }

    public String getWebsite() {
        return website;
    }
}


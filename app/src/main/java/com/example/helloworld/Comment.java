package com.example.helloworld;

public class Comment {

    private int id;
    private int attractionId;
    private String comment;

    // Constructor
    public Comment(int id, int attractionId, String comment) {
        this.id = id;
        this.attractionId = attractionId;
        this.comment = comment;
    }

    // Empty constructor in case you need to instantiate Comment object without setting fields initially
    public Comment() {
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}

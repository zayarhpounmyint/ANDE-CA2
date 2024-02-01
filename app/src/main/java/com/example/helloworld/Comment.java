package com.example.helloworld;

public class Comment {

    private int id;
    private int attractionId;
    private String username;
    private String comment;

    // Constructors
    public Comment(int attractionId, String username, String comment) {
        this.attractionId = attractionId;
        this.username = username;
        this.comment = comment;
    }

    public Comment(int id, int attractionId, String username, String comment) {
        this.id = id;
        this.attractionId = attractionId;
        this.username = username;
        this.comment = comment;
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

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}

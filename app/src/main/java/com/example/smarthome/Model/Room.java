package com.example.smarthome.Model;

public class Room {
    private String id;
    private String name;
    private String image;
    private Boolean mode;

    public Room(String id, String name, String image, Boolean mode) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.mode = mode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getMode() {
        return mode;
    }

    public void setMode(Boolean mode) {
        this.mode = mode;
    }
}

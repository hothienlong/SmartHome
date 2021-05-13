package com.example.smarthome.Model;

public class Room {
    private String name;
    private String image;
    private Boolean mode;

    public Room(String name, String image, Boolean mode) {
        this.name = name;
        this.image = image;
        this.mode = mode;
    }

    public Room(String name, String image) {
        this.name = name;
        this.image = image;
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

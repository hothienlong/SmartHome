package com.example.smarthome.Model;

public class Room {
    private String id;
    private String name;
    private Long image;
    private Boolean mode;



    public Room(String id, String name, Long image, Boolean mode) {
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

    public Long getImage() {
        return image;
    }

    public void setImage(Long image) {
        this.image = image;
    }

    public Boolean getMode() {
        return mode;
    }

    public void setMode(Boolean mode) {
        this.mode = mode;
    }

}

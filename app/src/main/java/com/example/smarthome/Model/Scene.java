package com.example.smarthome.Model;

import java.util.ArrayList;

public class Scene {
    private String id;
    private String name;
    private Long image;

    public Scene(String id, String name, Long image) {
        this.id = id;
        this.name = name;
        this.image = image;
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
}

package com.example.smarthome.Model;

import java.util.ArrayList;

public class Scene {
    private String name;
    private String logo;

    public Scene(String name, String logo) {
        this.logo = logo;
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

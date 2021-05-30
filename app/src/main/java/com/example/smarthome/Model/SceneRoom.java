package com.example.smarthome.Model;

import java.util.HashMap;

public class SceneRoom {
    private String roomName;
    private HashMap<String, HashMap<String, Boolean>> config;

    public SceneRoom(String roomName) {
        this.roomName = roomName;
        this.config = new HashMap<String, HashMap<String, Boolean>>();
    }

    public void addConfig(String id, Boolean status, String deviceType) {
        if (!this.config.containsKey(deviceType)) {
            this.config.put(deviceType, new HashMap<String, Boolean>());
        }

        this.config.get(deviceType).put(id, status);
    }
}

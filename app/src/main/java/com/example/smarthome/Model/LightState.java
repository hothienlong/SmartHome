package com.example.smarthome.Model;

public class LightState {
    public String id;
    public String state;
    public String room;

    public LightState(String id, String state, String room) {
        this.id = id;
        this.state = state;
        this.room = room;
    }
}

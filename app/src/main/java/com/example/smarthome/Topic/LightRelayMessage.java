package com.example.smarthome.Topic;

public class LightRelayMessage {
    String id;
//    final String name = "RELAY";
    String data;
    String unit;

    public LightRelayMessage(String id, String data, String unit) {
        this.id = id;
        this.data = data;
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getName() {
//        return name;
//    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "{\"id\":\"" + id +
                "\",\"name\":\"RELAY\",\"data\":\"" + data +
                "\",\"unit\":\"\"}";

    }
}

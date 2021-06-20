package com.example.smarthome.Topic;

public class GasTopic {
    String id;
    final String name = "GAS";
    String data;
    final String unit = "";


    public GasTopic(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

//    public void setData(String data) {
//        this.data = data;
//    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "{" +
                    "\"id\":\"" + id +
                    "\",\"name\":\"" + name +
                    "\",\"data\":\"" + data +
                    "\",\"unit\":\"" + unit +
                "}";

    }
}

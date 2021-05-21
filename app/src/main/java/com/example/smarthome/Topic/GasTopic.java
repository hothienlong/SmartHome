package com.example.smarthome.Topic;

public class GasTopic {
    final String id = "23";
    final String name = "GAS";
    String data;
    final String unit = "";


    public GasTopic(String data) {
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

//    @Override
//    public String toString() {
//        return "{" +
//                    "\"id\":\"" + id +
//                    "\",\"name\":\"" + name +
//                    "\",\"data\":\"" + data +
//                    "\",\"unit\":\"" + unit +
//                "}";
//
//    }
}

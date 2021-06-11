package com.example.smarthome.Topic;

public class DoorTopic {
    public static  final String topic = "bbb-door";
    private String id ;
    private String name;
    private String data;
    private String unit;

    public DoorTopic() {}
    public DoorTopic(String _id, String _name, String _data, String _unit) {
        id = _id;
        name = _name;
        data = _data;
        unit = _unit;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }


    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "DoorTopic{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", value='" + data + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}

package com.example.smarthome.Topic;

public class DoorTopic {
    public static  final String topic = "bbb-door";
    private String id ;
    private String name;
    private String value;
    private String unit;

    public DoorTopic() {}
    public DoorTopic(String _id, String _name, String _value, String _unit) {
        id = _id;
        name = _name;
        value = _value;
        unit = _unit;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "DoorTopic{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}

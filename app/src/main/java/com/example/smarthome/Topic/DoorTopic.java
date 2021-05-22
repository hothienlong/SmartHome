package com.example.smarthome.Topic;

public class DoorTopic {
    private String topic = "/bbc-door";
    private String id ;
    private String name;
    private String value;
    private String unit;

    public DoorTopic(String _id, String _name, String _value, String _unit) {
        id = _id;
        name = _name;
        value = _value;
        unit = _unit;
    }

    public String getId() {return id;}
    public void setId(String _id) { id = _id; }

    public String getName() {return name; }
    public void setName(String _name) { name = _name; }

    public String getValue() {return value; }
    public void setValue(String _value) { value = _value; }
}

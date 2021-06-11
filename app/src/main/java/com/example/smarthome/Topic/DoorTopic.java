package com.example.smarthome.Topic;

public class DoorTopic {
    public static  final String topic = "bbb-door";
    private String id ;
    private String name;
    private String value;
    private String unit;
    private String type;

    public DoorTopic() {}
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

    public String getUnit() { return unit; }
    public void setUnit(String _unit) { unit = _unit; }

    public int setType(String dtype) {
        if(dtype.isEmpty()) {
            return 0;
        }
        type = dtype;
        return 1;
    }

    public String getType() { return type; }

    @Override
    public String toString() {
        return "{id: \"" + id + "\", name: \""+ name + "\", value: \"" + value + "\", unit: \"" + "\", type: \"" + type + "\"}";
    }
}

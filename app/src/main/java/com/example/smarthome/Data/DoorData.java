package com.example.smarthome.Data;

public class DoorData {
    private String name;
    private Boolean status;
    private String type;

    public DoorData() {}
    public DoorData(String _name, Boolean _status, String _type) {
        name=_name;
        status=_status;
        type=_type;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DoorData{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", type='" + type + '\'' +
                '}';
    }
}

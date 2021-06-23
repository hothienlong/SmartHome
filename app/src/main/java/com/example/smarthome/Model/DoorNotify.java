package com.example.smarthome.Model;

public class DoorNotify {
    private String name;
    private Boolean status;
    private String type;

    public DoorNotify() {
        // mặt định fire base để lấy dữ liệu về
    }

    public DoorNotify(String name, Boolean status, String type) {
        this.name = name;
        this.status = status;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


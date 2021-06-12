package com.example.smarthome.Model;

public class RoomBigItem {
    private String id;
    private String name;
    private Long image;
    private Boolean mode;
    private Integer deviceSize;
    private Integer deviceOn;

    public RoomBigItem(String id, String name, Long image, Boolean mode, Integer deviceSize, Integer deviceOn) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.mode = mode;
        this.deviceSize = deviceSize;
        this.deviceOn = deviceOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getImage() {
        return image;
    }

    public void setImage(Long image) {
        this.image = image;
    }

    public Boolean getMode() {
        return mode;
    }

    public void setMode(Boolean mode) {
        this.mode = mode;
    }

    public Integer getDeviceSize() {
        return deviceSize;
    }

    public void setDeviceSize(Integer deviceSize) {
        this.deviceSize = deviceSize;
    }

    public Integer getDeviceOn() {
        return deviceOn;
    }

    public void setDeviceOn(Integer deviceOn) {
        this.deviceOn = deviceOn;
    }
}

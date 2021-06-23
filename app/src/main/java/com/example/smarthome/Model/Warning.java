package com.example.smarthome.Model;

public class Warning {
    private String name;
    private Integer image;
    private String text;

    public Warning() {
        // mặt định fire base để lấy dữ liệu về
    }

    public Warning(String name, Integer image, String text){
        this.name = name;
        this.image = image;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

package com.example.smarthome.Model;

public class Waning {
    private String text;
    private String image;
    private String time;

    public Waning(String text, String image, String time) {
        this.text = text;
        this.image = image;
        this.time = time;
    }

    public Waning(String text, String image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(Boolean mode) {
        this.time = time;
    }
}

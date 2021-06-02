package com.example.smarthome.Model;

public class Warning {
    private String name;
    private Integer image;
    private Integer imagebg;
    private String text;


    public Warning(String name, Integer image, Integer imagebg, String text){
        this.name = name;
        this.image = image;
        this.imagebg = imagebg;
        this.text = text;
    }

    public Integer getImagebg() {
        return imagebg;
    }

    public void setImagebg(Integer imagebg) {
        this.imagebg = imagebg;
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

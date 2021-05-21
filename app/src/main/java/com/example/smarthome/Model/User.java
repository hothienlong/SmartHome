package com.example.smarthome.Model;

public class User {
    private String username;
    private String password;
    private String address;
    private String full_name;
    private String tel;

    public User(String username, String password, String address, String full_name, String tel) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.full_name = full_name;
        this.tel = tel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}

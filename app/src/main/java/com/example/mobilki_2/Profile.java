package com.example.mobilki_2;

import java.io.Serializable;

public class Profile implements Serializable{
    private String name;
    private String email;
    private ProxyBitmap photo;

    public Profile() {
    }

    public Profile(String name) {
        this.name = name;
        this.email = "";
        this.photo = null;
    }

    public Profile(String name, String email, ProxyBitmap photo) {
        this.name = name;
        this.email = email;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ProxyBitmap getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoto(ProxyBitmap photo) {
        this.photo = photo;
    }
}

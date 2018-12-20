package com.project.claviancandrian.event_i;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class Event {

    private String id;
    private String name;
    private String desc;
    private String location;
    private String city;
    private String type;
    private String date;
    private String cpEmail;
    private String cpTelp;

    private String owner;

    private Double price;

    private String image;


    public Event() {
    }

    public Event(String id, String name, String desc, String location, String city, String type, String date, String cpEmail, String cpTelp, Double price, String owner, String image) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.location = location;
        this.city = city;
        this.type = type;
        this.date = date;
        this.cpEmail = cpEmail;
        this.cpTelp = cpTelp;
        this.price = price;
        this.owner = owner;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCpEmail() {
        return cpEmail;
    }

    public void setCpEmail(String cpEmail) {
        this.cpEmail = cpEmail;
    }

    public String getCpTelp() {
        return cpTelp;
    }

    public void setCpTelp(String cpTelp) {
        this.cpTelp = cpTelp;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

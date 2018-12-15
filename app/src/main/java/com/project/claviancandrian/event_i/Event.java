package com.project.claviancandrian.event_i;

public class Event {

    private String name;
    private String desc;
    private String location;
    private String city;
    private String type;
    private String date;
    private String cpEmail;
    private String cpTelp;

    private Double price;

    private Integer image;

    public Event() {
    }

    public Event(String name, String desc, String location, String city, String type, String date, String cpEmail, String cpTelp, Double price) {
        this.name = name;
        this.desc = desc;
        this.location = location;
        this.city = city;
        this.type = type;
        this.date = date;
        this.cpEmail = cpEmail;
        this.cpTelp = cpTelp;
        this.price = price;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
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

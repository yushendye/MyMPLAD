package com.development.mymplad;

public class Complaint {
    int id;
    String url;
    String title;
    String description;
    String location;

    public Complaint() {
    }

    public Complaint(int id, String url, String title, String description, String location) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.description = description;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

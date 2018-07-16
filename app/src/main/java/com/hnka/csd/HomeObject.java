package com.hnka.csd;


public class HomeObject {
    private String title;
    private String subtitle;
    private String image;
    private int id;

    public HomeObject(int id, String title, String subtitle, String image) {
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
        this.id = id;
    }

    public HomeObject(String title, String subtitle, String image) {
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
    }

    public HomeObject(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }
}

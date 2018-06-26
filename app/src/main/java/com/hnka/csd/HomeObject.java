package com.hnka.csd;

import com.google.gson.annotations.SerializedName;

public class HomeObject {
    private String title;
    private String subtitle;
    private String image;
    private int progress;

    public HomeObject(String title, String subtitle, String image, int progress) {
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
        this.progress = progress;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}

package com.hnka.csd;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Serie {
    private final String id;
    private final String title;
    private final String launch_date;
    private final String about;
    private final String poster_link;
    private final String number_of_seasons;

    public Serie(String id, String title, String launch_date, String about, String poster_link, String number_of_seasons) {
        this.id = id;
        this.title = title;
        this.launch_date = launch_date;
        this.about = about;
        this.poster_link = poster_link;
        this.number_of_seasons = number_of_seasons;
    }

    public Serie(JsonObject jsonElement) {
        this.id = jsonElement.get("id").getAsString();
        this.title = jsonElement.get("title").getAsString();
        this.launch_date = jsonElement.get("launch_date").getAsString();
        this.about = jsonElement.get("about").getAsString();
        this.poster_link = jsonElement.get("poster_link").getAsString();
        this.number_of_seasons = jsonElement.get("number_of_seasons").getAsString();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLaunch_date() {
        return launch_date;
    }

    public String getAbout() {
        return about;
    }

    public String getPoster_link() {
        return poster_link;
    }

    public String getNumber_of_seasons() {
        return number_of_seasons;
    }
}

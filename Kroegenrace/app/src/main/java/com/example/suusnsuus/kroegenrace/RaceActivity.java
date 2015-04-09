package com.example.suusnsuus.kroegenrace;


import org.json.JSONArray;

/**
 * Created by Suzanne on 9-4-2015.
 */
public class RaceActivity {
    private String google_id;
    private String description;
    private JSONArray tags;

    public RaceActivity(String google_id, String description, JSONArray tags) {
        this.google_id = google_id;
        this.description = description;
        this.tags = tags;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public String getDescription() {
        return description;
    }

    public JSONArray getTags() {
        return tags;
    }
}

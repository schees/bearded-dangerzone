package com.example.suusnsuus.kroegenrace;


import org.json.JSONArray;

/**
 * Created by Suzanne on 9-4-2015.
 */
public class RaceActivity {
    private String _id;
    private String google_id;
    private String description;
    private JSONArray tags;
    private String phoneNumber;
    private String placeName;
    private String placeAdress;

    public String getPlaceAdress() {
        return placeAdress;
    }

    public void setPlaceAdress(String placeAdress) {
        this.placeAdress = placeAdress;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public RaceActivity(String _id, String google_id, String description, JSONArray tags) {
        this._id = _id;
        this.google_id = google_id;
        this.description = description;
        this.tags = tags;
    }

    public String get_id() {
        return _id;
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

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}

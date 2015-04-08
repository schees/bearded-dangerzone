package com.example.suusnsuus.kroegenrace;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.Date;

/**
 * Created by Suzanne on 7-4-2015.
 */
public class Race {
    private String _id;
    private String name;
    private String description;
    private String owner;
    private String startDateTime;
    private String endDateTime;
    private JSONArray activities;
    private JSONArray users;

    public JSONArray getUsers() {
        return users;
    }

    public void setUsers(JSONArray users) {
        this.users = users;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public JSONArray getActivities() {
        return activities;
    }

    public void setActivities(JSONArray activities) {
        this.activities = activities;
    }

    public Race(String _id, String name, String description, String owner, String startDateTime, String endDateTime, JSONArray activities, JSONArray users) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.activities = activities;
        this.users = users;
    }
}

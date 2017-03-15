package com.brokenpicinc.brokenpic.model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by orgaf_000 on 2/4/2017.
 */

public class Player {
    String name;
    String image;
    String uniqueID;
    double lastUpdated;

    public Player() {
    }

    public Player(String nickname, String profilePhotoPath) {
        this.name = nickname;
        this.image = profilePhotoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public double getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(double lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("image", image);
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return result;
    }
}

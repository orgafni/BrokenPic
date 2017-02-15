package com.brokenpicinc.brokenpic.model;

/**
 * Created by orgaf_000 on 2/4/2017.
 */

public class Player {
    String name;
    String image;

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
}

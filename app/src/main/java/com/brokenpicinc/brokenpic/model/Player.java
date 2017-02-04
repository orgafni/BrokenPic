package com.brokenpicinc.brokenpic.model;

/**
 * Created by orgaf_000 on 2/4/2017.
 */

public class Player {
    String email;
    String nickname;
    String profilePhotoPath;
    String password;

    public Player(String email, String nickname, String profilePhotoPath, String password) {
        this.email = email;
        this.nickname = nickname;
        this.profilePhotoPath = profilePhotoPath;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

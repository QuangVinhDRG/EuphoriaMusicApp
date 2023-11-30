package com.example.euphoriamusicapp.data;

public class user {
    private String email;
    private String id;
    private Long isAdmin;
    private String profile;
    private String name;
    public user() {
    }

    public user(String email, String id, Long isAdmin, String profile,String name) {
        this.email = email;
        this.id = id;
        this.isAdmin = isAdmin;
        this.profile = profile;
        this.name = name;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Long isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

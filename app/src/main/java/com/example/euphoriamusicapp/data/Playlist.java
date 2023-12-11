package com.example.euphoriamusicapp.data;

public class Playlist {
    private int resourceId;

    private String playlistName, Name,Author;
    private String Image, Url;

    public Playlist() {
    }

    public Playlist(int resourceId, String playlistName, String name, String author, String image, String url) {
        this.resourceId = resourceId;
        this.playlistName = playlistName;
        Name = name;
        Author = author;
        Image = image;
        Url = url;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}

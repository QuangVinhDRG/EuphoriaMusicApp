package com.example.euphoriamusicapp.data;

public class Playlist {
    private int resourceId;
    private String playlistName, playlistAuthor;

    public Playlist(int resourceId, String playlistName, String playlistAuthor) {
        this.resourceId = resourceId;
        this.playlistName = playlistName;
        this.playlistAuthor = playlistAuthor;
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

    public String getPlaylistAuthor() {
        return playlistAuthor;
    }

    public void setPlaylistAuthor(String playlistAuthor) {
        this.playlistAuthor = playlistAuthor;
    }
}

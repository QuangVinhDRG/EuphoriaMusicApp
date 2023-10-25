package com.example.euphoriamusicapp.data;

public class BasicMusicInformation {
    private int resourceId;
    private String songName;
    private String authorName;

    public BasicMusicInformation() {
    }

    public BasicMusicInformation(int resourceId, String songName, String authorName) {
        this.resourceId = resourceId;
        this.songName = songName;
        this.authorName = authorName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}

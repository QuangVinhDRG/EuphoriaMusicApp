package com.example.euphoriamusicapp.data;

public class NewReleaseMusic {
    private int resourceId;
    private String songName, authorName, timeRelease;

    public NewReleaseMusic(int resourceId, String songName, String authorName, String timeRelease) {
        this.resourceId = resourceId;
        this.songName = songName;
        this.authorName = authorName;
        this.timeRelease = timeRelease;
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

    public String getTimeRelease() {
        return timeRelease;
    }

    public void setTimeRelease(String timeRelease) {
        this.timeRelease = timeRelease;
    }
}

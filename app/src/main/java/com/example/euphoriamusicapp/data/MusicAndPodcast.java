package com.example.euphoriamusicapp.data;

import java.io.Serializable;

public class MusicAndPodcast implements  Serializable{
    private int resourceId;
    private String songName;
    private String authorName;
    private String image;
    private String url;
    private boolean latest;
    private boolean featured;
    private int count;
    private int type;

    public MusicAndPodcast() {
    }
    public MusicAndPodcast(int resourceId, String songName, String authorName) {
        this.resourceId = resourceId;
        this.songName = songName;
        this.authorName = authorName;
    }
    public MusicAndPodcast(int resourceId, String songName, String authorName, String image, String url, boolean latest, boolean featured, int count, int type) {
        this.resourceId = resourceId;
        this.songName = songName;
        this.authorName = authorName;
        this.image = image;
        this.url = url;
        this.latest = latest;
        this.featured = featured;
        this.count = count;
        this.type = type;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

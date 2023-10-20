package com.example.euphoriamusicapp.data;

public class HotAlbum {
    private int resourceId;
    private String albumName, authorName;

    public HotAlbum(int resourceId, String albumName, String authorName) {
        this.resourceId = resourceId;
        this.albumName = albumName;
        this.authorName = authorName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}

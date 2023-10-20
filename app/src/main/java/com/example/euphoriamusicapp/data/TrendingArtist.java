package com.example.euphoriamusicapp.data;

public class TrendingArtist {
    private int resourceId;
    private String artistName;

    public TrendingArtist(int resourceId, String artistName) {
        this.resourceId = resourceId;
        this.artistName = artistName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}

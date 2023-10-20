package com.example.euphoriamusicapp.data;

public class Podcast {
    private int resourceId;
    private String podcastName, authorName;

    public Podcast(int resourceId, String podcastName, String authorName) {
        this.resourceId = resourceId;
        this.podcastName = podcastName;
        this.authorName = authorName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getPodcastName() {
        return podcastName;
    }

    public void setPodcastName(String podcastName) {
        this.podcastName = podcastName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}

package com.example.euphoriamusicapp.data;

import java.io.Serializable;

public class Podcast implements Serializable {

    private int resourceId;
    private String authorname;
    private int count;
    private boolean featured;
    private String image;
    private boolean latest;
    private String podcastName;
    private String  url;

    public Podcast() {

    }

    public Podcast(int resourceId, String authorname, int count, boolean featured, String image, boolean latest, String podcastName, String url) {
        this.resourceId = resourceId;
        this.authorname = authorname;
        this.count = count;
        this.featured = featured;
        this.image = image;
        this.latest = latest;
        this.podcastName = podcastName;
        this.url = url;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public String getPodcastName() {
        return podcastName;
    }

    public void setPodcastName(String podcastName) {
        this.podcastName = podcastName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

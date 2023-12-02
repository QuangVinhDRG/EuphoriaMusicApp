package com.example.euphoriamusicapp.data;

public class NewReleaseMusic {
    private int resourceId;
    private String songName;
    private String authorName;
    private String image;
    private String url;

    private boolean latest;
    private boolean featured;
    private int count;



    public NewReleaseMusic() {
    }

    public NewReleaseMusic(int resourceId, String songName, String authorName, String image,String url) {
        this.resourceId = resourceId;
        this.songName = songName;
        this.authorName = authorName;
        this.image = image;
        this.url = url;
    }

    public NewReleaseMusic(int resourceId, String songName, String authorName, String image, String url, boolean latest, boolean featured, int count) {
        this.resourceId = resourceId;
        this.songName = songName;
        this.authorName = authorName;
        this.image = image;
        this.url = url;
        this.latest = latest;
        this.featured = featured;
        this.count = count;

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



    @Override
    public String toString() {
        return "BasicMusicInformation{" +
                "resourceId=" + resourceId +
                ", songName='" + songName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", latest=" + latest +
                ", featured=" + featured +
                ", count=" + count +
                '}';
    }
}

package com.example.euphoriamusicapp.data;

public class RankingMusic {
    private int resourceId, ranking;
    private String songName, authorName;

    public RankingMusic(int resourceId, int ranking, String songName, String authorName) {
        this.resourceId = resourceId;
        this.ranking = ranking;
        this.songName = songName;
        this.authorName = authorName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
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

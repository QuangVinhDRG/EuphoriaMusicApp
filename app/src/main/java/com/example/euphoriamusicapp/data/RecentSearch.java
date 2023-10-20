package com.example.euphoriamusicapp.data;

import com.example.euphoriamusicapp.R;

public class RecentSearch {
    private int resourceId;
    private String searchContent, searchType, searchArtist;

    public RecentSearch(int resourceId, String searchContent, String searchType, String searchArtist) {
        this.resourceId = resourceId;
        this.searchContent = searchContent;
        this.searchType = searchType;
        this.searchArtist = searchArtist;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchArtist() {
        return searchArtist;
    }

    public void setSearchArtist(String searchArtist) {
        this.searchArtist = searchArtist;
    }
}

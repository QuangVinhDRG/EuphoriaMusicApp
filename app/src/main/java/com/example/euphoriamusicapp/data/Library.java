package com.example.euphoriamusicapp.data;

public class Library {
    private int resourceId;
    private String libraryName, libraryQuantity;

    public Library(int resourceId, String libraryName, String libraryQuantity) {
        this.resourceId = resourceId;
        this.libraryName = libraryName;
        this.libraryQuantity = libraryQuantity;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getLibraryQuantity() {
        return libraryQuantity;
    }

    public void setLibraryQuantity(String libraryQuantity) {
        this.libraryQuantity = libraryQuantity;
    }
}

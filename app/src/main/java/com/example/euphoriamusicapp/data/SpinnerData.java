package com.example.euphoriamusicapp.data;

public class SpinnerData {
    private int resourceId;
    private String itemName;

    public SpinnerData(int resourceId, String itemName) {
        this.resourceId = resourceId;
        this.itemName = itemName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}

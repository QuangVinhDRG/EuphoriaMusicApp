package com.example.euphoriamusicapp.data;

public class TopicAndCategory {
    private int resourceId;
    private String TopicAndCategoryName;
    private int type;

    public TopicAndCategory(int resourceId, String topicAndCategoryName,int type) {
        this.resourceId = resourceId;
        this.TopicAndCategoryName = topicAndCategoryName;
        this.type = type;
    }
    public TopicAndCategory() {

    }


    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTopicAndCategoryName() {
        return TopicAndCategoryName;
    }

    public void setTopicAndCategoryName(String topicAndCategoryName) {
        TopicAndCategoryName = topicAndCategoryName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

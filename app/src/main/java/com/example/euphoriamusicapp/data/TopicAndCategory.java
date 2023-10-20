package com.example.euphoriamusicapp.data;

public class TopicAndCategory {
    private int resourceId;
    private String TopicAndCategoryName;

    public TopicAndCategory(int resourceId, String topicAndCategoryName) {
        this.resourceId = resourceId;
        TopicAndCategoryName = topicAndCategoryName;
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
}

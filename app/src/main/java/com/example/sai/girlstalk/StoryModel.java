package com.example.sai.girlstalk;

public class StoryModel {

    String thumbnailUrl, title;

    public StoryModel(String thumbnailUrl, String title) {
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

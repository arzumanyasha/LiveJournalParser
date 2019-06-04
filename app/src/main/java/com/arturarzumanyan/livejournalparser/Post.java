package com.arturarzumanyan.livejournalparser;

public class Post {
    private String postName;
    private String postDescription;
    private String postImgUrl;
    private String link;

    //класс модели поста
    public Post(String postName, String postDescription, String postImgUrl, String link) {
        this.postName = postName;
        this.postDescription = postDescription;
        this.postImgUrl = postImgUrl;
        this.link = link;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostImgUrl() {
        return postImgUrl;
    }

    public void setPostImgUrl(String postImgUrl) {
        this.postImgUrl = postImgUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

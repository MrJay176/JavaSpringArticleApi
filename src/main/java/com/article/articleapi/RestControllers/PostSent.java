package com.article.articleapi.RestControllers;

import com.article.articleapi.Models.PostModel;

public class PostSent {

    String message;
    PostModel postModel;

    public PostSent() {
    }

    public PostSent(String message, PostModel postModel) {
        this.message = message;
        this.postModel = postModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PostModel getPostModel() {
        return postModel;
    }

    public void setPostModel(PostModel postModel) {
        this.postModel = postModel;
    }
}

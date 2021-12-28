package com.zulfikar.studentportal.forum.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Post {
    @SerializedName("post_id")
    private int id;
    @SerializedName("author_bracu_id")
    private int authorBracuId;
    @SerializedName("author_name")
    private String authorName;
    @SerializedName("author_photo")
    private String authorPhoto;
    @SerializedName("post_course")
    private String postCourse;
    @SerializedName("post_content")
    private String postContent;
    @SerializedName("date_created")
    private Date dateCreated;
    @SerializedName("post_semester")
    private String postSemester;
    @SerializedName("post_reactions")
    private HashMap<String, Object> postReactions;
    @SerializedName("post_comments")
    private HashMap<String, Object> postComments;

    public Post(String postContent) {
        this.postContent = postContent;
    }

    public Post(String postContent, String postCourse, String postSemester) {
        this.postContent = postContent;
        this.postCourse = postCourse;
        this.postSemester = postSemester;
    }

    public int getId() {
        return id;
    }

    public int getAuthorBracuId() {
        return authorBracuId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorPhoto() {
        return authorPhoto;
    }

    public String getPostCourse() {
        if (postCourse == null) return "";
        return postCourse;
    }

    public String getPostContent() {
        return postContent;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getPostSemester() {
        if (postSemester == null) return "";
        return postSemester;
    }

    public HashMap<String, Object> getPostReactions() {
        return postReactions;
    }

    public String getPostReactionsCount() {
        if (postReactions == null || !postReactions.containsKey("count")) return "0";
        return String.valueOf((int) Double.parseDouble(Objects.requireNonNull(
                postReactions.getOrDefault("count", ""))
                .toString()));
    }

    public HashMap<String, Object> getPostComments() {
        return postComments;
    }

    public String getPostCommentsCount() {
        if (postComments == null || !postComments.containsKey("count")) return "0";
        return String.valueOf((int) Double.parseDouble(Objects.requireNonNull(
                postComments.getOrDefault("count", ""))
                .toString()));
    }
}

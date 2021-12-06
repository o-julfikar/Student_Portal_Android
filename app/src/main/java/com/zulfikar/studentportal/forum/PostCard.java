package com.zulfikar.studentportal.forum;

public class PostCard {
    public final int authorId, postId, courseId, semesterId;
    public final String authorName, authorPhoto, courseCode, semesterNameYear, postContent, postDate,
            commentsCount, reactionsCount;
    public final CommentCard[] commentCards;

    public PostCard (int postId, int authorId, int courseId, int semesterId, String authorName,
                     String authorPhoto, String courseCode, String semesterNameYear,
                     String postContent, String postDate, String reactionsCount,
                     String commentsCount, CommentCard[] commentCards) {
        this.postId = postId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorPhoto = authorPhoto;
        this.courseCode = courseCode;
        this.courseId = courseId;
        this.semesterId = semesterId;
        this.semesterNameYear = semesterNameYear;
        this.postContent = postContent;
        this.postDate = postDate;
        this.commentsCount = commentsCount;
        this.reactionsCount = reactionsCount;
        this.commentCards = commentCards;
    }
}

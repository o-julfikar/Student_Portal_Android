package com.zulfikar.studentportal.forum.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class PostComments {
    @SerializedName("post_id")
    private int postId;
    private int count;
    private List<PostComment> comments;

    public int getPostId() {
        return postId;
    }

    public int getCount() {
        return count;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public String toString() {
        StringBuilder comments = new StringBuilder();
        String sep = "";
        for (PostComment comment : this.comments) {
            comments.append(sep).append(comment);
            sep = ",\n";
        }

        return "Post ID: " + postId + "\n" +
                "Count: " + count + "\n" +
                "Comments:\n" + comments;
    }


    public static class PostComment {
        @SerializedName("post_id")
        private int postId;
        private int comment_id;
        private int author_bracu_id;
        private int reactions_count;
        private int replies_count;
        private String author_name;
        private String author_photo;
        @SerializedName("comment_content")
        private String commentContent;
        private Date date_created;

        public PostComment(int postId, String commentContent) {
            this.postId = postId;
            this.commentContent = commentContent;
        }

        public int getComment_id() {
            return comment_id;
        }

        public int getAuthor_bracu_id() {
            return author_bracu_id;
        }

        public int getReactions_count() {
            return reactions_count;
        }

        public int getReplies_count() {
            return replies_count;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public String getAuthor_photo() {
            return author_photo;
        }

        public String getContent() {
            return commentContent;
        }

        public Date getDate_created() {
            return date_created;
        }

        public String toString() {
            String indent = "    ";
            return  indent + "{" + "\n" +
                    indent + indent + "comment_id: " + comment_id + "\n" +
                    indent + indent + "author_bracu_id: " + author_bracu_id + "\n" +
                    indent + indent + "author_name: " + author_name + "\n" +
                    indent + indent + "author_photo: " + author_photo + "\n" +
                    indent + indent + "content: " + commentContent + "\n" +
                    indent + indent + "date_created: " + date_created + "\n" +
                    indent + indent + "reactions_count: " + reactions_count + "\n" +
                    indent + indent + "replies_count: " + replies_count + "\n" +
                    indent + "}";
        }
    }
}

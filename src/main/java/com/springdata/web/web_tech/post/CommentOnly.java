package com.springdata.web.web_tech.post;

public class CommentOnly {

    private String Comment;
    private int up;
    private int down;

    public CommentOnly(String comment, int up, int down) {
        Comment = comment;
        this.up = up;
        this.down = down;
    }

    public String getComment() {
        return Comment;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public String getVotes(){
        return up + " " + down;
    }
}

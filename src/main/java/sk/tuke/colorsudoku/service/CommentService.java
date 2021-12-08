package sk.tuke.colorsudoku.service;

import sk.tuke.colorsudoku.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);

    List<Comment> getNewestComments();

    void reset();
}

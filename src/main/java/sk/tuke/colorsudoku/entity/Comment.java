package sk.tuke.colorsudoku.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private int id;

    private String author;
    private String text;
    private Date commentDate;

    public Comment() {
    }

    public Comment(String author, String text) {
        this.author = author;
        this.text = text;
        this.commentDate = new Date(System.currentTimeMillis());
    }

    public Comment(String author, String text, Date commentDate) {
        this.author = author;
        this.text = text;
        this.commentDate = commentDate;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public Date getCommentDate() {
        return commentDate;
    }
}

package sk.tuke.colorsudoku.service;

import sk.tuke.colorsudoku.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {
    public static final String INSERT = "INSERT INTO comment (author, text, comment_date) VALUES (?,?,?);";
    public static final String SELECT_TOP_10 = "SELECT author, text, comment_date from comment order by comment_date desc limit 10;";
    public static final String DELETE = "DELETE FROM comment;";
    public static final String JDBC_URL = "jdbc:postgresql://localhost/coloredsudoku";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "postgres";

    @Override
    public void addComment(Comment comment) {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(INSERT)
        ){
            statement.setString(1,comment.getAuthor());
            statement.setString(2,comment.getText());
            statement.setTimestamp(3, new Timestamp(comment.getCommentDate().getTime()));
            statement.executeUpdate();
        }catch(Exception e){
            throw new SudokuException("Connection failed to CommentServiceJDBC", e);
        }
    }

    @Override
    public List<Comment> getNewestComments() {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_TOP_10);
        ){
            List<Comment> comments = new ArrayList<>();
            while (rs.next()){
                comments.add(new Comment(rs.getString(1),rs.getString(2),rs.getTimestamp(3)));
            }
            return comments;
        } catch (SQLException e){
            throw new SudokuException("Connection failed to CommentServiceJDBC", e);
        }
    }

    @Override
    public void reset() {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
            Statement statement = connection.createStatement();
        ){
            statement.executeUpdate(DELETE);
        }catch (Exception e){
            throw new SudokuException("Connection failed to CommentServiceJDBC", e);
        }
    }
}

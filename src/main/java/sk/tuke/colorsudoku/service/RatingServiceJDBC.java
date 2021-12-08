package sk.tuke.colorsudoku.service;

import sk.tuke.colorsudoku.entity.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingServiceJDBC implements RatingService{
    public static final String INSERT = "INSERT INTO rating (playername, stars, ratingdate) VALUES (?,?,?);";
    public static final String SELECT_TOP_10 = "SELECT playername, stars, ratingdate from rating order by ratingdate desc;";
    public static final String DELETE = "DELETE FROM rating;";
    public static final String JDBC_URL = "jdbc:postgresql://localhost/coloredsudoku";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "postgres";

    @Override
    public void addRating(Rating rating) {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(INSERT)
        ){
            statement.setString(1,rating.getPlayerName());
            statement.setInt(2,rating.getStars());
            statement.setTimestamp(3, new Timestamp(rating.getRatingDate().getTime()));
            statement.executeUpdate();
        }catch(Exception e){
            throw new SudokuException("Connection failed to RatingServiceJDBC", e);
        }
    }

    @Override
    public List<Rating> getNewestRatings() {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_TOP_10);
        ){
            List<Rating> ratings = new ArrayList<>();
            while (rs.next()){
                ratings.add(new Rating(rs.getString(1),rs.getInt(2),rs.getTimestamp(3)));
            }
            return ratings;
        } catch (SQLException e){
            throw new SudokuException("Connection failed to RatingServiceJDBC", e);
        }
    }

    @Override
    public void reset() {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
            Statement statement = connection.createStatement();
        ){
            statement.executeUpdate(DELETE);
        }catch (Exception e){
            throw new SudokuException("Connection failed to RatingServiceJDBC", e);
        }
    }
}

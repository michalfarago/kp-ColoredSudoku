package sk.tuke.colorsudoku.service;

import sk.tuke.colorsudoku.core.Difficulty;
import sk.tuke.colorsudoku.entity.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService{

    public static final String INSERT = "INSERT INTO score (playername, points, playat, difficulty) VALUES (?,?,?,?);";
    public static final String SELECT_TOP_10 = "SELECT playername, points, playat, difficulty from score order by points desc limit 10;";
    public static final String DELETE = "DELETE FROM score;";
    public static final String JDBC_URL = "jdbc:postgresql://localhost/coloredsudoku";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "postgres";

    @Override
    public void addScore(Score score) {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(INSERT)
        ){
            statement.setString(1,score.getPlayerName());
            statement.setInt(2,score.getPoints());
            statement.setTimestamp(3, new Timestamp(score.getPlayAt().getTime()));
            statement.setInt(4,score.getDifficulty().getValue());
            statement.executeUpdate();
        }catch(Exception e){
            throw new SudokuException("Connection failed to ScoreServiceJDBC", e);
        }
    }

    @Override
    public List<Score> getNewestScores() {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_TOP_10);
        ){
            List<Score> scores = new ArrayList<>();
            while (rs.next()){
                scores.add(new Score(rs.getString(1),rs.getInt(2),rs.getTimestamp(3),rs.getInt(4)));
            }
            return scores;
        } catch (SQLException e){
            throw new SudokuException("Connection failed to ScoreServiceJDBC", e);
        }
    }

    @Override
    public void reset() {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
            Statement statement = connection.createStatement();
        ){
            statement.executeUpdate(DELETE);
        }catch (Exception e){
            throw new SudokuException("Connection failed to ScoreServiceJDBC", e);
        }
    }
}

package sk.tuke.colorsudoku.entity;

import sk.tuke.colorsudoku.core.Difficulty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Score {
    @Id
    @GeneratedValue
    private int id;

    private String playerName;
    private int points;
    private Date playAt;
    private Difficulty difficulty;

    public Score() {
    }

    public Score(String playerName, int points, Difficulty difficulty) {
        this.playerName = playerName;
        this.points = points;
        this.difficulty = difficulty;
        this.playAt = new Date(System.currentTimeMillis());
    }

    public Score(String playerName, int points, Date playAt, int difficulty) {
        this.playerName = playerName;
        this.points = points;
        switch (difficulty){
            case 1: this.difficulty = Difficulty.HARD; break;
            case 2: this.difficulty = Difficulty.MEDIUM; break;
            case 3: this.difficulty = Difficulty.EASY; break;
        }
        this.playAt = playAt;
    }

    public Score(String playerName, int points, int difficulty) {
        this.playerName = playerName;
        this.points = points;
        switch (difficulty){
            case 1: this.difficulty = Difficulty.HARD;
            case 2: this.difficulty = Difficulty.MEDIUM;
            case 3: this.difficulty = Difficulty.EASY;
        }
        this.playAt = new Date(System.currentTimeMillis());
    }

    public Score(String playerName, int points, Date playAt) {
        this.playerName = playerName;
        this.points = points;
        this.playAt = playAt;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPoints() {
        return points;
    }

    public Date getPlayAt() {
        return playAt;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}

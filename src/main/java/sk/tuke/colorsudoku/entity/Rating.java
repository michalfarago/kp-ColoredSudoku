package sk.tuke.colorsudoku.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    private int id;

    private String playerName;
    private int stars;
    private Date ratingDate;

    public Rating() {
    }

    public Rating(String playerName, int stars) {
        this.playerName = playerName;
        this.stars = stars;
        this.ratingDate = new Date(System.currentTimeMillis());
    }

    public Rating(String playerName, int stars, Date ratingDate) {
        this.playerName = playerName;
        this.stars = stars;
        this.ratingDate = ratingDate;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getStars() {
        return stars;
    }

    public Date getRatingDate() {
        return ratingDate;
    }
}

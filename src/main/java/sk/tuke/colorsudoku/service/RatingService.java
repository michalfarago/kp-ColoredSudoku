package sk.tuke.colorsudoku.service;

import sk.tuke.colorsudoku.entity.Rating;

import java.util.List;

public interface RatingService {
    void addRating(Rating rating);

    List<Rating> getNewestRatings();

    void reset();
}

package sk.tuke.colorsudoku.service;


import sk.tuke.colorsudoku.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score);

    List<Score> getNewestScores();

    void reset();
}

package sk.tuke.colorsudoku.service;

import org.springframework.web.client.RestTemplate;
import sk.tuke.colorsudoku.entity.Score;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ScoreServiceRestClient implements ScoreService{
    private String url = "http://localhost:8080/api/score";

    private RestTemplate restTemplate;
    @Override
    public void addScore(Score score) {
        restTemplate.postForEntity(url, score, Score.class);
    }

    @Override
    public List<Score> getNewestScores() {
        return Arrays.asList(restTemplate.getForEntity(url+"/topscores",Score[].class).getBody());
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException();
    }
}

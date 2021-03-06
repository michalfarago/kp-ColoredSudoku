package sk.tuke.colorsudoku.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.colorsudoku.entity.Score;
import sk.tuke.colorsudoku.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping("api/score")
public class ScoreServiceRest {
    @Autowired
    private ScoreService scoreService;

    @PostMapping
    public void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }

    @GetMapping("/topscores")
    public List<Score> getNewestScores() {
        return scoreService.getNewestScores();
    }
}

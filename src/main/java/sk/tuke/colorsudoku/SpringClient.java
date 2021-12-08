package sk.tuke.colorsudoku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.colorsudoku.core.Difficulty;
import sk.tuke.colorsudoku.core.Gameboard;
import sk.tuke.colorsudoku.service.*;

@SpringBootApplication
@Configuration
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);
    }

    @Bean
    public Gameboard gameboard(){
        return new Gameboard(3, Difficulty.EASY);
    }

    @Bean
    public CommentService commentService(){
        return new CommentServiceJDBC();
    }

    @Bean
    public RatingService ratingService(){
        return new RatingServiceJDBC();
    }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceJDBC();
    }
}

package sk.tuke.colorsudoku.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.colorsudoku.service.*;

@SpringBootApplication
@Configuration
@EntityScan("sk.tuke.colorsudoku")
public class ColorSudokuServer {
    public static void main(String[] args) {
        SpringApplication.run(ColorSudokuServer.class);
    }

    @Bean
    public CommentService commentService(){
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService(){ return new RatingServiceJPA(); }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceJPA();
    }
}

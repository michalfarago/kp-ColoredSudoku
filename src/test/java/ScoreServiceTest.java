import sk.tuke.colorsudoku.entity.Score;
import org.junit.Assert;
import org.junit.Test;
import sk.tuke.colorsudoku.service.ScoreService;
import sk.tuke.colorsudoku.service.ScoreServiceJDBC;
import sk.tuke.colorsudoku.service.ScoreServiceJPA;
import sk.tuke.colorsudoku.service.ScoreServiceRestClient;


public class ScoreServiceTest {
    private ScoreService createService(){
        return new ScoreServiceJPA();
    }

    @Test
    public void testAddScore(){
        ScoreService commentService = createService();
        commentService.reset();
        commentService.addScore(new Score("Jano", 50, 1));
        Assert.assertEquals(1, commentService.getNewestScores().size());
    }

    @Test
    public void testAddScore10() {
        ScoreService commentService = createService();
        commentService.reset();
        for (int i = 0; i < 20; i++)
            commentService.addScore(new Score("Jaro", 50, 1));
        Assert.assertEquals(10, commentService.getNewestScores().size());
    }

    @Test
    public void testReset(){
        ScoreService commentService = createService();
        commentService.reset();
        Assert.assertEquals(0, commentService.getNewestScores().size());
    }
}

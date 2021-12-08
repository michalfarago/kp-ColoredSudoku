import sk.tuke.colorsudoku.entity.Rating;
import org.junit.Assert;
import org.junit.Test;
import sk.tuke.colorsudoku.service.RatingService;
import sk.tuke.colorsudoku.service.RatingServiceJDBC;

public class RatingServiceTest {
    private RatingService createService(){
        return new RatingServiceJDBC();
    }

    @Test
    public void testAddComment(){
        RatingService commentService = createService();
        commentService.reset();
        commentService.addRating(new Rating("Jano", 5));
        Assert.assertEquals(1, commentService.getNewestRatings().size());
    }

    @Test
    public void testAddRating10() {
        RatingService commentService = createService();
        commentService.reset();
        for (int i = 0; i < 20; i++)
            commentService.addRating(new Rating("Jaro", 5));
        Assert.assertEquals(10, commentService.getNewestRatings().size());
    }

    @Test
    public void testReset(){
        RatingService commentService = createService();
        commentService.reset();
        Assert.assertEquals(0, commentService.getNewestRatings().size());
    }
}

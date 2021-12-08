import sk.tuke.colorsudoku.entity.Comment;
import org.junit.Assert;
import org.junit.Test;
import sk.tuke.colorsudoku.service.CommentService;
import sk.tuke.colorsudoku.service.CommentServiceJDBC;

public class CommentServiceTest {

    private CommentService createService(){
        return new CommentServiceJDBC();
    }

    @Test
    public void testAddComment(){
        CommentService commentService = createService();
        commentService.reset();
        commentService.addComment(new Comment("Jano", "Super"));
        Assert.assertEquals(1, commentService.getNewestComments().size());
    }

    @Test
    public void testAddScore10() {
        CommentService commentService = createService();
        commentService.reset();
        for (int i = 0; i < 20; i++)
            commentService.addComment(new Comment("Jaro", "Comment number " + i));
        Assert.assertEquals(10, commentService.getNewestComments().size());
    }

    @Test
    public void testReset(){
        CommentService commentService = createService();
        commentService.reset();
        Assert.assertEquals(0, commentService.getNewestComments().size());
    }
}

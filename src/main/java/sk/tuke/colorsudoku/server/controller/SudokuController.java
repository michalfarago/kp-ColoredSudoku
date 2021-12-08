package sk.tuke.colorsudoku.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.colorsudoku.core.Difficulty;
import sk.tuke.colorsudoku.core.Gameboard;
import sk.tuke.colorsudoku.core.Tile;
import sk.tuke.colorsudoku.entity.Comment;
import sk.tuke.colorsudoku.entity.Rating;
import sk.tuke.colorsudoku.entity.Score;
import sk.tuke.colorsudoku.features.Hint;
import sk.tuke.colorsudoku.service.CommentService;
import sk.tuke.colorsudoku.service.RatingService;
import sk.tuke.colorsudoku.service.ScoreService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SudokuController {

    @Autowired
    private RatingService ratingService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;

    private Random random = new Random();
    private int selectedColor = 1;
    private String playername = "guest";
    private Gameboard gameboard;
    private boolean gamestatus = false;

    @RequestMapping("/ratingpage")
    public String sudokuRatings(Model model){

        double result = 0.0;
        List<Rating> ratings = new ArrayList<>();
        ratings = ratingService.getNewestRatings();
        for(Rating r : ratings){
            result += r.getStars();
        }

        model.addAttribute("averagerating", Math.round( (result/ratings.size()) * 100.0 / 100.0));
        model.addAttribute("ratinglist",ratingService.getNewestRatings());

        return "ratingpage";
    }

    @RequestMapping("/commentpage")
    public String sudokuComments(Model model){
        model.addAttribute("commentlist",commentService.getNewestComments());
        return "commentpage";
    }

    @RequestMapping("/leaderboard")
    public String sudokuScores(Model model){
        model.addAttribute("scorelist",scoreService.getNewestScores());
        return "scorepage";
    }


    @RequestMapping("/choosecolor")
    public String changeColor(@RequestParam int value, Model model){
        this.selectedColor = value;
        model.addAttribute("playername", playername);
        return "sudoku";
    }

    @GetMapping("/getMyScores")
    @ResponseBody
    public List<Score> getThis(){
        return scoreService.getNewestScores();
    }

    @RequestMapping("/newgame")
    public String newGame(@RequestParam int gamedifficulty, @RequestParam int squaresize, @RequestParam String name, Model model){
        this.playername = name.length() != 0 ? name : "guest";
        switch (gamedifficulty){
            case 3:this.gameboard = new Gameboard(squaresize,Difficulty.EASY);   break;
            case 2:this.gameboard = new Gameboard(squaresize,Difficulty.MEDIUM); break;
            case 1:this.gameboard = new Gameboard(squaresize,Difficulty.HARD);   break;
        }
        model.addAttribute("playername", playername);
        this.gamestatus = false;
        return "sudoku";
    }

    @RequestMapping("/loadgame")
    public String loadGame(Model model){
        model.addAttribute("playername", playername);
        return "sudoku";
    }

    @RequestMapping("/changeplayername")
    public String changePlayerName(@RequestParam String playername, Model model){
        this.playername = playername;
        model.addAttribute("playername", playername);
        return "sudoku";
    }

    @RequestMapping("/addcomment")
    public String addCommentToDB(@RequestParam String text, Model model){
        commentService.addComment(new Comment(this.playername,text));
        model.addAttribute("playername", playername);
        return "sudoku";
    }

    @RequestMapping("/addrating")
    public String addRatingToDB(@RequestParam int stars, Model model){
        ratingService.addRating(new Rating(this.playername, stars));
        model.addAttribute("playername", playername);
        return "sudoku";
    }

    @RequestMapping("/mainmenu")
    public String mainMenu(){
        return "mainmenu";
    }

    @RequestMapping("/savescore")
    public String saveScore(){
        this.scoreService.addScore(new Score(this.playername, this.gameboard.getPoints(), this.gameboard.getDifficulty()));
        this.playername = "";
        this.gameboard = null;
        this.gamestatus = false;
        return "mainmenu";
    }

    @RequestMapping("/changesquare")
    public String changeSquare(@RequestParam int x, @RequestParam int y, Model model){
        model.addAttribute("playername", playername);
        this.gameboard.WriteToTile(x, y, selectedColor);
        this.gamestatus = this.gameboard.IsGameSolved() ? true : false;
        return "sudoku";
    }

    @RequestMapping("/changecheckhelper")
    public String changeCheckHelper(@RequestParam boolean status, Model model){
        model.addAttribute("playername", playername);
        this.gameboard.getCheckHelper().setStatus(status);
        return "sudoku";
    }

    @RequestMapping("/askhint")
    public String askHint(Model model){
        model.addAttribute("playername", playername);
        Hint hint = gameboard.getHintAssist().GiveHint();
        model.addAttribute("hint",hint);
        return "sudoku";
    }

    public String colorOptions(){
        StringBuilder stringBuilder = new StringBuilder();

        for(int y = 0; y < gameboard.getSquareSize(); y++) {
            stringBuilder.append("<tr>\n");
            for(int x = 1; x <= gameboard.getSquareSize(); x++){
                stringBuilder.append("<td class='tile" + (y * gameboard.getSquareSize()+ x) + " chooseBar'>\n");
                if(selectedColor == y * gameboard.getSquareSize() + x) {
                    stringBuilder.append("<button class=\"tileButton font-weight-bold\" onclick=\"location.href='/choosecolor?value=" + (y * gameboard.getSquareSize() + x) + "'\" type=\"button\">X</button>\n");
                }
                else
                {
                    stringBuilder.append("<button class=\"tileButton font-weight-bold\" onclick=\"location.href='/choosecolor?value=" + (y * gameboard.getSquareSize() + x) + "'\" type=\"button\"></button>\n");
                }
                stringBuilder.append("</td>\n");
            }
            stringBuilder.append("</tr>\n");
        }
        return stringBuilder.toString();
    }

    public String sudokuGameboard(){
        Tile[][] tileset = this.gameboard.getTileset();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<tr class=\"blockTile\">\n");
        stringBuilder.append("<th class=\"blockTile\"></th>\n");

        for (int y = 0; y < gameboard.getRowCount(); y++) {
            stringBuilder.append("<th class=\"text-center blockTile bg-light\">" + y + "</th>");
        }

        stringBuilder.append("</tr>\n");

        for (var y = 0; y < gameboard.getRowCount(); y++){
            stringBuilder.append("<tr class=\"blockTile\">\n");
            stringBuilder.append("<th class=\"text-center blockTile bg-light\">" + y + "</th>\n");
                for (var x = 0; x < gameboard.getColCount(); x++){
                    if (tileset[y][x].isEditable()){
                        stringBuilder.append("<td class=\"tile" + tileset[y][x].getValue() + " blockTile\">\n");
                        stringBuilder.append("<button class=\"tileButton\"  onclick=\"location.href='changesquare?x=" + x + "&y=" + y + "'" + "\" type=\"button\"></button>\n");
                        stringBuilder.append("</td>\n");
                    }
                    else
                    {
                        stringBuilder.append("<td class=\"tile" + tileset[y][x].getValue() + " blockTile lock\">\n");
                        stringBuilder.append("</td>\n");
                    }
                }
            stringBuilder.append("</tr>\n");
        }

        return stringBuilder.toString();
    }

    public String userImage(){
        return "images/user_"+(random.nextInt(2) + 1)+".png";
    }

    public String scoreDifficulty(Score score){
        return "images/dificulty" + score.getDifficulty().getValue() + ".png";
    }

    public String tileSet(){
        return "<link rel=\"stylesheet\" href=\"css/tile" + this.gameboard.getSquareSize() + ".css\"/>";
    }

    public String hintSquare(Hint hint){
        return "blockTile p-2 tile" + hint.getValue();
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public String getPlayername() {
        return playername;
    }

    public int getPoints() {
        return gameboard.getPoints();
    }

    public Gameboard getGameboard() {
        return gameboard;
    }

    public boolean isGamestatus() {
        return gamestatus;
    }
}

// GameDriver.java
public class GameDriver {
    public static void main(String[] args) {
        GameRules rules = new StandardRuleStrategy();
        Game game = new CustomGame(rules);

        // Add players
        game.addPlayer(new Player("Ahmad"));
        game.addPlayer(new Player("samer"));
        game.addPlayer(new Player("Sami"));
        game.addPlayer(new Player("Ali"));

        // Start the game
        game.play();
    }
}

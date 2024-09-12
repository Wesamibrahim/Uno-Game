import java.util.*;

public class CustomGame extends Game {
    public CustomGame(GameRules rules) {
        super(rules);
        /*this.deck =new StandardDeck();
        this.rules = new StandardRuleStrategy();*/
        this.deck = new CustomDeck();
        this.rules = new CustomRuleStrategy();

    }
@Override
    protected void initializeDiscardPile(){
        super.initializeDiscardPile();
}

    @Override
    int determineInitialHandSize() {
        return rules.initialHandSize();
    }

    @Override
    void handleInvalidPlay(Player player) {
        // Default implementation: just print a message
        System.out.println(player.getName() + " could not play a card and drew a card.");
    }

    @Override
    void applyPenalty(List<Player> players, Deck deck) {
        rules.applyPenalty(players,deck);
    }

    @Override
    void handleDrawTwo(Player player, Deck deck) {
        rules.onDraw(player,deck);
    }

    @Override
    void applyCardEffect(Card playedCard, Player currentPlayer, Deck deck) {
        switch (playedCard.getType()) {
            case "Wild" -> {
                String chosenColor = currentPlayer.findMostFrequentColor();
                topCard = new Card(chosenColor, playedCard.getType());
                rules.setColor(chosenColor);
                System.out.println(currentPlayer.getName() + " chose color " + chosenColor);
            }
            case "Wild Draw Four" -> {
                String chosenColor = currentPlayer.findMostFrequentColor();
                topCard = new Card(chosenColor, playedCard.getType());
                rules.setColor(chosenColor);
                System.out.println(currentPlayer.getName() + " chose color " + chosenColor);

                Player nextPlayer = players.get((currentPlayerIndex + 1) % players.size());
                for (int i = 0; i < 4; i++) {
                    nextPlayer.drawCard(deck.drawCard());
                }
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // Skip the next player's turn
                System.out.println(nextPlayer.getName() + " turn is skipped and has to draw 4");
            }
            case "Skip" -> super.currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            case "Reverse" -> {
                Collections.reverse(players);
                super.currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            }
            case "Draw Two" -> DrawTwo(players.get((currentPlayerIndex + 1) % players.size()), deck);
            case "Steal" -> {
                Player nextPlayer = players.get((currentPlayerIndex + 1) % players.size());
                stealCard(currentPlayer, nextPlayer);
            }
            case "Donation" -> {
                Player nextPlayer = players.get((currentPlayerIndex + 1) % players.size());
                donateCard(currentPlayer, nextPlayer);
            }
            // Add more card types as needed
            default -> topCard = playedCard;
        }
    }

    private void DrawTwo(Player player, Deck deck) {
        player.drawCard(deck.drawCard());
        player.drawCard(deck.drawCard());
    }

    private void stealCard(Player currentPlayer, Player nextPlayer) {
        if (!nextPlayer.getHand().isEmpty()) {
            Card stolenCard = nextPlayer.getHand().remove(new Random().nextInt(nextPlayer.getHand().size()));
            currentPlayer.drawCard(stolenCard);
            System.out.println(currentPlayer.getName() + " stole a card from " + nextPlayer.getName());
        }
    }

    private void donateCard(Player currentPlayer, Player nextPlayer) {
        if (!currentPlayer.getHand().isEmpty()) {
            Card donatedCard = currentPlayer.getHand().remove(new Random().nextInt(currentPlayer.getHand().size()));
            nextPlayer.drawCard(donatedCard);
            System.out.println(currentPlayer.getName() + " donated a card to " + nextPlayer.getName());
        }
    }
}
import java.util.*;

public abstract class Game {
    protected List<Player> players;
    protected Deck deck;
    protected GameRules rules;
    protected Card topCard;
    protected int currentPlayerIndex;

    public Game(GameRules rules) {
        this.rules = rules;
        this.players = new ArrayList<>();
        this.topCard = null;
        this.currentPlayerIndex = 0;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    abstract int determineInitialHandSize();
    abstract void handleInvalidPlay(Player player);
    abstract void applyPenalty(List<Player> players, Deck deck);
    abstract void handleDrawTwo(Player player, Deck deck);
    abstract void applyCardEffect(Card playedCard, Player currentPlayer, Deck deck);

    private void printGameStatus() {
        System.out.println("\n--- Game Status ---");
        System.out.println("Top Card: " + (topCard != null ? topCard : "None"));
        for (Player player : players) {
            System.out.println(player.getName() + "'s Hand: " + player.getHand());
        }
    }

    private void chooseDealer() {
        System.out.println("Choosing dealer...");
        Map<Player, Card> playerCards = new HashMap<>();
        for (Player player : players) {
            Card card = deck.drawCard();
            playerCards.put(player, card);
            System.out.println(player.getName() + " drew " + card);
        }

        Player dealer = Collections.max(playerCards.entrySet(),
                Map.Entry.comparingByValue(
                        Comparator.comparingInt(card -> {
                            if (card.getType().startsWith("Numbered")) {
                                return Integer.parseInt(card.getType().split(" ")[1]);
                            } else {
                                return 0;
                            }
                        })
                )
        ).getKey();

        System.out.println(dealer.getName() + " is the dealer.");
        currentPlayerIndex = (players.indexOf(dealer) + 1) % players.size();
    }

    private void dealCards() {
        System.out.println("Dealing cards...");
        for (Player player : players) {
            for (int i = 0; i < determineInitialHandSize(); i++) {
                player.drawCard(deck.drawCard());
            }
        }
        printGameStatus();
    }

    protected void initializeDiscardPile() {
        System.out.println("Initializing discard pile...");
        topCard = deck.drawCard();
        System.out.println("Top card of the discard pile: " + topCard);
        if ("Wild".equals(topCard.getType()) || "Wild Draw Four".equals(topCard.getType())) {
            String startingColor = players.get(currentPlayerIndex).findMostFrequentColor();
            System.out.println("Wild card revealed. Starting color is " + startingColor + " decided by " + players.get(currentPlayerIndex).getName());
            rules.setColor(startingColor);
        } else {
            rules.setColor(topCard.getColor());
        }
    }

    public void play() {
        chooseDealer();
        dealCards();
        initializeDiscardPile();

        boolean gameWon = false;

        while (!gameWon) {
            Player currentPlayer = players.get(currentPlayerIndex);
            printGameStatus();
            System.out.println("Current player: " + currentPlayer.getName());

            if (currentPlayer.hasWon()) {
                printGameStatus();
                System.out.println(currentPlayer.getName() + " has won!");
                gameWon = true;
                continue;
            }

            Card playedCard = currentPlayer.playCard(topCard, rules);

            if (playedCard != null && rules.isPlayable(playedCard, topCard)) {
                applyCardEffect(playedCard, currentPlayer, deck);
                deck.discardCard(playedCard);
                System.out.println(currentPlayer.getName() + " played: " + playedCard);
                currentPlayer.callUno();

                if (currentPlayer.hasWon()) {
                    printGameStatus();
                    System.out.println(currentPlayer.getName() + " has won!");
                    applyPenalty(players, deck);
                    gameWon = true;
                }
            } else {
                Card drawnCard = deck.drawCard();
                currentPlayer.drawCard(drawnCard);
                System.out.println(currentPlayer.getName() + " drew: " + drawnCard);

                if (rules.isPlayable(drawnCard, topCard)) {
                    topCard = drawnCard;
                    deck.discardCard(drawnCard);
                    System.out.println(currentPlayer.getName() + " played the drawn card: " + drawnCard);
                    applyCardEffect(drawnCard, currentPlayer, deck);
                } else {
                    handleInvalidPlay(currentPlayer);
                }
            }

            if (!currentPlayer.hasCalledUno() && currentPlayer.getHand().size() == 1) {
                System.out.println(currentPlayer.getName() + " forgot to say UNO! They draw 2 cards.");
                for (int i = 0; i < 2; i++) {
                    currentPlayer.drawCard(deck.drawCard());
                }
            }
            currentPlayer.resetUno();
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }
}

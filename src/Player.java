import java.util.*;

class Player {
    private final String name;
    private final List<Card> hand;
    private boolean hasCalledUno;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.hasCalledUno = false;
    }

    public String getName() {
        return name;
    }

    public void drawCard(Card card) {
        if (card != null) {
            hand.add(card);
        }
    }

    public Card playCard(Card topCard, GameRules rules) {
        for (Card card : hand) {
            if (rules.isPlayable(card, topCard)) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    public String findMostFrequentColor() {
        Map<String, Integer> colorCount = new HashMap<>();
        for (Card card : hand) {
            if (!card.getType().equals("Wild") && !card.getType().equals("Wild Draw Four")) {
                colorCount.put(card.getColor(), colorCount.getOrDefault(card.getColor(), 0) + 1);
            }
        }

        if (colorCount.isEmpty()) {
            return "none";
        }
        return Collections.max(colorCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public boolean hasWon() {
        return hand.isEmpty();
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }

    public void callUno() {
        if (hand.size() == 1) {
            hasCalledUno = true;
            System.out.println(name + " says: UNO!");
        }
    }

    public boolean hasCalledUno() {
        return hasCalledUno;
    }

    public void resetUno() {
        hasCalledUno = false;
    }
}

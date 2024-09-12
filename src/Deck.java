import java.util.*;

public abstract class Deck {
    protected List<Card> cards;
    protected LinkedList<Card> drawPile;
    protected LinkedList<Card> discardPile;

    public Deck() {
        cards = new LinkedList<>();
        initializeDeck();
        shuffle();
        drawPile = new LinkedList<>(cards);
        discardPile = new LinkedList<>();
        System.out.println("Deck initialized with " + drawPile.size() + " cards in drawPile.");
    }

    protected abstract void initializeDeck();

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (drawPile.isEmpty()) {
            reshuffle();
        }
        return drawPile.pop();
    }

    private void reshuffle() {
        if (discardPile.isEmpty()) {
            throw new NoSuchElementException("No cards left to draw and discard pile is empty!");
        }
        System.out.println("Reshuffle triggered!");
        System.out.println("DrawPile before reshuffle: " + drawPile.size() + " cards");
        System.out.println("DiscardPile before reshuffle: " + discardPile.size() + " cards");

        drawPile.addAll(discardPile);
        discardPile.clear();
        shuffle();
        drawPile = new LinkedList<>(cards);

        System.out.println("DrawPile after reshuffle: " + drawPile.size() + " cards");
        System.out.println("DiscardPile after reshuffle: " + discardPile.size() + " cards");
    }

    public void discardCard(Card card) {
        discardPile.push(card);
    }
}

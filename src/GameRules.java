import java.util.*;

interface GameRules {
    boolean isPlayable(Card card, Card topCard);
    void setColor(String color);
    void applyPenalty(List<Player> players, Deck deck);
    int initialHandSize();
    void onDraw(Player player, Deck deck);

}
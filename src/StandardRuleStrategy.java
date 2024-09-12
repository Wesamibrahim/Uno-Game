import java.util.*;

class StandardRuleStrategy implements GameRules {
    private String currentColor;

    @Override
    public boolean isPlayable(Card card, Card topCard) {
        if (card == null || topCard == null) {
            return false;
        }
        return card.getColor().equals(topCard.getColor()) ||
                card.getType().equals(topCard.getType()) ||
                card.getColor().equals(currentColor) ||
                card.getType().equals("Wild") ||
                card.getType().equals("Wild Draw Four");
    }

    @Override
    public void setColor(String color) {
        this.currentColor = color;
    }

    @Override
    public void applyPenalty(List<Player> players, Deck deck) {
        for(int i=0;i<players.size();i++)
        if(players.get(i).hasWon())
        {
            int x = i+1;
            while (!(players.get((x)%players.size()).getName().equals(players.get(i).getName()))) {
                System.out.println(players.get((x) % players.size()).getName() + " is a loser :)");
                x++;
            }

        }

    }

    @Override
    public int initialHandSize() {
        return 7;
    }

    @Override
    public void onDraw(Player player, Deck deck) {
            player.drawCard(deck.drawCard());
            player.drawCard(deck.drawCard());
        }




}
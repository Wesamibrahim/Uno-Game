public class CustomDeck extends Deck {
    @Override
    protected void initializeDeck() {
        String[] colors = {"Magenta", "Pink", "Brown", "Cyan"};
        String[] types = {"Numbered", "Skip", "Reverse", "Draw Two", "Wild", "Wild Draw Four"};

        for (String color : colors) {
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i <= 9; i++) {
                    cards.add(new Card(color, "Numbered " + i));
                }
                cards.add(new Card(color, "Skip"));
                cards.add(new Card(color, "Reverse"));
                cards.add(new Card(color, "Draw Two"));
            }
            cards.add(new Card(color, "Numbered " + 0));
        }

        for (int i = 0; i < 6; i++) {
            cards.add(new Card("Wild", "Wild"));
            cards.add(new Card("Wild", "Wild Draw Four"));
        }

        cards.add(new Card("Special", "Steal"));
        cards.add(new Card("Special", "Donation"));
    }
}

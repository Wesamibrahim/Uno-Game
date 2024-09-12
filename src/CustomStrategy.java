class CustomRuleStrategy extends StandardRuleStrategy {
    @Override
    public boolean isPlayable(Card card, Card topCard) {
        // Handle new card types and elemental cards
        if (card.getType().equals("Steal") || card.getType().equals("Donation")) {
            return true; // Custom cards are always playable (for simplicity)
        }
        return super.isPlayable(card, topCard);
    }

}

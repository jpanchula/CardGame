import java.util.ArrayList;

public class Deck {
    /* Instance variables */
    private ArrayList<Card> cards;
    private int cardsLeft;

    /* Constructor */
    public Deck(String[] ranks, String[] suits, int[] values) {
        this.cards = new ArrayList<Card>();
        this.cardsLeft = 0;
        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(ranks[i], suit, values[i]));
                cardsLeft++;
            }
        }
    }

    /* Getters */
    public int getCardsLeft() {
        return cardsLeft;
    }

    /* Methods */
    // Returns true if cardsLeft is 0
    public boolean isEmpty() {
        return cardsLeft == 0;
    }

    // Returns the card at index cardsLeft - 1 and decrements cardsLeft
    public Card deal() {
        return cardsLeft > 0 ? cards.get(cardsLeft-- - 1) : null;
    }

    // Shuffles the cards randomly using swaps
    public void shuffle() {
        // For i = last index of the deck down to 0
        for (int i = cardsLeft - 1; i > 0; i--) {
            // Generate a random int r between 0 and i inclusive
            int r = (int)(Math.random() * (i + 1));
            // Swap the card at index i with the card at index r
            Card temp = cards.remove(i);
            cards.add(i, cards.get(r));
            cards.remove(r);
            cards.add(r, temp);
        }
    }
}

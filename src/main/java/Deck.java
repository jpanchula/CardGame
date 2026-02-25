import java.awt.*;
import java.util.ArrayList;

public class Deck {
    /* Constants */
    private static final int TEXT_SIZE = 20;

    /* Instance variables */
    private ArrayList<Card> cards;
    private int cardsLeft;
    private GameView window;

    /* Constructor */
    public Deck(String[] ranks, String[] suits, int[] values, GameView window) {
        this.window = window;
        this.cards = new ArrayList<Card>();
        this.cardsLeft = 0;
        // Create and add Cards to the cards ArrayList
        for (int i = 0; i < ranks.length; i++) {
            for (String suit : suits) {
                cardsLeft++;
                cards.add(new Card(ranks[i], suit, values[i], cardsLeft, window));
            }
        }
        // Shuffle the deck after creating all cards
        shuffle();
    }

    /* Getters */
    // Returns the number of cards left
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
            Card temp = cards.get(i);
            cards.set(i, cards.get(r));
            cards.set(r, temp);
        }
    }

    // Draws the deck and the number of cards in it
    public void draw(Graphics g) {
        if (!isEmpty()) {
            // Set color and font
            g.setColor(Color.WHITE);
            g.setFont(new Font("Serif", Font.PLAIN, TEXT_SIZE));
            // Find the x offset for the text based on how many cards are left
            int offsetX = cardsLeft > 10 ? 10 : 5;
            // Draw the number of cards left
            g.drawString(Integer.toString(cardsLeft), GameView.WINDOW_CENTER - offsetX, GameView.WINDOW_CENTER - 60);
            // Draw the "deck" (a single card back image in the center)
            g.drawImage(Card.getCardBack(),
                    GameView.WINDOW_CENTER - Card.getCardWidth() / 2,
                    GameView.WINDOW_CENTER - Card.getCardHeight() / 2,
                    Card.getCardWidth(), Card.getCardHeight(),
                    window);
        }
    }
}

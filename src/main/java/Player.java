import java.util.ArrayList;

public class Player {
    /* Instance variables */
    private String name;
    private ArrayList<Card> hand;
    private int points;

    /* Constructors */
    // Creates a player with just a name
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<Card>();
        this.points = 0;
    }

    // Creates a player with a name and a hand
    public Player(String name, ArrayList<Card> hand) {
        this.name = name;
        this.hand = hand;
        this.points = 0;
    }

    /* Getters */
    // Returns the player's name
    public String getName() {
        return name;
    }

    // Returns the player's hand
    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getPoints() {
        return points;
    }

    /* Methods */
    // Adds points to the player's points
    public void addPoints(int points) {
        this.points += points;
    }

    // Adds a card to the player's hand
    public void addCard(Card card) {
        hand.add(card);
    }

    // Returns true if the player has cards in their hand
    public boolean hasCardsInHand() {
        return !hand.isEmpty();
    }

    // Gives all cards of a rank to another player and returns the number of cards that were given
    public int give(String rank, Player other) {
        int numCards = 0;
        int i = 0;
        while (i < hand.size()) {
            // If the card has the inputted rank
            if (hand.get(i).getRank().equalsIgnoreCase(rank)) {
                // Give the card to the other player
                other.addCard(hand.remove(i));
                numCards++;
            }
            else {
                // Increment i when a card is not removed
                i++;
            }
        }
        return numCards;
    }

    // Returns true if the player has a book, if so, removing it from their hand and adds 1 to points
    public void checkForBook(String rank) {
        // Count the number of cards they have
        int numCards = 0;
        for (Card card : hand) {
            if (card.getRank().equalsIgnoreCase(rank))
                numCards++;
        }
        // If the player has a book of 4 cards
        if (numCards == 4) {
            // Print that they have a book and add to their points
            System.out.println(name + " made a book of " + rank + "s!");
            points++;
            // For each card in the hand
            for (int i = 0; i < hand.size(); i++) {
                // If the card has the rank
                if (hand.get(i).getRank().equalsIgnoreCase(rank))
                    // Remove it from the hand then decrement i to avoid skipping the next index
                    hand.remove(i--);
            }
        }
    }

    // Method that returns true if the player names are equal
    public boolean equals(String otherName) {
        return name.equalsIgnoreCase(otherName);
    }

    /* toString */
    @Override
    public String toString() {
        return name + " has " + points + " point(s).\n" +
                name + "'s cards: " + hand;
    }
}

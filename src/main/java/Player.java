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
            // If the card has entered rank
            if (hand.get(i).getRank().equals(rank)) {
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

    // TODO: Method that checks if the player has a book, if so, removing it from their hand and adds 1 to points
    public boolean checkForBooks(String rank) {
        return false;
    }

    // Method that returns true if the player names are equal
    public boolean equals(String otherName) {
        return name.equals(otherName);
    }

    /* toString */
    @Override
    public String toString() {
        return name + " has " + points + " points\n" +
                name + "'s cards: " + hand;
    }
}

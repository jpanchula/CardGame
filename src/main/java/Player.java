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

    // TODO: Method that gives all cards of a rank to another player

    /* toString */
    @Override
    public String toString() {
        return name + " has " + points + " points\n" +
                name + "'s cards: " + hand;
    }
}

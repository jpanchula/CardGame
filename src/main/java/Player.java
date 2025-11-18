import java.util.ArrayList;

public class Player {
    /* Instance variables */
    private String name;
    private ArrayList<Card> hand;
    private int points;

    /* Constructors */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<Card>();
        this.points = 0;
    }

    public Player(String name, ArrayList<Card> hand) {
        this.name = name;
        this.hand = hand;
        this.points = 0;
    }

    /* Getters */
    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getPoints() {
        return points;
    }

    /* Methods */
    public void addPoints(int points) {
        this.points += points;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    /* toString */
    @Override
    public String toString() {
        return name + " has " + points + " points\n" +
                name + "'s cards: " + hand;
    }
}

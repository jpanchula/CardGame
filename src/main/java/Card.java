public class Card {
    /* Instance variables */
    private String rank;
    private String suit;
    private int value;

    /* Constructor */
    public Card(String rank, String suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
    }

    /* Getters and Setters */
    // Returns the rank of the Card
    public String getRank() {
        return rank;
    }

    // Sets the rank of the Card
    public void setRank(String rank) {
        this.rank = rank;
    }

    // Returns the suit of the Card
    public String getSuit() {
        return suit;
    }

    // Sets the suit of the Card
    public void setSuit(String suit) {
        this.suit = suit;
    }

    // Returns the value of the Card
    public int getValue() {
        return value;
    }

    // Sets the value of the Card
    public void setValue(int value) {
        this.value = value;
    }

    /* toString */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
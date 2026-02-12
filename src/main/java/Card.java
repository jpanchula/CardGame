import javax.swing.*;
import java.awt.*;

public class Card {
    /* Constant image */
    public static final Image cardBack = new ImageIcon("src/main/resources/Cards/back.png").getImage();

    /* Instance variables */
    private String rank;
    private String suit;
    private int value;
    private Image cardImage;
    private GameView window;

    /* Constructor */
    public Card(String rank, String suit, int value, int cardNum, GameView window) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
        this.cardImage = new ImageIcon("src/main/resources/Cards/" + cardNum + ".png").getImage();
        this.window = window;
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

    /* Draw self */
    public void draw(Graphics g, int x, int y, boolean isDisplayed) {
        g.drawImage((isDisplayed ? cardImage : cardBack),
                x, y,
                75, 105,
                window);
    }

    /* toString */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
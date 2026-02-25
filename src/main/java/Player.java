import java.awt.*;
import java.util.ArrayList;

public class Player {
    /* Constants */
    private static final int HAND_POS_X = 163;
    private static final int HAND_POS_Y = GameView.WINDOW_SIZE - 150;
    private static final int TEXT_SIZE = 20;

    private static final int BOTTOM = 0;
    private static final int LEFT = 1;
    private static final int TOP = 2;
    private static final int RIGHT = 3;

    /* Static variables */
    private static int numPlayers;
    public static int currentTurn;
    private static boolean isPlaying;

    /* Instance variables */
    private String name;
    private ArrayList<Card> hand;
    private int points;
    private int playerNum;

    /* Constructors */
    // Creates a player with just a name
    public Player(String name, int playerNum) {
        this.name = name;
        this.hand = new ArrayList<Card>();
        this.points = 0;
        this.playerNum = playerNum;
    }

    // Creates a player with a name and a hand
    public Player(String name, ArrayList<Card> hand) {
        this.name = name;
        this.hand = hand;
        this.points = 0;
    }

    /* Getters and Setters */
    // Returns the player's name
    public String getName() {
        return name;
    }

    // Returns the player's hand
    public ArrayList<Card> getHand() {
        return hand;
    }

    // Returns the player's points
    public int getPoints() {
        return points;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    // Sets the current turn
    public static void setCurrentTurn(int turn) {
        currentTurn = turn;
    }

    // Sets the number of players
    public static void setNumPlayers(int num) {
        numPlayers = num;
    }

    // Sets isPlaying
    public static void setIsPlaying(boolean playing) {
        isPlaying = playing;
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

    // Returns the number of cards with rank
    public int find(String rank) {
        int numCards = 0;
        for (Card c : hand) {
            if (c.getRank().equalsIgnoreCase(rank))
                numCards++;
        }
        return numCards;
    }

    // Gives all cards of a rank to another player
    public void give(String rank, Player other) {
        // For each card
        for (int i = 0; i < hand.size(); i++) {
            // If the card is of the given rank
            if (hand.get(i).getRank().equalsIgnoreCase(rank))
                // Give the card away and decrement i to avoid skipping the next card
                other.addCard(hand.remove(i--));
        }
    }

    // Returns true if the player has a book, if so, removing it from their hand and adds 1 to points
    public boolean checkForBook(String rank) {
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
            return true;
        }
        return false;
    }

    public void removeBook(String rank) {
        // Add a point to the player
        addPoints(1);
        // For each card in the hand
        for (int i = 0; i < hand.size(); i++) {
            // If the card has the rank
            if (hand.get(i).getRank().equalsIgnoreCase(rank))
                // Remove it from the hand then decrement i to avoid skipping the next index
                hand.remove(i--);
        }
    }

    // Returns true if the player names are equal
    public boolean equals(String otherName) {
        return name.equalsIgnoreCase(otherName);
    }

    /* Draws the current hand and player label */
    public void draw(Graphics g) {
        // Create a Graphics2D object for rotation (logic by Perplexity AI)
        Graphics2D g2d = (Graphics2D) g.create();
        // Rotate and draw name and points
        rotateAndDrawName(g2d);
        // Calculate the offset for each card
        int offset = 500 / hand.size();
        // Calculate the x value of the first card
        int x = (hand.size() > 1) ? HAND_POS_X : GameView.WINDOW_CENTER - Card.getCardWidth() / 2;
        // Calculate if the hand should be displayed
        boolean isDisplayed = (playerNum == currentTurn) && isPlaying;
        // Draw each card
        for (Card c : hand) {
            c.draw(g2d, x, HAND_POS_Y, isDisplayed);
            // Update position based on offset for next card
            x += offset;
        }
    }

    // Rotates the layout and draws name and points
    public void rotateAndDrawName(Graphics2D g2d) {
        // Calculate the layout based on playerNum
        // TODO: Fix layout calculation
        int layout = 0;
        if (playerNum == currentTurn)
            layout = BOTTOM;
        else if (playerNum == (currentTurn + 1) % numPlayers && playerNum > 2)
            layout = LEFT;
        else if (numPlayers == 2)
            layout = TOP;
        else
            layout = RIGHT;

        // Set color and font
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Serif", Font.PLAIN, TEXT_SIZE));
        // Apply the layout
        switch (layout) {
            case BOTTOM:
                // Draw name on the bottom, adjusting position for name length
                g2d.drawString(name, GameView.WINDOW_CENTER - (name.length() - 1) * (TEXT_SIZE / 2), GameView.WINDOW_SIZE - GameView.TEXT_X_OFFSET);
                break;
            // Left/top (next player)
            case LEFT:
                // Draw name on the top, adjusting position for name length
                g2d.drawString(name, GameView.WINDOW_CENTER - (name.length() - 1) * (TEXT_SIZE / 2), GameView.TEXT_X_OFFSET);
                // Rotate 180 degrees around the center
                g2d.rotate(Math.toRadians(180), GameView.WINDOW_CENTER, GameView.WINDOW_CENTER);
                break;
            // Top
            case TOP:
                // Draw name on the top, adjusting position for name length
                g2d.drawString(name, GameView.WINDOW_CENTER - (name.length() - 1) * (TEXT_SIZE / 2), GameView.TEXT_X_OFFSET);
                // Rotate 180 degrees around the center
                g2d.rotate(Math.toRadians(180), GameView.WINDOW_CENTER, GameView.WINDOW_CENTER);
                break;
            // Right
            case RIGHT:
                // Draw name on the right, adjusting position for name length
                g2d.drawString(name, GameView.WINDOW_SIZE - GameView.TEXT_X_OFFSET - (name.length() - 1) * (TEXT_SIZE / 2), GameView.WINDOW_CENTER);
                // Rotate -90 degrees around the center
                g2d.rotate(Math.toRadians(-90), GameView.WINDOW_CENTER, GameView.WINDOW_CENTER);
                break;
        }
    }

    /* toString */
    @Override
    public String toString() {
        return name + " has " + points + " point(s).\n" +
                name + "'s cards: " + hand;
    }
}

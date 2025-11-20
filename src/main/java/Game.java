// Go Fish by Jacob Panchula

public class Game {
    /* Constants */
    private static final String[] SUITS = {"Clubs", "Diamonds", "Hearts", "Spades"};
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "King", "Queen", "Ace"};
    private static final int[] VALUES = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    /* Instance variables */
    Player[] players;
    Deck deck;

    /* Constructor */
    public Game() {
        deck = new Deck(RANKS, SUITS, VALUES);
        // Initialize players, validating that the number of players is within the range
        players = new Player[Input.getIntInRange(MIN_PLAYERS, MAX_PLAYERS)];
        // For each player
        for (int i = 0; i < players.length; i++) {
            // Get each name, validating uniqueness
            players[i] = new Player(Input.getUniqueName(players, i));
        }
    }

    /* Methods */

    // Contains the logic to run the game
    public void play() {
        printInstructions();
        deal();
        // While the game is not over
        int i = 0;
        while (!gameOver()) {
            // Wrap the index if it went out of bounds
            i %= players.length;
            // TODO: Draw buffer between turns and print hand more neatly
            System.out.println(players[i]);
            // If the player does not have cards in their hand and the deck is empty
            if (!players[i].hasCardsInHand() && deck.isEmpty()) {
                // Skip turn
                i++;
            }
            // If the player does not have cards in their hand but there's cards in the deck
            if (!players[i].hasCardsInHand() && !deck.isEmpty()) {
                // Have the player draw a card
                System.out.println("Your hand is empty. Press enter to draw a card!");
                Input.waitForEnter();
                players[i].addCard(deck.deal());
                System.out.println("You got the " + players[i].getHand().getLast() + "!");
            }
            // If the player has cards in their hand
            if (players[i].hasCardsInHand()) {
                // Get the index of an opponent, validating that it is not self and
                Player opponent = Input.getOpponent(players, i);
                // TODO: Get a card rank from the player
                String rank = "";

                int numCards = opponent.give(rank, players[i]);
                if (numCards == 0) {
                    //
                    System.out.print("Press enter to tell " + players[i].getName() + " to go fish!");
                    Input.waitForEnter();
                    System.out.println(opponent.getName() + " tells " + players[i].getName() + " to go fish!");
                    i++;
                }
                else {
                    System.out.print("Press enter to give " + players[i].getName() + " your " + rank + "(s)!");
                    Input.waitForEnter();
                }
            }
        }
    }

    // Deals each player a hand of cards depending on the number of players
    public void deal() {
        // 7 cards each if less than 4 players, 5 cards each otherwise
        int num_cards = players.length < MAX_PLAYERS ? 7 : 5;
        for (int i = 0; i < num_cards; i++) {
            for (Player player : players) {
                player.addCard(deck.deal());
            }
        }
    }

    public boolean gameOver() {
        // Count the total number of books each player has
        int pointSum = 0;
        for (Player player : players) {
            pointSum += player.getPoints();
        }
        // If thirteen books have been won, the game is over
        return pointSum == 13;
    }

    /* Static Methods */

    // Returns true if name is not the name of another player
    public static boolean isUniquePlayerName(Player[] players, String name) {
        for (Player player : players) {
            if (player != null && player.equals(name))
                return false;
        }
        return true;
    }

    // Returns the player that has the name
    public static Player findPlayerWithName(Player[] players, String name) {
        for (Player player : players) {
            if (player.equals(name)) {
                return player;
            }
        }
        // If the player with that name is non-existent
        return null;
    }

    // Prints the instructions for the game
    public static void printInstructions() {
        // TODO: Write instructions
        System.out.println("This is Go Fish!");
    }

    // Main method
    public static void main(String[] args) {
        // Create a new Game object and begin the game
        Game game = new Game();
        game.play();
    }
}

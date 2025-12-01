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
        players = new Player[Input.getNumPlayers(MIN_PLAYERS, MAX_PLAYERS)];
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
        // Player index
        int i = 0;
        // While the game is not over
        while (!gameOver()) {
            // Print the player and their hand
            System.out.println(players[i]);
            // If the player does not have cards in their hand and the deck is empty
            if (!players[i].hasCardsInHand() && deck.isEmpty()) {
                // Skip turn, moving index to the next player
                i = (i + 1) % players.length;
            }
            // TODO: Implement the rest of the play logic into a function for abstraction
            // If the player does not have cards in their hand but there's cards in the deck
            else if (!players[i].hasCardsInHand() && !deck.isEmpty()) {
                // Have the player draw a card
                drawCard(players[i]);
            }
            // If the player has cards in their hand
            if (players[i].hasCardsInHand()) {
                // Get an opponent that the player is asking
                Player opponent;
                // If there are more than two players
                if (players.length > 2)
                    // Ask the player for who they're asking
                    opponent = Input.getOpponent(players, i);
                else
                    // Automatically set the opponent as the other player
                    opponent = players[(i + 1) % 2];

                // Get a rank that the player is asking for
                String rank = Input.getRank(players[i].getHand());

                // Draw buffer to pass to opponent and print their cards
                drawBuffer(opponent);
                System.out.println(opponent);
                // Print what the player is asking for
                System.out.println(players[i].getName() + " is asking you for " + rank + "s.");

                // Transfers all cards of rank from the opponent's hand to the player's hand
                int numCards = opponent.give(rank, players[i]);
                // If the opponent had none of the rank
                if (numCards == 0) {
                    // Allow opponent to tell the player to go fish
                    System.out.print("Press enter to tell " + players[i].getName() + " to go fish!");
                    Input.waitForEnter();
                    // Draw buffer to pass back to the player
                    drawBuffer(players[i]);
                    System.out.println(opponent.getName() + " tells " + players[i].getName() + " to go fish!");
                    drawCard(players[i]);
                    players[i].checkForBook(rank);
                    // If the player caught a card with the rank they were looking for
                    if (rank.equals(players[i].getHand().getLast().getRank())) {
                        // Allow player to continue their turn
                        System.out.println("You caught a(n) " + rank + "!");
                        System.out.println("Press enter to continue your turn.");
                        Input.waitForEnter();
                    }
                    else {
                        // Allow player to end their turn
                        System.out.println("Press enter to end your turn.");
                        Input.waitForEnter();
                        // Go to the next turn and draw buffer
                        // The expression within the player index here moves i to the next player
                        drawBuffer(players[++i % players.length]);
                    }
                }
                else {
                    // Allow opponent to give their cards to the player
                    System.out.print("Press enter to give " + players[i].getName() + " your " + rank + "(s)!");
                    Input.waitForEnter();
                    // Draw buffer to pass back to current player
                    drawBuffer(players[i]);
                    System.out.println("You got " + numCards + " " + rank + "(s) from " + opponent.getName() + "!");
                    // Check if they made a book
                    players[i].checkForBook(rank);
                    System.out.println("Press enter to continue your turn.");
                    Input.waitForEnter();
                }
            }
        }
    }

    // Draws a card
    public void drawCard(Player player) {
        System.out.println("Press enter to draw a card.");
        Input.waitForEnter();
        player.addCard(deck.deal());
        System.out.println("You drew the " + player.getHand().getLast() + "!");
    }

    // Deals each player a hand of cards depending on the number of players and checks for books
    public void deal() {
        // 7 cards each if less than 4 players, 5 cards each otherwise
        int num_cards = players.length < MAX_PLAYERS ? 7 : 5;
        for (int i = 0; i < num_cards; i++) {
            for (Player player : players) {
                player.addCard(deck.deal());
            }
        }
        // Checks for books
        for (String rank : RANKS) {
            for (Player player : players) {
                player.checkForBook(rank);
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

    // Draws a buffer to pass the turn to the given player
    public static void drawBuffer(Player player) {
        // Draw 100 new lines
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
        // Wait for the next player to press enter to continue
        System.out.println("Give the computer to " + player.getName() + " and press enter to continue.");
        Input.waitForEnter();
        // Print the player's points and cards
        System.out.println(player);
    }

    // Returns a properly capitalized rank
    public static String formatRank(String input) {
        for (String rank : RANKS) {
            if (input.equalsIgnoreCase(rank)) {
                return rank;
            }
        }
        // Throw an error if the rank is invalid
        // This function is only called after rank is verified so this shouldn't happen
        throw new RuntimeException();
    }

    // Returns true if name is not the name of another player
    public static boolean isUniquePlayerName(Player[] players, String name) {
        // For each player
        for (Player player : players) {
            // If the player is not null and has the same name
            if (player != null && player.equals(name))
                return false;
        }
        return true;
    }

    // Returns true if input is an invalid rank
    public static boolean isInvalidRank(String input) {
        // Check if the input is empty
        if (input.isEmpty()) {
            return true;
        }
        // For each rank
        for (String rank : RANKS) {
            // If the rank is equal to the input
            if (rank.equalsIgnoreCase(input))
                // Return true
                return false;
        }
        // Return false
        return true;
    }

    // Returns the player that has the name
    public static Player findPlayerWithName(Player[] players, String name) {
        for (Player player : players) {
            if (player.equals(name)) {
                return player;
            }
        }
        // Throw an error if the player with that name is non-existent
        // This function is only called after name is verified so this shouldn't happen
        throw new RuntimeException();
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

// Go Fish by Jacob Panchula

public class Game {
    /* Constants */
    private static final String[] SUITS = {"Clubs", "Diamonds", "Hearts", "Spades"};
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "King", "Queen", "Ace"};
    private static final int[] VALUES = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    public static final String[] INSTRUCTIONS = {
            "\nThis is Go Fish!",
            "The goal is to win the most \"books\" of cards. A book is any four of a kind.",
            "The player asks an opponent by name for one of the card ranks they have in their hand.",
            "If the opponent has the card, they must give all of them over and the player continues their turn.",
            "If the opponent does not have the card, they must tell the player to go fish.",
            "If you are told to go fish, you draw a card at random from the deck.",
            "If you get the card you were originally asking for, you made a \"catch\" so you can continue your turn.",
            "If you do not get the card you were originally asking for, your turn is over.",
            "The game ends when all 13 possible books are obtained.\n"
    };

    /* State constants */
    public static final int STATE_INIT = 0;
    public static final int STATE_INSTR = 1;
    public static final int STATE_PLAY = 2;
    public static final int STATE_END = 3;

    /* Instance variables */
    private Player[] players;
    private Deck deck;
    private int index;
    private int state;
    private GameView window;

    /* Constructor */
    public Game() {
        window = new GameView(this);
        deck = new Deck(RANKS, SUITS, VALUES, window);
        index = 0;
        state = STATE_INIT;
        // Initialize players, validating that the number of players is within the accepted range
        players = new Player[Input.getNumPlayers(MIN_PLAYERS, MAX_PLAYERS)];
        Player.setNumPlayers(players.length);
        // For each player
        for (int i = 0; i < players.length; i++) {
            // Get each name, validating uniqueness
            players[i] = new Player(Input.getUniqueName(players, i), i);
        }
    }

    /* Methods */

    // Contains the logic to run the game
    public void play() {
        printInstructions();
        deal();
        // While the game is not over
        while (!gameOver()) {
            // Print the player's points and hand
            System.out.println(players[index]);
            // If the player does not have cards in their hand and the deck is empty
            if (!players[index].hasCardsInHand() && deck.isEmpty()) {
                // Skip turn, moving index to the next player
                index = (index + 1) % players.length;
                continue;
            }
            // If the player does not have cards in their hand but there's cards in the deck
            else if (!players[index].hasCardsInHand()) {
                // Have the player draw a card
                drawCard(players[index]);
            }

            // Play the turn after verifying that the player now has cards in their hand
            playTurn();
        }

        printScores();
    }

    public void playTurn() {
        state = STATE_PLAY;
        window.repaint();
        Player.setCurrentTurn(index);
        // Get an opponent that the player is asking
        Player opponent;
        // If there are more than two players
        if (players.length > 2)
            // Ask the player for who they're asking
            opponent = Input.getOpponent(players, index);
        else
            // Automatically set the opponent as the other player
            opponent = players[(index + 1) % 2];

        // Get a rank that the player is asking for
        String rank = Input.getRank(players[index].getHand());

        // Draw buffer to pass to opponent and print their cards
        drawBuffer(opponent);
        System.out.println(opponent);
        // Print what the player is asking for
        System.out.println(players[index].getName() + " is asking you for " + rank + "s.");

        // Transfers all cards of rank from the opponent's hand to the player's hand
        int numCards = opponent.give(rank, players[index]);
        // If the opponent had none of the rank
        if (numCards == 0) {
            // Allow opponent to tell the player to go fish
            System.out.print("Press enter to tell " + players[index].getName() + " to go fish!");
            Input.waitForEnter();
            // Draw buffer to pass back to the current player
            drawBuffer(players[index]);
            System.out.println(opponent.getName() + " tells " + players[index].getName() + " to go fish!");
            // Draw card and store its rank
            drawCard(players[index]);
            String cardRank = players[index].getHand().getLast().getRank();
            // Check for a book
            players[index].checkForBook(rank);
            // If the player caught a card with the rank they were looking for
            if (rank.equals(cardRank)) {
                // Allow player to continue their turn
                System.out.println("You caught a(n) " + rank + "!");
                System.out.println("Press enter to continue your turn.");
                Input.waitForEnter();
            }
            else {
                // Allow player to end their turn
                System.out.println("Press enter to end your turn.");
                Input.waitForEnter();
                // Moves index to the next player
                index = (index + 1) % players.length;
                // Draw buffer and move on to the next turn
                drawBuffer(players[index]);
            }
        }
        else {
            // Allow opponent to give their cards to the player
            System.out.print("Press enter to give " + players[index].getName() + " your " + rank + "(s)!");
            Input.waitForEnter();
            // Draw buffer to pass back to current player
            drawBuffer(players[index]);
            System.out.println("You got " + numCards + " " + rank + "(s) from " + opponent.getName() + "!");
            // Check if they made a book
            players[index].checkForBook(rank);
            // Allow player to continue their turn
            System.out.println("Press enter to continue your turn.");
            Input.waitForEnter();
        }
    }

    // Allows the user to draw a card
    public void drawCard(Player player) {
        System.out.println("Press enter to draw a card.");
        Input.waitForEnter();
        player.addCard(deck.deal());
        System.out.println("You drew the " + player.getHand().getLast() + "!");
        window.repaint();
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

    public void printScores() {
        state = STATE_END;
        window.repaint();
        System.out.println("\nFinal scores:");
        for (Player player : players) {
            System.out.println(player.getName() + " has " + player.getPoints() + " point(s).");
        }
        System.out.println("\nThank you for playing!");
    }

    // Returns the state
    public int getState() {
        return state;
    }

    // Returns the players
    public Player[] getPlayers() {
        return players;
    }

    // Returns the deck
    public Deck getDeck() {
        return deck;
    }

    // Prints the instructions for Go Fish
    public void printInstructions() {
        state = STATE_INSTR;
        window.repaint();
        // Print the instructions
        for (String line : INSTRUCTIONS) {
            System.out.println(line);
        }

        // Wait until the user moves on
        System.out.println("Press enter to begin!");
        Input.waitForEnter();
    }

    /* Static Methods */

    // Draws a buffer to pass the turn to the given player
    public void drawBuffer(Player player) {
        // Set the turn to -1 because it is nobody's turn
        Player.setCurrentTurn(-1);
        window.repaint();
        // Draw 100 new lines
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
        // Wait for the next player to press enter to continue
        System.out.println("Give the computer to " + player.getName() + " and press enter to continue.");
        Input.waitForEnter();
        // Set the turn to the current player's playerNum
        Player.setCurrentTurn(player.getPlayerNum());
        window.repaint();
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

    // Main method
    public static void main(String[] args) {
        // Create a new Game object and begin the game
        Game game = new Game();
        game.play();
    }
}

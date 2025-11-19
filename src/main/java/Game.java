// Go Fish by Jacob Panchula

public class Game {
    /* Constants */
    private static final String[] SUITS = {"Clubs", "Diamonds", "Hearts", "Spades"};
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "King", "Queen", "Ace"};
    private static final int[] VALUES = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    /* Instance variables */
    Player[] players;
    Deck deck;

    /* Constructor */
    public Game(int numPlayers) {
        deck = new Deck(RANKS, SUITS, VALUES);
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter Player " + (i + 1) + "'s name: ");
            players[i] = new Player(Input.getString());
        }
    }

    /* Methods */
    // Initializes the game by getting the number of players before playing
    public static void initGame() {
        int numPlayers = 0;
        while (numPlayers < 2 || numPlayers > 4) {
            System.out.print("Enter the number of players (2-4): ");
            numPlayers = Input.getInt();
        }
        Game game = new Game(numPlayers);
        game.play();
    }

    // Contains the logic to run the game
    public void play() {
        printInstructions();
        deal();
    }

    // Prints the instructions for the game
    public static void printInstructions() {
        System.out.println("This is Go Fish!");
    }

    // Deals each player a hand of cards depending on the number of players
    public void deal() {
        int num_cards = players.length < 4 ? 7 : 5;
        for (int i = 0; i < num_cards; i++) {
            for (Player player : players) {
                player.addCard(deck.deal());
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        Game.initGame();
    }
}

import java.util.Scanner;
/* Class with helper functions to get user input */
public class Input {
    // Returns an integer from the user, -1 if invalid
    public static int getInt() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number!");
            return -1;
        }
    }


    public static int getIntInRange(int min, int max) {
        int numPlayers = 0;
        while (numPlayers < min || numPlayers > max) {
            System.out.print("Enter the number of players (2-4): ");
            numPlayers = Input.getInt();
            if (numPlayers < min || numPlayers > max) {
                System.out.println("Must be in range " + min + " to " + max + "!");
            }
        }
        return numPlayers;
    }

    // Returns a non-empty name from the user
    public static String getUniqueName(Player[] players, int i) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (input.isEmpty() || !Game.isUniquePlayerName(players, input)) {
            System.out.print("Enter Player " + (i + 1) + "'s name: ");
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Name cannot be empty!");
            }
            else if (!Game.isUniquePlayerName(players, input)) {
                System.out.println("Name must be unique!");
            }
        }
        return input;
    }

    // Returns the index of an opponent that has cards and is not self
    public static Player getOpponent(Player[] players, int selfIndex) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        // While the input is the name of a non-existent player or the input is self's name
        // or the input is the name of a player without cards in their hand
        while (players[selfIndex] == Game.findPlayerWithName(players, input)
                || (Game.findPlayerWithName(players, input) == null
                || !Game.findPlayerWithName(players, input).hasCardsInHand())) {
            // Get the user's input
            System.out.print("Enter the name of the person you would like to ask: ");
            input = scanner.nextLine();
            // Error messages
            if (Game.isUniquePlayerName(players, input))
                System.out.println("Please enter a valid name!");
            else if (players[selfIndex] == Game.findPlayerWithName(players, input))
                System.out.println("Please enter a name other than your own!");
            else if ((Game.findPlayerWithName(players, input) != null &&
                    !Game.findPlayerWithName(players, input).hasCardsInHand()))
                System.out.println("Please enter a name that has cards in their hand!");
        }
        Player player = Game.findPlayerWithName(players, input);
        if (player != null)
            return player;
        System.out.println("STOP");
        return new Player("");

    }

    // TODO: Method that returns a valid rank from the user

    // Waits for the user to press enter
    public static void waitForEnter() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}

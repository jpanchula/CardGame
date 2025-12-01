import java.util.Scanner;
import java.util.ArrayList;
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


    public static int getNumPlayers(int min, int max) {
        int numPlayers = 0;
        while (numPlayers < min || numPlayers > max) {
            System.out.print("Enter the number of players (" + min + "-" + max + "): ");
            numPlayers = Input.getInt();
            if (numPlayers < min || numPlayers > max) {
                System.out.println("The number must be in range " + min + " to " + max + " inclusive!");
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
        while (Game.isUniquePlayerName(players, input)
                || players[selfIndex] == Game.findPlayerWithName(players, input)
                || !Game.findPlayerWithName(players, input).hasCardsInHand()) {
            // Get the user's input
            System.out.print("Enter the name of the person you would like to ask: ");
            input = scanner.nextLine();
            // Error messages
            if (Game.isUniquePlayerName(players, input))
                System.out.println("Name must be the name of another player!");
            else if (players[selfIndex] == Game.findPlayerWithName(players, input))
                System.out.println("Name cannot be your own!");
            // If the player does not have cards in their hand
            else if (!Game.findPlayerWithName(players, input).hasCardsInHand())
                System.out.println("Name must be a player that has cards in their hand!");
        }
        return Game.findPlayerWithName(players, input);
    }

    // Returns a valid rank in the given hand from the user
    public static String getRank(ArrayList<Card> hand) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        boolean hasCard = false;
        // While input is an invalid rank
        while (Game.isInvalidRank(input) || !hasCard) {
            System.out.print("Enter the rank of the card that you would like to ask for: ");
            input = scanner.nextLine();
            // For each card in the hand
            for (Card card : hand) {
                // If the card has the inputted rank
                if (card.getRank().equalsIgnoreCase(input)) {
                    hasCard = true;
                    // Break the loop and return input
                    break;
                }
            }
            // Print error message if the rank is invalid
            if (Game.isInvalidRank(input))
                System.out.println("Please enter a valid rank!");
            else if (!hasCard)
                System.out.println("Please enter the rank of a card in your hand!");
        }
        // Return a correctly formatted rank
        return Game.formatRank(input);
    }

    // Waits for the user to press enter
    public static void waitForEnter() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}

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

    // Returns a string from the user
    public static String getString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}

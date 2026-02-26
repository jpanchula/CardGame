import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    /* Constants */
    public static final int WINDOW_SIZE = 800;
    public static final int WINDOW_CENTER = 400;
    public static final Color BACKGROUND_COLOR = new Color(25, 150, 50);

    private static final int TEXT_X_POS = 20;
    private static final int TEXT_Y_OFFSET = 30;
    private static final int INIT_TEXT_SIZE = 35;
    private static final int INSTR_TEXT_SIZE = 18;
    private static final int BUFFER_TEXT_SIZE = 20;
    private static final int END_TEXT_SIZE = 50;

    /* Instance Variables */
    private Game backend;

    /* Constructor */
    public GameView(Game backend) {
        this.backend = backend;

        this.setTitle("Go Fish!");
        this.setSize(WINDOW_SIZE, WINDOW_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        switch (backend.getState()) {
            case Game.STATE_INIT:
                // Draw the init message
                drawInit(g);
                break;
            case Game.STATE_INSTR:
                // Display the instructions
                drawInstructions(g);
                break;
            case Game.STATE_PLAY:
                // Draw the game while being played
                drawPlay(g);
                break;
            case Game.STATE_BUFFER:
                // Draw the game then draw the buffer message
                drawPlay(g);
                drawBufferMessage(g);
                break;
            case Game.STATE_END:
                drawEnd(g);
                break;
        }
    }

    public void drawInit(Graphics g) {
        // Draw the background
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        // Set the font and color
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, INIT_TEXT_SIZE));
        // Draw a message to the user
        g.drawString("Follow instructions in the console to begin!", TEXT_X_POS * 3, WINDOW_CENTER);
    }

    public void drawInstructions(Graphics g) {
        // Draw the background
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        // Set the font and color
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, INSTR_TEXT_SIZE));
        // Calculate initial position
        int y = GameView.WINDOW_SIZE / 2 - TEXT_Y_OFFSET * Game.INSTRUCTIONS.length / 2;
        // Draw instructions
        for (String s : Game.INSTRUCTIONS) {
            g.drawString(s, TEXT_X_POS, y);
            y += TEXT_Y_OFFSET;
        }
    }

    public void drawPlay(Graphics g) {
        // Draw the background
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        // Draw each player
        for (Player p : backend.getPlayers()) {
            p.draw(g);
        }
        // Draw the deck
        backend.getDeck().draw(g);
    }

    // Draws the buffer message over the play screen
    public void drawBufferMessage(Graphics g) {
        // Set the font and color
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, BUFFER_TEXT_SIZE));
        // Draw the buffer message
        g.drawString("Pass the computer to " + backend.getPlayers()[Player.currentTurn].getName(),
                    WINDOW_CENTER / 2, WINDOW_CENTER / 2 + TEXT_Y_OFFSET * 3);
    }

    // Draws the leaderboard screen at the end
    public void drawEnd(Graphics g) {
        // Draw the background
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        // Set the font and color
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, END_TEXT_SIZE));
        // Draw the header
        g.drawString("Final Scores: ", TEXT_X_POS * 5, TEXT_Y_OFFSET * 3);
        // Calculate initial position
        int y = GameView.WINDOW_SIZE / 2 - TEXT_Y_OFFSET * 3 * backend.getPlayers().length / 2;
        // Draw point standings
        for (Player p : backend.getPlayers()) {
            g.drawString(p.getName() + ": " + p.getPoints() + " points", TEXT_X_POS * 5, y);
            y += TEXT_Y_OFFSET * 4;
        }
    }
}

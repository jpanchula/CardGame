import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    /* Constants */
    public static final int WINDOW_SIZE = 800;
    public static final int WINDOW_CENTER = 400;
    public static final Color BACKGROUND_COLOR = new Color(25, 150, 50);
    private static final int TEXT_Y_OFFSET = 30;
    public static final int TEXT_X_OFFSET = 175;

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
                // TODO: Finish init
                g.setColor(BACKGROUND_COLOR);
                g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
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
                // TODO: Finish end screen
                g.setColor(BACKGROUND_COLOR);
                g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
                break;
        }
    }

    public void drawInstructions(Graphics g) {
        // Draw the background
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        // Set the font and color
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, 18));
        // Calculate initial position
        int x = 20;
        int y = GameView.WINDOW_SIZE / 2 - TEXT_Y_OFFSET * Game.INSTRUCTIONS.length / 2;
        // Draw instructions
        for (String s : Game.INSTRUCTIONS) {
            g.drawString(s, x, y);
            y += TEXT_Y_OFFSET;
        }
    }

    // TODO: Finish drawing game on play
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
        g.setFont(new Font("Serif", Font.PLAIN, 20));
        g.drawString("Pass the computer to " + backend.getPlayers()[Player.currentTurn].getName(),
                    WINDOW_CENTER / 2, WINDOW_CENTER / 2);
    }
}

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    /* Constants */
    public static final int WINDOW_SIZE = 800;

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
            // TODO: Finish init
            case Game.STATE_INIT:
                g.setColor(Color.BLACK);
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
        }
    }

    // TODO: Fix displaying instructions
    public void drawInstructions(Graphics g) {
        // Draw a black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        // Set the font and initial x and y
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, 15));
        int x = 20;
        int y = 50;
        // Draw instructions
        for (String s : Game.INSTRUCTIONS) {
            g.drawString(s, x, y);
            y += 30;
        }
    }

    // TODO: Finish drawing game on play
    public void drawPlay(Graphics g) {
        System.out.println("drawing play!");
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        for (Player p : backend.getPlayers()) {
            p.draw(g);
        }
    }
}

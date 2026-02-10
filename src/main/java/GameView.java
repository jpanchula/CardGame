import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    /* Constants */
    public static final int WINDOW_SIZE = 500;

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
        // For now just have a white screen on init
        if (backend.getState() == Game.STATE_INIT) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        }
        else {
            // Display the instructions
            drawInstructions(g);
        }
    }

    // TODO: Draw instructions
    public void drawInstructions(Graphics g) {
        g.setColor(Color.BLACK);
    }
}

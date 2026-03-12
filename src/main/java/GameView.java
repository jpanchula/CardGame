import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton restartButton;

    /* Constructor */
    public GameView(Game backend) {
        this.backend = backend;

        this.setTitle("Go Fish!");
        this.setSize(WINDOW_SIZE, WINDOW_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1. Create a custom JPanel to handle all the drawing securely
        JPanel drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Required to properly clear the panel

                // 2. The drawing logic moves here, replacing the old paint() override!
                switch (backend.getState()) {
                    case Game.STATE_INIT:
                        drawInit(g);
                        break;
                    case Game.STATE_INSTR:
                        drawInstructions(g);
                        break;
                    case Game.STATE_PLAY:
                        drawPlay(g);
                        break;
                    case Game.STATE_BUFFER:
                        drawPlay(g);
                        drawBufferMessage(g);
                        break;
                    case Game.STATE_END:
                        drawEnd(g);
                        break;
                }
            }
        };
        drawPanel.setLayout(null); // Allows absolute positioning of the button

        // 3. Set up the button and add it to the PANEL (not the frame)
        restartButton = new JButton("Restart Game");
        restartButton.setBounds(WINDOW_CENTER - 100, WINDOW_SIZE - 150, 200, 50);
        restartButton.setFont(new Font("Serif", Font.BOLD, 20));
        restartButton.setVisible(false); // Hidden until the game ends
        restartButton.addActionListener(e -> backend.restart());
        drawPanel.add(restartButton);

        // 4. Add the drawing panel to the window
        this.add(drawPanel);

        this.setVisible(true);
    }

    public void setRestartButtonVisible(boolean visible) {
        restartButton.setVisible(visible);
        repaint();
    }

    // NOTE: The @Override public void paint(Graphics g) method has been REMOVED entirely!

    public void drawInit(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, INIT_TEXT_SIZE));
        g.drawString("Follow instructions in the console to begin!", TEXT_X_POS * 3, WINDOW_CENTER);
    }

    public void drawInstructions(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, INSTR_TEXT_SIZE));
        int y = GameView.WINDOW_SIZE / 2 - TEXT_Y_OFFSET * Game.INSTRUCTIONS.length / 2;
        for (String s : Game.INSTRUCTIONS) {
            g.drawString(s, TEXT_X_POS, y);
            y += TEXT_Y_OFFSET;
        }
    }

    public void drawPlay(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        for (Player p : backend.getPlayers()) {
            p.draw(g);
        }
        backend.getDeck().draw(g);
    }

    public void drawBufferMessage(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, BUFFER_TEXT_SIZE));
        g.drawString("Pass the computer to " + backend.getPlayers()[Player.currentTurn].getName(),
                WINDOW_CENTER / 2, WINDOW_CENTER / 2 + TEXT_Y_OFFSET * 3);
    }

    public void drawEnd(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, END_TEXT_SIZE));
        g.drawString("Final Scores: ", TEXT_X_POS * 5, TEXT_Y_OFFSET * 3);
        int y = GameView.WINDOW_SIZE / 2 - TEXT_Y_OFFSET * 3 * backend.getPlayers().length / 2;
        for (Player p : backend.getPlayers()) {
            g.drawString(p.getName() + ": " + p.getPoints() + " points", TEXT_X_POS * 5, y);
            y += TEXT_Y_OFFSET * 4;
        }
    }
}
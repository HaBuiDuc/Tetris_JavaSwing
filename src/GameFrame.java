import javax.swing.*;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame{
    public GameFrame() {
        this.setSize(GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);
        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);
        this.addKeyListener(gamePanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

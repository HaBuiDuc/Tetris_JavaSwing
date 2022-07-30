import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener {
    public static final int PANEL_WIDTH = 300;
    public static final int PANEL_HEIGHT = 598;
    public static final int BLOCK_SIZE = 30;
    private int[][] blocks = new int[PANEL_WIDTH/BLOCK_SIZE][PANEL_HEIGHT/BLOCK_SIZE];
    private Shape [] mShapes;
    private Shape mCurrentShape;
    private Timer mTimer;
    public GamePanel() {
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        this.addKeyListener(this);
        this.setVisible(true);
        mTimer = new Timer(1000/60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mCurrentShape.update();
                repaint();
            }
        });
        mTimer.start();
        mShapes = new Shape[7];
        mShapes[0] = new Shape(new int[][]{{1,1,0},{0,1,1}}, 0);
        mShapes[1] = new Shape(new int[][] {{0,1,1},{1,1,0}}, 1);
        mShapes[2] = new Shape(new int[][] {{1,1,1},{0,1,0}},2);
        mShapes[3] = new Shape(new int[][]{{1,1,1},{1,0,0}},3);
        mShapes[4] = new Shape(new int[][]{{1,1,1},{0,0,1}}, 4);
        mShapes[5] = new Shape(new int[][]{{1,1,1,1}},5);
        mShapes[6] = new Shape(new int[][]{{1,1},{1,1}}, 6);
        mCurrentShape = mShapes[0];
        setNextShape();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawBlocks(g);
        mCurrentShape.drawShape(g);
    }
    public void drawBlocks(Graphics g) {
        for (int i=0;i<PANEL_WIDTH/BLOCK_SIZE;i++) {
            g.drawLine(i*BLOCK_SIZE, 0, i*BLOCK_SIZE, PANEL_HEIGHT);
        }
        for (int i=0;i<PANEL_HEIGHT/BLOCK_SIZE;i++) {
            g.drawLine(0, i*BLOCK_SIZE, PANEL_WIDTH,i*BLOCK_SIZE);
        }
    }
    private void setNextShape() {
        Random random = new Random();
        int index = random.nextInt(6);
        mCurrentShape = mShapes[index];
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                mCurrentShape.setPositionX(mCurrentShape.getPositionX() - 1);
                System.out.println("Nut duoc bam");
            }
            case KeyEvent.VK_RIGHT -> {
                mCurrentShape.setPositionX(mCurrentShape.getPositionX() + 1);
            }
            default -> {
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

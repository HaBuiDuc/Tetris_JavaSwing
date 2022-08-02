import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener {
    public static final int PANEL_WIDTH = 300;
    public static final int PANEL_HEIGHT = 598;
    public static final int BLOCK_SIZE = 30;
    private int[][] blocks = new int[PANEL_HEIGHT/BLOCK_SIZE][PANEL_WIDTH/BLOCK_SIZE];
    private Shape [] mShapes;
    private Shape mCurrentShape;

    public int[][] getBlocks() {
        return blocks;
    }

    private Timer mTimer;
    public GamePanel() {
        for (int[] block : blocks) {
            Arrays.fill(block, 0);
        }
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        this.addKeyListener(this);
        this.setVisible(true);
        mTimer = new Timer(1000/60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(gameOver());
                if (!gameOver()) {
                    mCurrentShape.update();
                    if (mCurrentShape.checkCollision()) {
//                        for (int row = 0;row < blocks.length;row++) {
//                            for (int column = 0;column < blocks[row].length;column++) {
//                                System.out.print(blocks[row][column]);
//                            }
//                            System.out.println();
//                        }
//                        System.out.println("*******************");
                        setNextShape();
                    }
                    deleteRow();
                } else {
                    mTimer.stop();
                }
                repaint();
            }
        });
        mTimer.start();
        mShapes = new Shape[7];
        mShapes[0] = new Shape(new int[][] {{1,1,0},{0,1,1}}, 1, this);
        mShapes[1] = new Shape(new int[][] {{0,1,1},{1,1,0}}, 2, this);
        mShapes[2] = new Shape(new int[][] {{1,1,1},{0,1,0}},3, this);
        mShapes[3] = new Shape(new int[][] {{1,1,1},{1,0,0}},4, this);
        mShapes[4] = new Shape(new int[][] {{1,1,1},{0,0,1}}, 5, this);
        mShapes[5] = new Shape(new int[][] {{1,1,1,1}},6, this);
        mShapes[6] = new Shape(new int[][] {{1,1},{1,1}}, 7, this);
        setNextShape();
        System.out.println(blocks.length);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        boolean check = gameOver();
        if (!gameOver()) {
            drawBlocks(g);
            mCurrentShape.drawShape(g);
        } else {
            drawOver(g);
        }
    }
    public void drawBlocks(Graphics g) {
        for (int i=0;i<PANEL_WIDTH/BLOCK_SIZE;i++) {
            g.drawLine(i*BLOCK_SIZE, 0, i*BLOCK_SIZE, PANEL_HEIGHT);
        }
        for (int i=0;i<PANEL_HEIGHT/BLOCK_SIZE;i++) {
            g.drawLine(0, i*BLOCK_SIZE, PANEL_WIDTH,i*BLOCK_SIZE);
        }

        for (int row = 0;row < blocks.length;row++) {
            for (int column = 0;column < blocks[row].length;column++) {
                if (blocks[row][column] != 0) {
                    switch (blocks[row][column]) {
                        case 1 -> g.setColor(Color.RED);
                        case 2 -> g.setColor(new Color(255,128,0));
                        case 3 -> g.setColor(new Color(204,204,0));
                        case 4 -> g.setColor(Color.green);
                        case 5 -> g.setColor(Color.blue);
                        case 6 -> g.setColor(new Color(102,0,204));
                        case 7 -> g.setColor(new Color(255,51,153));
                    }
                    g.fillRect(column * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }
    private void drawOver(Graphics g) {
        String over = "Game Over";
        g.drawString(over, PANEL_WIDTH/2-over.length()*4, PANEL_HEIGHT/2);
    }
    private void setNextShape() {
        Random random = new Random();
        int index = random.nextInt(7);
        mCurrentShape = new Shape(mShapes[index]);
    }
    private void setRow(int[][] board, int rowNumber) {
        for (int row = rowNumber;row > 0;row--) {
            board[row] = board[row-1];
        }
    }
    private void deleteRow() {
        for (int row = 0;row < blocks.length;row++) {
            boolean flag = true;
            for (int column = 0;column < blocks[row].length;column++) {
                if (blocks[row][column] == 0) {
                    flag = false;
                }
            }
            if (flag) {
                setRow(blocks, row);
//                for (int column = 0;column < blocks[row].length;column++) {
//                    System.out.print(blocks[row][column]);
//                }
            }
        }
    }
    private boolean gameOver() {
        for (int i = 0;i < blocks[0].length;i++) {
            if (blocks[1][i] != 0) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                if (mCurrentShape.checkMove() != 1) {
                    mCurrentShape.setPositionX(mCurrentShape.getPositionX() - 1);
                }
//                System.out.println("Nut duoc bam");
            }
            case KeyEvent.VK_RIGHT -> {
                if (mCurrentShape.checkMove() != 2) {
                    mCurrentShape.setPositionX(mCurrentShape.getPositionX() + 1);
                }
            }
            case KeyEvent.VK_SPACE -> {
                mCurrentShape.rotate();
            }
            case KeyEvent.VK_DOWN -> {
                mCurrentShape.speedUp();
//                System.out.println("Nut duoc bam");
            }
            default -> {
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            mCurrentShape.normalSpeed();
        }
    }
}

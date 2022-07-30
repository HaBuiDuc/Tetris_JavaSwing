import java.awt.*;

public class Shape {
    private int [][] typeShape;
    private int color;
    private int positionX = 4;
    private int positionY = 0;
    private int currentSpeed = 600;
    private int normalSpeed;
    private long lastTime, time;

    public Shape(int [][] newTypeShape, int newColor) {
        lastTime = System.currentTimeMillis();
        time = 0;
        this.typeShape = newTypeShape;
        this.color = newColor;
    }
    public void drawShape(Graphics g) {
        switch (color) {
            case 0 -> {
                g.setColor(Color.RED);
            }
            case 1 -> {
                g.setColor(new Color(255,128,0));
            }
            case 2 -> {
                g.setColor(Color.yellow);
            }
            case 3 -> {
                g.setColor(Color.green);
            }
            case 4 -> {
                g.setColor(Color.blue);
            }
            case 5 -> {
                g.setColor(new Color(102,0,204));
            }
            case 6 -> {
                g.setColor(new Color(255,51,153));
            }
        }
        for (int row = 0; row < typeShape.length; row++) {
            for (int column = 0; column < typeShape[row].length; column++) {
                if (typeShape[row][column] != 0) {
                    int x = row * GamePanel.BLOCK_SIZE + positionX * GamePanel.BLOCK_SIZE;
                    int y = column * GamePanel.BLOCK_SIZE + positionY * GamePanel.BLOCK_SIZE;
                    g.fillRect(x, y, GamePanel.BLOCK_SIZE, GamePanel.BLOCK_SIZE);
                }
            }
        }
    }
    public void update() {
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (time > 600) {
            positionY++;
            time = 0;
        }
    }
    public int[][] getTypeShape() {
        return typeShape;
    }

    public void setTypeShape(int[][] typeShape) {
        this.typeShape = typeShape;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}

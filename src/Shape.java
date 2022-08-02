import java.awt.*;

public class Shape {
    private int [][] typeShape;
    private int color;
    private int positionX = 4;
    private int positionY = 0;
    private int currentSpeed;
    private int normalSpeed = 600;
    private int downSpeed = 50;
    private long lastTime, time;
    private GamePanel gameBoard;

    public Shape(int [][] newTypeShape, int newColor, GamePanel newGameBoard) {
        lastTime = System.currentTimeMillis();
        time = 0;
        this.typeShape = newTypeShape;
        this.color = newColor;
        this.gameBoard = newGameBoard;
        this.currentSpeed = this.normalSpeed;
    }


    public Shape(Shape shape) {
        this.typeShape = shape.typeShape;
        this.color = shape.color;
        this.gameBoard = shape.gameBoard;
        this.positionX = shape.positionX;
        this.positionY = shape.positionY;
        this.currentSpeed = this.normalSpeed;
    }

    public void drawShape(Graphics g) {
        switch (color) {
            case 1 -> g.setColor(Color.RED);
            case 2 -> g.setColor(new Color(255,128,0));
            case 3 -> g.setColor(new Color(204,204,0));
            case 4 -> g.setColor(Color.green);
            case 5 -> g.setColor(Color.blue);
            case 6 -> g.setColor(new Color(102,0,204));
            case 7 -> g.setColor(new Color(255,51,153));
        }
        for (int row = 0; row < typeShape.length; row++) {
            for (int column = 0; column < typeShape[row].length; column++) {
                if (typeShape[row][column] != 0) {
                    int x = column * GamePanel.BLOCK_SIZE + positionX * GamePanel.BLOCK_SIZE;
                    int y = row * GamePanel.BLOCK_SIZE + positionY * GamePanel.BLOCK_SIZE;
                    g.fillRect(x, y, GamePanel.BLOCK_SIZE, GamePanel.BLOCK_SIZE);
                }
            }
        }
    }

    public int getNormalSpeed() {
        return normalSpeed;
    }

    public int getDownSpeed() {
        return downSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public void update() {
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (time > currentSpeed) {
//            System.out.println(currentSpeed);
            positionY++;
            time = 0;
        }
    }
    public void rotate() {
        int[][] newTypeShape = transpose(this.typeShape);
        for (int row = 0;row < newTypeShape.length;row++) {
            int tmpColumn = newTypeShape[row].length - 1;
            for (int column = 0;column < newTypeShape[row].length/2;column++) {
                int tmp;
                tmp = newTypeShape[row][column];
                newTypeShape[row][column] = newTypeShape[row][tmpColumn];
                newTypeShape[row][tmpColumn] = tmp;
                tmpColumn--;
            }
        }
        this.typeShape = newTypeShape;
        for (int[] ints : typeShape) {
            for (int anInt : ints) {
//                System.out.print(anInt);
            }
//            System.out.println();
        }
    }
    private int[][] transpose(int[][] matrix) {
        int[][] newMatrix = new int[matrix[0].length][matrix.length];
        for (int row = 0;row < matrix.length;row++) {
            for (int column = 0;column < matrix[row].length;column++) {
                newMatrix[column][row] = matrix[row][column];
            }
        }
        return newMatrix;
    }
    public boolean checkCollision() {
        int[][] board = gameBoard.getBlocks();
//        for (int row = 0;row < typeShape.length;row++) {
//            System.out.println(positionY+row);
//            for (int column = 0;column < typeShape[row].length;column++) {
//                if (board[positionY+row][positionX+column] != 0 || positionY + typeShape.length == GamePanel.PANEL_HEIGHT/GamePanel.BLOCK_SIZE) {
//                    System.out.println("Da va cham");
//
//                    return true;
//                }
//            }
//        }
        if (positionY + typeShape.length == GamePanel.PANEL_HEIGHT/GamePanel.BLOCK_SIZE) {
            setBoard(gameBoard.getBlocks());
            for (int i=0;i<gameBoard.getBlocks().length;i++) {
                for (int j=0;j<gameBoard.getBlocks()[i].length;j++) {
                    System.out.print(gameBoard.getBlocks()[i][j]);
                }
                System.out.println();
            }
            return true;
        }
//        for (int column = 0;column < typeShape[0].length;column++) {
//            if (typeShape[typeShape.length-1][column] != 0) {
//                if (board[positionY+typeShape.length][positionX+column] != 0) {
//                    setBoard(gameBoard.getBlocks());
//                    return true;
//                }
//
//            }
//        }
        for (int row = 0;row < typeShape.length;row++) {
            for (int column = 0;column < typeShape[row].length;column++) {
                if (typeShape[row][column] != 0) {
                    if (board[positionY+row+1][positionX+column] != 0) {
//                        positionY--;
                        setBoard(gameBoard.getBlocks());
                        return true;
                    }
                }
            }
        }

        return false;
    }
    public void speedUp() {
        currentSpeed = downSpeed;
    }
    public void normalSpeed() {
        currentSpeed = normalSpeed;
    }
    private void setBoard(int[][] board) {
        for (int row = 0;row < typeShape.length;row++) {
            for (int column = 0;column < typeShape[row].length;column++) {
                if (typeShape[row][column] != 0) {
                    board[positionY+row][positionX+column] = color;
                }
            }
        }
    }
    private boolean checkLeft() {
        for (int row = 0;row < typeShape.length;row++) {
           for (int column = 0;column < typeShape[row].length;column++) {
               if (typeShape[row][column] != 0) {
                   if (gameBoard.getBlocks()[positionY+row][positionX-1+column] != 0) {
                       return true;
                   }
               }
           }
        }
        return false;
    }
    private boolean checkRight() {
        for (int row = 0;row < typeShape.length;row++) {
           for (int column = 0;column < typeShape[row].length;column++) {
               if (typeShape[row][column] != 0) {
                   if (gameBoard.getBlocks()[positionY+row][positionX+1+column] != 0) {
                       return true;
                   }
               }
           }

        }
        return false;
    }
    public int checkMove() {
        if (positionX == 0) {
            return 1;
        }
        if (checkLeft()) {
            return 1;
        }
        if (positionX + typeShape[0].length == gameBoard.getBlocks()[0].length) {
            return 2;
        }
        if (checkRight()) {
            return 2;
        }
        return 0;
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

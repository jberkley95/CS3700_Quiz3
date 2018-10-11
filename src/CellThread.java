import java.util.concurrent.Phaser;

/**
 * @author John Berkley
 * CPP Class: CS 3700
 * Date Created: Oct 10, 2018
 */
public class CellThread implements Runnable {
    private Phaser phaser1;
    private Phaser phaser2;
    private boolean[][] currentBoard;
    private boolean[][] nextBoard;
    private int row;
    private int col;

    public CellThread(Phaser phaser1, Phaser phaser2, boolean[][] currentBoard, boolean[][] nextBoard, int row, int col) {
        this.phaser1 = phaser1;
        this.phaser2 = phaser2;
        this.currentBoard = currentBoard;
        this.nextBoard = nextBoard;
        this.row = row;
        this.col = col;
    }

    @Override
    public void run() {
        boolean isAlive;
        int numAdjacentAlive;

        while (true) {
            isAlive = currentBoard[row][col];

            numAdjacentAlive = 0;

            // Upper Left
            try {
                if (currentBoard[row - 1][col - 1]) {
                    numAdjacentAlive++;
                }
            } catch (IndexOutOfBoundsException e) {}
            // Above
            try {
                if (currentBoard[row - 1][col]) {
                    numAdjacentAlive++;
                }
            } catch (IndexOutOfBoundsException e) {}
            // Upper right
            try {
                if (currentBoard[row - 1][col + 1]) {
                    numAdjacentAlive++;
                }
            } catch (IndexOutOfBoundsException e) {}
            // Left
            try {
                if (currentBoard[row][col - 1]) {
                    numAdjacentAlive++;
                }
            } catch (IndexOutOfBoundsException e) {}
            // Right
            try {
                if (currentBoard[row][col + 1]) {
                    numAdjacentAlive++;
                }
            } catch (IndexOutOfBoundsException e) {}
            // Bottom Left
            try {
                if (currentBoard[row + 1][col - 1]) {
                    numAdjacentAlive++;
                }
            } catch (IndexOutOfBoundsException e) {}
            // Below
            try {
                if (currentBoard[row + 1][col]) {
                    numAdjacentAlive++;
                }
            } catch (IndexOutOfBoundsException e) {}
            // Bottom Right
            try {
                if (currentBoard[row + 1][col + 1]) {
                    numAdjacentAlive++;
                }
            } catch (IndexOutOfBoundsException e) {}

            nextBoard[row][col] = (isAlive && (numAdjacentAlive >= 2 && numAdjacentAlive <= 3)) || (!isAlive && numAdjacentAlive == 3);
            phaser2.arrive();
            phaser1.awaitAdvance(phaser1.getPhase());

            //reset space
            try {
                nextBoard[row][col] = false;
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
}

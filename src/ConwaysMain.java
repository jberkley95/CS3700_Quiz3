import java.util.Arrays;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author John Berkley
 * CPP Class: CS 3700
 * Date Created: Oct 10, 2018
 */
public class ConwaysMain {
    public static void main(String[] args) {
        final int M = 25, N = 25;
        boolean[][] currentBoard = new boolean[M][N];
        boolean[][] nextBoard = new boolean[M][N];
        Phaser phaser1 = new Phaser(1);
        Phaser phaser2 = new Phaser(M * N);
        int generation = 0;

        // Fill board
        for (int i = 0; i < (M * N) * .25; i++) {
            currentBoard[(int) (Math.random() * M)][(int) (Math.random() * N)] = true;
        }

        // Create threads
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                new Thread(new CellThread(phaser1, phaser2, currentBoard, nextBoard, i, j)).start();
            }
        }

        while (true) {
            phaser2.awaitAdvance(phaser2.getPhase());
            updateBoard(currentBoard, nextBoard);
            displayBoard(currentBoard, generation++);
            phaser1.arrive();
        }
    }

    public static void displayBoard(boolean[][] board, int generation) {
        System.out.println("Generation: " + generation);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j]) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public static void updateBoard(boolean[][] currentBoard, boolean[][] nextBoard) {
        for (int i = 0; i < currentBoard.length; i++) {
            currentBoard[i] = Arrays.copyOfRange(nextBoard[i], 0, currentBoard[i].length);
        }
    }
}

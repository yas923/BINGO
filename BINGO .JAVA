import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Bingo {
    private static final int GRID_SIZE = 5;
    private static final int MAX_NUMBER = 75;
    private int[][] card;
    private Set<Integer> drawnNumbers;
    private boolean[][] marked;

    public Bingo() {
        card = new int[GRID_SIZE][GRID_SIZE];
        marked = new boolean[GRID_SIZE][GRID_SIZE];
        drawnNumbers = new HashSet<>();
        generateCard();
    }

    // Generate Bingo card with random numbers
    private void generateCard() {
        Random random = new Random();
        for (int i = 0; i < GRID_SIZE; i++) {
            Set<Integer> columnNumbers = new HashSet<>();
            int rangeStart = i * 15 + 1;
            int rangeEnd = rangeStart + 15;
            for (int j = 0; j < GRID_SIZE; j++) {
                int num;
                do {
                    num = random.nextInt(rangeEnd - rangeStart) + rangeStart;
                } while (columnNumbers.contains(num));
                card[j][i] = num;
                columnNumbers.add(num);
            }
        }
        card[GRID_SIZE / 2][GRID_SIZE / 2] = 0; // Free space in the center
        marked[GRID_SIZE / 2][GRID_SIZE / 2] = true;
    }

    // Display the Bingo card
    public void displayCard() {
        System.out.println("B   I   N   G   O");
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (card[i][j] == 0) {
                    System.out.print(" *  ");
                } else {
                    System.out.printf("%2d  ", card[i][j]);
                }
            }
            System.out.println();
        }
    }

    // Mark a number if it is on the Bingo card
    public void markNumber(int number) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (card[i][j] == number) {
                    marked[i][j] = true;
                    System.out.println("Number " + number + " marked!");
                    return;
                }
            }
        }
        System.out.println("Number " + number + " not found on your card.");
    }

    // Check if the player has won (5 in a row/column/diagonal)
    public boolean checkWin() {
        // Check rows
        for (int i = 0; i < GRID_SIZE; i++) {
            boolean win = true;
            for (int j = 0; j < GRID_SIZE; j++) {
                if (!marked[i][j]) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        // Check columns
        for (int i = 0; i < GRID_SIZE; i++) {
            boolean win = true;
            for (int j = 0; j < GRID_SIZE; j++) {
                if (!marked[j][i]) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        // Check diagonals
        boolean win = true;
        for (int i = 0; i < GRID_SIZE; i++) {
            if (!marked[i][i]) {
                win = false;
                break;
            }
        }
        if (win) return true;

        win = true;
        for (int i = 0; i < GRID_SIZE; i++) {
            if (!marked[i][GRID_SIZE - 1 - i]) {
                win = false;
                break;
            }
        }
        return win;
    }

    public static void main(String[] args) {
        Bingo bingo = new Bingo();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your Bingo Card:");
        bingo.displayCard();

        while (true) {
            System.out.print("Enter a drawn number (1-75), or 0 to quit: ");
            int number = scanner.nextInt();
            if (number == 0) break;

            if (number < 1 || number > MAX_NUMBER || bingo.drawnNumbers.contains(number)) {
                System.out.println("Invalid or repeated number. Try again.");
                continue;
            }

            bingo.drawnNumbers.add(number);
            bingo.markNumber(number);

            if (bingo.checkWin()) {
                System.out.println("Bingo! You won!");
                break;
            }
        }
        scanner.close();
        System.out.println("Game over. Thanks for playing!");
    }
}
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SudokuSolver problem = new SudokuSolver(new int[][] {{0, 0, 6, 1, 0, 0, 0, 0, 8},
                {0, 8, 0, 0, 9, 0, 0, 3, 0},
                {2, 0, 0, 0, 0, 5, 4, 0, 0},
                {4, 0, 0, 0, 0, 1, 8, 0, 0},
                {0, 3, 0, 0, 7, 0, 0, 4, 0},
                {0, 0, 7, 9, 0, 0, 0, 0, 3},
                {0, 0, 8, 4, 0, 0, 0, 0, 6},
                {0, 2, 0, 0, 5, 0, 0, 8, 0},
                {1, 0, 0, 0, 0, 2, 5, 0, 0}});
        int[][] solution = problem.solve();
        for (int[] row : solution) {
            System.out.println(Arrays.toString(row));
        }
    }
}

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuSolver {
    int[][] grid;

    public SudokuSolver(int[][] grid) {
        this.grid = grid;
    }

    public int[][] solve() {
        if (!testException()) {
            throw new IllegalArgumentException();
        }
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells.add(new Cell(grid[i][j], i, j));
            }
        }
        setUpConnected(cells);
        cells = cells.stream().filter(e -> e.value == 0).collect(Collectors.toList());
        if (cells.size() == 0) {
            throw new IllegalArgumentException();
        }
        setUpPossibles(cells);
        cells.sort(Comparator.comparing(e -> e.getPossibles().size()));
        return solver(cells, grid);
    }

    private int[][] solver(List<Cell> cells, int[][] grid) {
        int iteration = 0;
        List<Cell> tested = new ArrayList<>();
        boolean hasSolution = false;
        int[][] output = new int[9][9];
        while (true) {
            Cell current = cells.get(0);
            if (current.validate()) {
                current.setValue(current.getPossibles().get(current.getIteration()));
                current.setIteration(current.getIteration() + 1);
                grid[current.getI()][current.getJ()] = current.getValue();
                cells.remove(0);
                tested.add(current);
                iteration++;
            } else {
                current.setIteration(current.getIteration() + 1);
                if (current.getIteration() >= current.getPossibles().size()) {
                    current.setIteration(0);
                    try {
                        current = tested.get(tested.size() - 1);
                    } catch (Exception e) {
                        if (hasSolution) break;
                        throw new IllegalArgumentException();
                    }
                    grid[current.getI()][current.getJ()] = 0;
                    current.setValue(0);
                    tested.remove(tested.size() - 1);
                    cells.add(0, current);
                }
            }
            if (cells.size() == 0 && !hasSolution) {
                for (int i = 0; i < 9; i++) {
                    output[i] = grid[i].clone();
                }
                current.setIteration(current.getIteration() + 1);
                grid[current.getI()][current.getJ()] = 0;
                current.setValue(0);
                tested.remove(tested.size() - 1);
                cells.add(current);
                hasSolution = true;
            } else if (cells.size() == 0) {
                for (int i = 0; i < 9; i++) {
                    if(!Arrays.equals(output[i], grid[i])) {
                        throw new IllegalArgumentException();
                    }
                }
                break;
            }
            if (iteration >= 10000 && hasSolution) {
                break;
            }
        }
        return output;
    }

    private void setUpConnected(List<Cell> cells) {
        for (Cell cell : cells) {
            List<Cell> connected = new ArrayList<>();
            int i = cell.getI() % 3 == 0 ? cell.getI() + 1 : cell.getI() % 3 == 1 ? cell.getI() : cell.getI() - 1;
            int j = cell.getJ() % 3 == 0 ? cell.getJ() + 1 : cell.getJ() % 3 == 1 ? cell.getJ() : cell.getJ() - 1;
            for (Cell connection : cells) {
                if (cell == connection) continue;
                if (cell.getI() == connection.getI() || cell.getJ() == connection.getJ()) {
                    connected.add(connection);
                    continue;
                }
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (connection.getI() == i + k && connection.getJ() == j + l) {
                            connected.add(connection);
                        }
                    }
                }
            }
            cell.setConnected(connected);
        }
    }

    private void setUpPossibles(List<Cell> cells) {
        for (Cell cell : cells) {
            Set<Integer> taken = new HashSet<>();
            for (Cell n : cell.connected) {
                if (n.value != 0) {
                    taken.add(n.value);
                }
            }
            List<Integer> list = IntStream.range(1, 10).boxed().collect(Collectors.toList());
            list.removeAll(taken);
            cell.setPossibles(list);
        }
    }

    private boolean testException(){
        for (int[] row : grid) {
            if (grid.length != 9 || row.length != 9) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            List<Integer> row = Arrays.stream(grid[i]).filter(e -> e != 0).boxed().collect(Collectors.toList());
            if (new HashSet<>(row).size() != row.size() || row.stream().anyMatch(e -> e < 0 || e > 9)) {
                return false;
            }
            List<Integer> column = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                if (grid[j][i] != 0) {
                    column.add(grid[j][i]);
                }
            }
            if (new HashSet<>(column).size() != column.size()) {
                return false;
            }
        }
        for (int i = 1; i <= 7; i += 3) {
            for (int j = 1; j <= 7; j += 3) {
                List<Integer> block = new ArrayList<>();
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (grid[i + k][j + l] != 0) {
                            block.add(grid[i + k][j + l]);
                        }
                    }
                }
                if (new HashSet<>(block).size() != block.size()) {
                    return false;
                }
            }
        }
        return true;
    }
}
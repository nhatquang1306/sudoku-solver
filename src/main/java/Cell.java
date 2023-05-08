import java.util.ArrayList;
import java.util.List;

public class Cell {
    public int value, i, j, iteration;
    public List<Cell> connected;
    public List<Integer> possibles;

    public Cell(int value, int i, int j) {
        this.value = value;
        this.i = i;
        this.j = j;
        iteration = 0;
        connected = new ArrayList<>();
        possibles = new ArrayList<>();
    }

    public boolean validate() {
        if (iteration >= possibles.size()) {
            return false;
        }
        for (Cell cell : connected) {
            if (possibles.get(iteration) == cell.getValue()) {
                return false;
            }
        }
        return true;
    }

    public List<Cell> getConnected() {
        return connected;
    }

    public void setConnected(List<Cell> connected) {
        this.connected = connected;
    }

    public List<Integer> getPossibles() {
        return possibles;
    }

    public void setPossibles(List<Integer> possibles) {
        this.possibles = possibles;
    }

    public void addPossible(int value) {}

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }
}
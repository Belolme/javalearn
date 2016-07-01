/**
 * Created by billin on 16-7-1.
 * Chess Position
 */
public class Position {

    private int col;
    private int row;

    public Position(int row, int col) {
        setRow(row);
        setCol(col);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "Position{" +
                "col=" + col +
                ", row=" + row +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return col == position.col && row == position.row;
    }
}
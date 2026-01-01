package cpsc2150.extendedCheckers.models;

/**
 * BoardPosition is an object for storing information pertaining to the positions on the checkerboard for the checkers
 * game. Each BoardPosition has a row and a column. BoardPosition.java contains a method for checking whether two
 * BoardPositions are equal, and also provides means of converting a BoardPosition's information into a string
 *
 * @invariant row >= 0 and row < [boardSize obtained in CheckersFE] AND column >= 0 and column < [boardSize obtained
 * in CheckersFE]
 */
public class BoardPosition {
    // row component of the BoardPosition
    private int row;

    // column component of the BoardPosition
    private int column;

    /**
     * Parameterized constructor for BoardPosition object. Sets row and column instance variables to their respective
     * parameters. Accepts two parameters.
     * @param aRow The row number of the board position, as an int
     * @param aCol The column number of the board position, as an int
     *
     * @pre aRow >= 0 and aRow < [boardSize obtained in CheckersFE] AND aCol >= 0 and aCol < [boardSize obtained in
     * CheckersFE]
     *
     * @post this.row = aRow AND this.column = aCol
     */
    public BoardPosition(int aRow, int aCol) {
        this.row = aRow;
        this.column = aCol;
    }

    /**
     * Simple accessor for the row instance variable.
     * @return The row number of the board position, as an int
     *
     * @pre None
     *
     * @post getRow = row AND row = #row AND column = #column
     */
    public int getRow() {
        return row;
    }

    /**
     * Simple accessor for the column instance variable.
     * @return The column number of the board position, as an int
     *
     * @pre None
     *
     * @post getColumn = column AND row = #row AND column = #column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Compares a BoardPosition to the parameter Object. Accepts one parameter.
     * @param obj An instance of BoardPosition, as an Object
     * @return True when an Object and a reference to a BoardPosition are equal (have the same row and column values),
     * as a boolean, OR, false when an Object and a reference to a BoardPosition are not equal, as a boolean
     *
     * @pre obj != null
     *
     * @post equals = [True when an Object and a reference to a BoardPosition are equal (have the same row and column
     * values), OR, false when an Object and a reference to a BoardPosition are not equal] AND row = #row AND column
     * = #column
     */
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;

        if (obj == this) {
            isEqual = true;
        }
        else if (((BoardPosition) obj).getRow() == this.row && ((BoardPosition) obj).getColumn() == column) {
            isEqual = true;
        }
        return isEqual;
    }

    /**
     * Creates a String that represents BoardPosition.
     * @return a representation of the BoardPosition in the format of "row,column", as a String
     *
     * @pre None
     *
     * @post toString = [BoardPosition in the form of "row,column"] AND row = #row AND column = #column
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(row).append(",").append(column);
        return result.toString();
    }
}

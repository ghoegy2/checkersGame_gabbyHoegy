package cpsc2150.extendedCheckers.models;

/**
 * AbsCheckerBoard is an implementation of ICheckerBoard that provides an override of toString(). It is implemented by
 * calling the primary methods in ICheckerBoard {@link ICheckerBoard}.
 *
 * @invariant 0 <= [boardArray row value][boardArray column value] < boardSize AND 0 <= pieces <= boardSize AND
 * 0 <= directions <= boardSize
 *
 * @cooresponds None, no private data
 */
public abstract class AbsCheckerBoard implements ICheckerBoard {
    /**
     * Creates a String that represents the physical and current status of the checkerboard.
     * @return a "header" line to display all column numbers and a "header column" that displays all row numbers, as a
     * String
     *
     * @pre None
     *
     * @post toString = ["header" line to display all column numbers and "header column" to display all row numbers]
     * board = #board AND pieceCount = #pieceCount AND BOARD_DIMENSION = #boardDimension AND playerOne = #playerOne
     * AND playerTwo = #playerTwo AND playerOneKing = #playerOneKing AND playerTwoKing = #playerTwoKing AND
     * viableDirections = #viableDirections
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int boardSize = getBoardSize();
        int row, column;
        char boardChar;
        BoardPosition pos;

        // adds the 0th row to the resulting checkerboard string as a header
        result.append("|  |");
        for (column = 0; column < boardSize; column++) {
            result.append(String.format("%2d|", column));
        }
        result.append("\n");

        // adds the rest of the checkerboard's elements to the string
        for (row = 0; row < boardSize; row++) {
            result.append(String.format("|%-2d|", row));
            for (column = 0; column < boardSize; column++) {
                pos = new BoardPosition(row, column);
                boardChar = whatsAtPos(pos);
                result.append(boardChar).append(" |");
            }
            result.append("\n");
        }
        return result.toString();
    }
}

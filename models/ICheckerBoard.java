package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ICheckerBoard represents a checkerboard for players and pieces. Its methods work with the CheckerBoard implementation
 * and the CheckerBoardMem implementation of the game. It contains moves that players can choose and pieces can
 * complete. ICheckerBoard gives the user the ability to calculate directions in the form of a BoardPosition with
 * getDirections, check if a player has won the game using checkPlayerWin, "crown" a piece using crownPiece, move a
 * piece using movePiece, jump a piece using jumpPiece, see what is in positions around a BoardPosition using
 * scanSurroundingPositions, calculate valid directions to move a piece in using determineViableDirections, and
 * determine whether a piece has reached the opposite side of the board using pieceAtOtherSide.
 *
 * @defines self: the board
 *          pieces: the pieceCount of a player
 *          boardSize: the BOARD_DIMENSION of the checkerboard
 *          player1: the name of playerOne
 *          player2: the name of playerTwo
 *          player1King: the name of playerOneKing
 *          player2King: the name of playerTwoKing
 *          directions: the viableDirections a player can move a piece in
 *
 * @constraints [player pieces cannot occupy the positions of black tiles] AND [a player's number of pieces can
 * never be negative] AND [pieces cannot go past the boardSize row and columns of the board]
 *
 * @initialization_ensures self is initialized as a checkerboard with dimensions of a user-chosen number (8x8, 10x10,
 * 12x12, 14x14, or 16x16). Each player will start with a number of pieces that correspond with the size of their board
 * (12 for 8x8, 20 for 10x10, 30 for 12x12, 42 for 14x14, or 56 for 16x16). Player one will start with a set of two
 * directions (SE and SW) and player two will start with a set of two directions (NE and NW).
 */
public interface ICheckerBoard {
    int TWO_POSITIONS = 2;
    char EMPTY_POS = ' ';
    char BLACK_TILE = '*';
    int EVEN_DIVISOR = 2;
    char NULL_POS = '\0';

    /**
     * Fills in a new, current version of the checkerboard with player pieces. This method utilizes the provided
     * boardSize to initialize how large the board is. It also initializes the players' chars and pieces. Then, it
     * places the char pieces on the board. Finally, it initializes directions for the players. Accepts two parameters.
     * @param playerOnePiece The letter that represents player one's piece, as a char
     * @param playerTwoPiece The letter that represents player two's piece, as a char
     *
     * @pre playerOnePiece != null AND playerTwoPiece != null AND CheckersFE.PIECE_LETTER_MIN < playerOnePiece <
     * CheckersFE.PIECE_LETTER_MAX AND CheckersFE.PIECE_LETTER_MIN < playerTwoPiece < CheckersFE.PIECE_LETTER_MAX
     *
     * @post putPlayersOnNewBoard = [board that's filled in] AND pieceCount = pieceCount++ AND viableDirections =
     * [a starting list of viableDirections for each player] AND self = #self AND pieces = #pieces AND boardSize
     * = #boardSize AND player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King
     * = #player2King AND directions = #directions
     */
    public void putPlayersOnNewBoard(char playerOnePiece, char playerTwoPiece);

    /**
     * Simple accessor for the viableDirections HashMap.
     * @return A player's viable directions, as a HashMap containing a Character and an ArrayList of DirectionEnums
     *
     * @pre None
     *
     * @post getViableDirections = #directions AND self = #self AND pieces = #pieces AND boardSize
     * = #boardSize AND player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King
     * = #player2King AND directions = #directions
     */
    HashMap<Character, ArrayList<DirectionEnum>> getViableDirections();

    /**
     * Simple accessor for the pieceCount HashMap.
     * @return A player's piece count, as a HashMap containing a Character and an Integer
     *
     * @pre None
     *
     * @post getPieceCounts = #pieces AND self = #self AND pieces = #pieces AND boardSize = #boardSize AND player1
     * = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King = #player2King AND directions
     * = #directions
     */
    HashMap<Character, Integer> getPieceCounts();

    /**
     * Calculates a new position given a chosen direction. Accepts one parameter.
     * @param dir The chosen direction the player wants to move their piece in, as a DirectionEnum
     * @return The new position that could be added to any given piece's position, as a BoardPosition
     *
     * @pre dir != null
     *
     * @post self = #self AND pieces = #pieces AND boardSize = #boardSize AND player1 = #player1 AND player2 = #player2
     * AND player1King = #player1King AND player2King = #player2King AND directions = #directions
     */
    static BoardPosition getDirection(DirectionEnum dir) {
        return switch (dir) {
            // initializes the directions around the current BoardPosition with new BoardPositions
            case NE -> new BoardPosition(-1, 1);
            case NW -> new BoardPosition(-1, -1);
            case SE -> new BoardPosition(1, 1);
            case SW -> new BoardPosition(1, -1);
        };
    }

    /**
     * Simple accessor for the row integer of the checkerboard's size.
     * @return The maximum row number of the checkerboard's size, as an int
     *
     * @pre None
     *
     * @post getRowNum = boardSize AND self = #self AND pieces = #pieces AND boardSize = #boardSize AND
     * player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King = #player2King
     * AND directions = #directions
     */
    int getRowNum();

    /**
     * Simple accessor for the column integer of the checkerboard's size.
     * @return The maximum column number of the checkerboard's size, as an int
     *
     * @pre None
     *
     * @post getColNum = boardSize AND self = #self AND pieces = #pieces AND boardSize = #boardSize AND
     * player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King = #player2King
     * AND directions = #directions
     */
    int getColNum();

    /**
     * Sets a given BoardPosition equal to the char given by player. Also, updates pieceCount. Accepts two parameters.
     * @param pos The current position on the checkerboard, as a BoardPosition
     * @param player The letter that represents a player's name and pieces, as a char
     *
     * @pre pos != null AND player != null AND 0 < [position row number] < boardSize AND 0 < [position column number] <
     * boardSize
     *
     * @post placePiece = [a player's piece as a value in the given boardArray position] AND pieces = pieces + 1 AND
     * self = #self AND pieces = #pieces AND boardSize = #boardSize AND player1 = #player1 AND player2 = #player2 AND
     * player1King = #player1King AND player2King = #player2King AND directions = #directions
     */
    void placePiece(BoardPosition pos, char player);

    /**
     * A standard accessor for the BoardPosition. Identifies the character located at a given BoardPosition.
     * Accepts one parameter.
     * @param pos The position on the checkerboard given by the boardArray, as a BoardPosition
     * @return What is located at the provided position, as a char
     *
     * @pre pos != null AND 0 < [position row number] < boardSize AND 0 < [position column number] < boardSize
     *
     * @post whatsAtPos = [what is located at pos in boardArray] AND self = #self AND pieces = #pieces AND boardSize
     * = #boardSize AND player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King =
     * #player2King AND directions = #directions
     */
    char whatsAtPos(BoardPosition pos);

    /**
     * Checks if a player has won the game of checkers. Accepts one parameter.
     * @param player The player that is being evaluated to see if they won, as a Character
     * @return True if all remaining pieces on the board belong to one player, as a boolean, OR, false if both players
     * still have pieces on the board, as a boolean
     *
     * @pre player != null
     *
     * @post checkPlayerWin = [true when all remaining pieces on board belong to one player] AND self = #self AND
     * pieces = #pieces AND boardSize = #boardSize AND player1 = #player1 AND player2 = #player2 AND player1King =
     * #player1King AND player2King = #player2King AND directions = #directions
     */
    default boolean checkPlayerWin(Character player) {
        boolean playerWon = false;
        HashMap<Character, Integer> pieces = getPieceCounts();
        // when it's player1's turn and player2 doesn't have any pieces left, player1 wins
        if (player == getPlayerOne() || player == getPlayerOneKing()) {
            if (pieces.get(getPlayerTwo()) == 0) {
                playerWon = true;
            }
        }
        // when it's player2's turn and player1 doesn't have any pieces left, player1 wins
        else if (player == getPlayerTwo() || player == getPlayerTwoKing()) {
            if (pieces.get(getPlayerOne()) == 0) {
                playerWon = true;
            }
        }
        return playerWon;
    }

    /**
     * "Crowns" a piece by converting the value in the BoardPosition to an equivalent uppercase value that identifies
     * that the piece has been crowned. Accepts one parameter.
     * @param posOfPlayer The current position of a player, as a BoardPosition
     *
     * @pre posOfPlayer != null 0 <= [position row number] < boardSize AND 0 <= [position column number] < boardSize
     *
     * @post crownPiece = [changes the symbol of a player's piece to show it is a king] AND self = #self AND pieces =
     * #pieces AND boardSize = #boardSize AND player1 = #player1 AND player2 = #player2 AND player1King = #player1King
     * AND player2King = #player2King AND directions = #directions
     */
    default void crownPiece(BoardPosition posOfPlayer) {
        char piece = whatsAtPos(posOfPlayer);

        // crowns player1's piece
        if (piece == getPlayerOne()) {
            removePiece(posOfPlayer);
            placePiece(posOfPlayer, getPlayerOneKing());
        }
        // crowns player2's piece
        else {
            removePiece(posOfPlayer);
            placePiece(posOfPlayer, getPlayerTwoKing());
        }
    }

    /**
     * Moves a piece on the board given a starting position and a direction to move in. Accepts two parameters.
     * @param startingPos The player's current position on the checkerboard, as a BoardPosition
     * @param dir The direction the player's piece will move in, as a DirectionEnum
     * @return The new position that the piece was moved will be located at, as a BoardPosition
     *
     * @pre startingPos != null AND dir != null AND 0 <= [position row number] < boardSize AND 0 <=
     * [position column number] < boardSize
     *
     * @post movePiece = [the new position that the piece that was moved will be located at] AND self = #self AND
     * pieces = #pieces AND boardSize = #boardSize AND player1 = #player1 AND player2 = #player2 AND player1King
     * = #player1King AND player2King = #player2King AND directions = #directions
     */
    default BoardPosition movePiece(BoardPosition startingPos, DirectionEnum dir) {
        char piece = whatsAtPos(startingPos);
        // calculates the new BoardPosition the piece will move to, given a direction
        BoardPosition newPos = new BoardPosition(startingPos.getRow() + getDirection(dir).getRow(), startingPos.getColumn() + getDirection(dir).getColumn());
        // removes the piece from where it started
        removePiece(startingPos);
        // places the piece that was moved in the new BoardPosition
        placePiece(newPos, piece);
        return newPos;
    }

    /**
     * Performs operations to "jump" an opponent's piece (moves two positions). Removes the piece that was jumped.
     * Accepts two parameters.
     * @param startingPos The player's current position on the checkerboard, as a BoardPosition
     * @param dir The direction the player's piece will move in, as a DirectionEnum
     * @return The new position that a player's piece jumped to, as a BoardPosition. Also, removes one piece
     * from the opponent's pieceCount
     *
     * @pre startingPos != null AND dir != null AND 0 <= [position row number] < boardSize AND 0 <=
     * [position column number] < boardSize
     *
     * @post jumpPiece = [the piece's new position in boardArray after the jump] AND pieces = #pieces - 1 AND
     * self = #self AND pieces = #pieces AND boardSize = #boardSize AND player1 = #player1 AND player2 = #player2
     * AND player1King = #player1King AND player2King = #player2King AND directions = #directions
     */
    default BoardPosition jumpPiece(BoardPosition startingPos, DirectionEnum dir) {
        char piece = whatsAtPos(startingPos);

        // calculates the new position of a player's piece after it jumps an opponent's piece
        BoardPosition newPos = new BoardPosition(startingPos.getRow() + getDirection(dir).getRow() + getDirection(dir).getRow(), startingPos.getColumn() + getDirection(dir).getColumn() + getDirection(dir).getColumn());
        // locates the position of the opponent's piece that is being captured
        BoardPosition posToCapture = new BoardPosition(startingPos.getRow() + getDirection(dir).getRow(), startingPos.getColumn() + getDirection(dir).getColumn());
        // remove the player's piece
        removePiece(startingPos);
        // places the player's piece that just jumped in the new position
        placePiece(newPos, piece);
        // removes the opponent's piece that was jumped from the board
        removePiece(posToCapture);
        return newPos;
    }

    /**
     * Removes an opponent's piece that has been jumped by a player's piece from the board. Accepts one parameter.
     * @param capturePos The board position of the piece the player wants to remove, as a BoardPosition
     *
     * @pre pos != null AND 0 < [pos row] < boardSize AND 0 < [pos column] < boardSize
     *
     * @post removePiece = [removes a player's piece that has been jumped from the board] AND pieces = pieces - 1 AND
     * self = #self AND pieces = #pieces AND boardSize = #boardSize AND player1 = #player1 AND player2 = #player2
     * AND player1King = #player1King AND player2King = #player2King AND directions = #directions
     */
    void removePiece(BoardPosition capturePos);

    /**
     * Scans surrounding indices of a BoardPosition to see if they are empty or occupied. Accepts one parameter.
     * @param startingPos The player's current position on the checkerboard, as a BoardPosition
     * @return What is contained in the indices surrounding the provided position, as a HashMap<DirectionEnum,
     * Character>
     *
     * @pre startingPos != null AND 0 <= [position row number] < boardSize AND 0 <= [position column number] <
     * boardSize
     *
     * @post scanSurroundingPositions = [what is contained in the indices surrounding the boardArray position] AND
     * self = #self AND pieces = #pieces AND boardSize = #boardSize AND player1 = #player1 AND player2 = #player2
     * AND player1King = #player1King AND player2King = #player2King AND directions = #directions
     */
    default HashMap<DirectionEnum, Character> scanSurroundingPositions(BoardPosition startingPos) {
        HashMap<DirectionEnum, Character> resultMap = new HashMap<>();
        BoardPosition pos;
        BoardPosition dirPos;

        // checks the area surrounding NE
        pos = getDirection(DirectionEnum.NE);
        dirPos = new BoardPosition(startingPos.getRow() + pos.getRow(), startingPos.getColumn() + pos.getColumn());
        if ((dirPos.getRow() >= 0) && (dirPos.getColumn() < getBoardSize())) {
            resultMap.put(DirectionEnum.NE, whatsAtPos(dirPos));
        }
        // checks the area surrounding NW
        pos = getDirection(DirectionEnum.NW);
        dirPos = new BoardPosition(startingPos.getRow() + pos.getRow(), startingPos.getColumn() + pos.getColumn());
        if ((dirPos.getRow() >= 0) && (dirPos.getColumn() >= 0)) {
            resultMap.put(DirectionEnum.NW, whatsAtPos(dirPos));
        }
        // checks the area surrounding SE
        pos = getDirection(DirectionEnum.SE);
        dirPos = new BoardPosition(startingPos.getRow() + pos.getRow(), startingPos.getColumn() + pos.getColumn());
        if ((dirPos.getRow() < getBoardSize()) && (dirPos.getColumn() < getBoardSize())) {
            resultMap.put(DirectionEnum.SE, whatsAtPos(dirPos));
        }
        // checks the area surrounding SW
        pos = getDirection(DirectionEnum.SW);
        dirPos = new BoardPosition(startingPos.getRow() + pos.getRow(), startingPos.getColumn() + pos.getColumn());
        if ((dirPos.getRow() < getBoardSize()) && (dirPos.getColumn() >= 0)) {
            resultMap.put(DirectionEnum.SW, whatsAtPos(dirPos));
        }
        return resultMap;
    }

    /**
     * Sets a player's position on the board. This is the piece that will be moved. This method also identifies
     * opponent pieces and creates the viableDirections HashMap by determining valid directions a player could move
     * in given a board position.
     * @param pos The board position of the piece the player will move, as a BoardPosition
     *
     * @pre pos != null AND 0 <= [position row number] < boardSize AND 0 <= [position column number] < boardSize
     *
     * @post determineViableDirections = [the board position of the piece the player will move] AND self = #self AND
     * pieces = #pieces AND boardSize = #boardSize AND player1 = #player1 AND player2 = #player2 AND player1King =
     * #player1King AND player2King = #player2King AND directions = #directions
     */
    default void determineViableDirections(BoardPosition pos) {
        char player, opponent, opponentKing;
        boolean isPlayerKing, isPlayerOne, isPlayerTwo;
        ArrayList<DirectionEnum> possibleDirections = new ArrayList<>();
        int row, column;

        // gets information about the player and determines the opponent
        player = whatsAtPos(pos);
        isPlayerKing = (player == getPlayerOneKing() || player == getPlayerTwoKing());
        isPlayerOne = (player == getPlayerOne() || player == getPlayerOneKing());
        isPlayerTwo = (player == getPlayerTwo() || player == getPlayerTwoKing());
        if (isPlayerOne == true) {
            opponent = getPlayerTwo();
            opponentKing = getPlayerTwoKing();
        }
        else {
            opponent = getPlayerOne();
            opponentKing = getPlayerOneKing();
        }

        // determines what is around a player
        HashMap<DirectionEnum, Character> surroundingArea = scanSurroundingPositions(pos);

        // when a position in a desired direction is empty, the direction is viable and gets added to the possibleDirections array
        if (surroundingArea.containsKey(DirectionEnum.NE) && (surroundingArea.get(DirectionEnum.NE) == EMPTY_POS) && (isPlayerTwo || isPlayerKing)) {
            possibleDirections.add(DirectionEnum.NE);
        }
        if (surroundingArea.containsKey(DirectionEnum.NW) && (surroundingArea.get(DirectionEnum.NW) == EMPTY_POS) && (isPlayerTwo || isPlayerKing)) {
            possibleDirections.add(DirectionEnum.NW);
        }
        if (surroundingArea.containsKey(DirectionEnum.SE) && (surroundingArea.get(DirectionEnum.SE) == EMPTY_POS) && (isPlayerOne || isPlayerKing)) {
            possibleDirections.add(DirectionEnum.SE);
        }
        if (surroundingArea.containsKey(DirectionEnum.SW) && (surroundingArea.get(DirectionEnum.SW) == EMPTY_POS) && (isPlayerOne || isPlayerKing)) {
            possibleDirections.add(DirectionEnum.SW);
        }

        // checks the surrounding positions past the initial surrounding positions of a board position to check if a jump can occur (a jump could happen when this position is empty)
        if (surroundingArea.containsKey(DirectionEnum.NE) && ((surroundingArea.get(DirectionEnum.NE) == opponent) || (surroundingArea.get(DirectionEnum.NE) == opponentKing)) && (isPlayerTwo || isPlayerKing)) {
            row = pos.getRow() - TWO_POSITIONS;
            column = pos.getColumn() + TWO_POSITIONS;
            if (row >= 0 && column < getBoardSize() && whatsAtPos(new BoardPosition(row, column)) == EMPTY_POS) {
                possibleDirections.add(DirectionEnum.NE);
            }
        }
        if (surroundingArea.containsKey(DirectionEnum.NW) && ((surroundingArea.get(DirectionEnum.NW) == opponent) || (surroundingArea.get(DirectionEnum.NW) == opponentKing)) && (isPlayerTwo || isPlayerKing)) {
            row = pos.getRow() - TWO_POSITIONS;
            column = pos.getColumn() - TWO_POSITIONS;
            if (row >= 0 && column >= 0 && whatsAtPos(new BoardPosition(row, column)) == EMPTY_POS) {
                possibleDirections.add(DirectionEnum.NW);
            }
        }
        if (surroundingArea.containsKey(DirectionEnum.SE) && ((surroundingArea.get(DirectionEnum.SE) == opponent) || (surroundingArea.get(DirectionEnum.SE) == opponentKing)) && (isPlayerOne || isPlayerKing)) {
            row = pos.getRow() + TWO_POSITIONS;
            column = pos.getColumn() + TWO_POSITIONS;
            if (row < getBoardSize() && column < getBoardSize() && whatsAtPos(new BoardPosition(row, column)) == EMPTY_POS) {
                possibleDirections.add(DirectionEnum.SE);
            }
        }
        if (surroundingArea.containsKey(DirectionEnum.SW) && ((surroundingArea.get(DirectionEnum.SW) == opponent) || (surroundingArea.get(DirectionEnum.SW) == opponentKing)) && (isPlayerOne || isPlayerKing)) {
            row = pos.getRow() + TWO_POSITIONS;
            column = pos.getColumn() - TWO_POSITIONS;
            if (row < getBoardSize() && column >= 0 && whatsAtPos(new BoardPosition(row, column)) == EMPTY_POS) {
                possibleDirections.add(DirectionEnum.SW);
            }
        }

        // adds valid directions for a given player and ArrayList
        setViableDirections(player, possibleDirections);
    }

    /**
     * Checks whether a piece has reached the opposite side of the board. This is row 0 for player2 and row boardSize
     * for player1. Accepts one parameter.
     * @param pos The board position of the piece the player is moving, as a BoardPosition
     * @return True when a piece reaches the farthest opposite row on the board and false otherwise
     *
     * @pre pos != null AND 0 <= [position row number] < boardSize AND 0 <= [position column number] < boardSize
     *
     * @post pieceAtOtherSide = [true when a piece reaches the farthest opposite row on the board and false otherwise]
     * AND self = #self AND pieces = #pieces AND boardSize = #boardSize AND player1 = #player1 AND player2 = #player2
     * AND player1King = #player1King AND player2King = #player2King AND directions = #directions
     */
    default boolean pieceAtOtherSide(BoardPosition pos) {
        boolean atOtherSide = false;

        // true when player one reaches the bottom of the board
        if (whatsAtPos(pos) == getPlayerOne()) {
            atOtherSide = pos.getRow() == (getBoardSize() - 1);
        }
        // true when player two reaches the top of the board
        else {
            atOtherSide = pos.getRow() == 0;
        }
        return atOtherSide;
    }

    /**
     * Sets a player's viableDirections. This method adds directions to the viableDirections HashMap by determining
     * valid directions a player could move in given a board position.
     * @param player The character that represents a player, as a char
     * @param possibleDirections The list of directions a player's piece can move, as an ArrayList<DirectionEnum>
     *
     * @pre player != null AND possibleDirections != null
     *
     * @post setViableDirections = [player and ArrayList get updated in viableDirections] AND self = #self AND pieces =
     * #pieces AND boardSize = #boardSize AND player1 = #player1 AND player2 = #player2 AND player1King = #player1King
     * AND player2King = #player2King AND directions = #directions
     */
    void setViableDirections(char player, ArrayList<DirectionEnum> possibleDirections);

    /**
     * Simple accessor for the board size of the game's checkerboard.
     * @return The checkerBoard's maximum dimension, as an int
     *
     * @pre None
     *
     * @post getBoardSize = boardSize AND self = #self AND pieces = #pieces AND boardSize = #boardSize AND
     * player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King = #player2King AND
     * directions = #directions
     */
    int getBoardSize();

    /**
     * Simple accessor for getting player1's name/piece name.
     * @return Player1's name/piece name, as a char
     *
     * @pre None
     *
     * @post getPlayerOne = player1 AND self = #self AND pieces = #pieces AND boardSize = #boardSize AND
     * player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King = #player2King AND
     * directions = #directions
     */
    char getPlayerOne();

    /**
     * Simple setter for player1's name/piece name. Also, assigns a starting pieces count to the provided piece.
     * @param piece The piece associated with player1, as a char
     *
     * @pre piece != null AND 'a' <= piece <= 'z'
     *
     * @post setPlayerOne = [player1 gets the value of piece and starting pieces is assigned to piece] AND
     * player1 = #piece AND pieces = #0 AND self = #self AND pieces = #pieces AND boardSize = #boardSize AND
     * player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King = #player2King AND
     * directions = #directions
     */
    void setPlayerOne(char piece);

    /**
     * Simple accessor for getting player1's king piece name.
     * @return Player1's king piece name, as a char
     *
     * @pre None
     *
     * @post getPlayerOneKing = player1King AND self = #self AND pieces = #pieces AND boardSize = #boardSize AND
     * player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King = #player2King AND
     * directions = #directions
     */
    char getPlayerOneKing();

    /**
     * Simple setter for player1 king's name/piece name. Also, assigns a starting pieces count to the provided piece.
     * @param piece The piece associated with player1 king, as a char
     *
     * @pre piece != null AND 'a' <= piece <= 'z'
     *
     * @post setPlayerOneKing = [player1King gets the value of piece and starting pieces is assigned to piece]
     * AND player1King = #piece AND pieces = #0 AND self = #self AND pieces = #pieces AND boardSize = #boardSize AND
     * player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King = #player2King AND
     * directions = #directions
     */
    void setPlayerOneKing(char piece);

    /**
     * Simple accessor for getting player2's name/piece name.
     * @return Player2's name/piece name, as a char
     *
     * @pre None
     *
     * @post getPlayerTwo = player2 AND self = #self AND pieces = #pieces AND boardSize = #boardSize AND
     * player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King = #player2King AND
     * directions = #directions
     */
    char getPlayerTwo();

    /**
     * Simple setter for player2's name/piece name. Also, assigns a starting pieces count to the provided piece.
     * @param piece The piece associated with player2, as a char
     *
     * @pre piece != null AND 'a' <= piece <= 'z'
     *
     * @post setPlayerTwo = [player2 gets the value of piece and starting pieces is assigned to piece] AND
     * player1 = #piece AND pieces = #0 AND self = #self AND pieces = #pieces AND boardSize = #boardSize AND
     * player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King = #player2King AND
     * directions = #directions
     */
    void setPlayerTwo(char piece);

    /**
     * Simple accessor for getting player2's king piece name.
     * @return Player2's king piece name, as a char
     *
     * @pre None
     *
     * @post getPlayerOneKing = player2King AND self = #self AND pieces = #pieces AND boardSize = #boardSize AND
     * player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King = #player2King AND
     * directions = #directions
     */
    char getPlayerTwoKing();

    /**
     * Simple setter for player2 king's name/piece name. Also, assigns a starting pieces count to the provided piece.
     * @param piece The piece associated with player2 king, as a char
     *
     * @pre piece != null AND 'a' <= piece <= 'z'
     *
     * @post setPlayerOneKing = [player2King gets the value of piece and starting pieces is assigned to piece]
     * AND player1King = #piece AND pieces = #0 AND self = #self AND pieces = #pieces AND boardSize = #boardSize AND
     * player1 = #player1 AND player2 = #player2 AND player1King = #player1King AND player2King = #player2King AND
     * directions = #directions
     */
    void setPlayerTwoKing(char piece);
}

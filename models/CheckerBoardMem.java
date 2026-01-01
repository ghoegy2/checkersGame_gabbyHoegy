package cpsc2150.extendedCheckers.models;
import cpsc2150.extendedCheckers.util.DirectionEnum;
import cpsc2150.extendedCheckers.views.CheckersFE;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * CheckerBoardMem extends AbsCheckerBoard and is an implementation of ICheckerBoard. It is for storing information
 * pertaining to the players' pieces on the checkerboard. Each CheckerBoardMem has a board, pieceCount, BOARD_DIMENSION,
 * playerOne, playerTwo, playerOneKing, playerTwoKing, and viableDirections. CheckerBoardMem.java provides means of
 * placing and removing pieces, scanning indices around board positions, removing pieces, setting viableDirections for
 * players, accessing private data variables, and creating/altering the state of the current checkerboard.
 *
 * @invariant [player pieces cannot occupy the positions of black tiles] AND [a player's number of pieces can
 * never be negative] AND [pieces cannot go past the boardSize row and columns of the board]
 *
 * @cooresponds self: the HashMap<Character, ArrayList<BoardPosition>> board
 *          pieces: the pieceCount of a player
 *          boardSize: the BOARD_DIMENSION of the checkerboard
 *          player1: the name of playerOne
 *          player2: the name of playerTwo
 *          player1King: the name of playerOneKing
 *          player2King: the name of playerTwoKing
 *          directions: the viableDirections a player can move a piece in
 */
public class CheckerBoardMem extends AbsCheckerBoard {
    /**
     * a HashMap of characters keys and ArrayList<BoardPosition> values used to represent our checkerboard.
     */
    private HashMap<Character, ArrayList<BoardPosition>> board;

    /**
     * a HashMap, with a Character key and an Integer value, that is used to map a player's char to the number of
     * tokens that player still has left on the board.
     */
    private HashMap<Character, Integer> pieceCount;

    /**
     * represents the value of the size that the board will be (either 8x8, 10x10, 12x12, 14x14, or 16x16)
     */
    private final int BOARD_DIMENSION;

    /**
     * represents types of player pieces
     */
    private char playerOne, playerTwo, playerOneKing, playerTwoKing;

    /**
     * A HashMap, with a Character key and an ArrayList of DirectionEnums value, used to map a player (and its king
     * representation) to the directions that player can viably move in. A non-kinged (standard) piece can only move
     * in the diagonal directions away from its starting position. A kinged piece can move in the same directions the
     * standard piece can move in plus the opposite directions the standard piece can move in.
     */
    private HashMap<Character, ArrayList<DirectionEnum>> viableDirections;

    /**
     * Constructor for CheckerBoardMem object. Initializes BOARD_DIMENSION and board. Accepts one parameter.
     * @param aDimension The user-chosen dimension of the checkerboard's size, as an int
     *
     * @pre aDimension != null AND aDimension = [8, 10, 12, 14, or 16]
     *
     * @post BOARD_DIMENSION = aDimension AND board = #board AND pieceCount = #pieceCount AND BOARD_DIMENSION
     * = #boardDimension AND playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing = #playerOneKing
     * AND playerTwoKing = #playerTwoKing AND viableDirections = #viableDirections
     */
    public CheckerBoardMem(int aDimension) {
        BOARD_DIMENSION = aDimension;
        board = new HashMap<>();
        pieceCount = new HashMap<>();
    }

    /**
     * Fills in a new, current version of the checkerboard with player pieces. This method utilizes a 2D char array
     * with the provided board dimension to initialize how large the board is. It also initializes the players'
     * chars and pieceCounts. Then, it places the character pieces on the board. Finally, it initializes
     * viableDirections for the players. Accepts two parameters.
     * @param playerOnePiece The letter that represents player one's piece, as a char
     * @param playerTwoPiece The letter that represents player two's piece, as a char
     *
     * @pre playerOnePiece != null AND playerTwoPiece != null AND CheckersFE.PIECE_LETTER_MIN < playerOnePiece <
     * CheckersFE.PIECE_LETTER_MAX AND CheckersFE.PIECE_LETTER_MIN < playerTwoPiece < CheckersFE.PIECE_LETTER_MAX
     *
     * @post putPlayersOnNewBoard = [board that's filled in] AND pieceCount = pieceCount++ AND viableDirections =
     * [a starting list of viableDirections for each player] AND board = #board AND pieceCount = #pieceCount AND
     * BOARD_DIMENSION = #boardDimension AND playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing =
     * #playerOneKing AND playerTwoKing = #playerTwoKing AND viableDirections = #viableDirections
     */
    @Override
    public void putPlayersOnNewBoard(char playerOnePiece, char playerTwoPiece) {
        int row, column;
        BoardPosition pos;
        int playerOneRowEnd = (BOARD_DIMENSION / EVEN_DIVISOR) - TWO_POSITIONS;
        int playerTwoRowStart = playerOneRowEnd + TWO_POSITIONS + 1;

        // initializes player input piece chars
        playerOne = playerOnePiece;
        playerTwo = playerTwoPiece;
        playerOneKing = Character.toUpperCase(playerOne);
        playerTwoKing = Character.toUpperCase(playerTwo);

        // initializes board with ArrayLists for positions
        board.put(playerOne, new ArrayList<>());
        board.put(playerOneKing, new ArrayList<>());
        board.put(playerTwo, new ArrayList<>());
        board.put(playerTwoKing, new ArrayList<>());

        // pieceCount starts at zero for the players
        pieceCount = new HashMap<>();
        pieceCount.put(playerOne, 0);
        pieceCount.put(playerTwo, 0);

        for (row = 0; row < getBoardSize(); row++) {
            for (column = 0; column < getBoardSize(); column++) {
                pos = new BoardPosition(row, column);
                // adds playerOne's pieces to the checkerboard
                if (row <= playerOneRowEnd && row % EVEN_DIVISOR == 0 && column % EVEN_DIVISOR == 0) {
                    placePiece(pos, getPlayerOne());
                }
                else if (row <= playerOneRowEnd && row % EVEN_DIVISOR == 1 && column % EVEN_DIVISOR == 1) {
                    placePiece(pos, getPlayerOne());
                }
                // adds playerTwo's pieces to the checkerboard
                else if (row >= playerTwoRowStart && row % EVEN_DIVISOR == 0 && column % EVEN_DIVISOR == 0) {
                    placePiece(pos, getPlayerTwo());
                }
                else if (row >= playerTwoRowStart && row % EVEN_DIVISOR == 1 && column % EVEN_DIVISOR == 1) {
                    placePiece(pos, getPlayerTwo());
                }
            }
        }
        // creates viableDirections for playerOne and playerTwo
        viableDirections = new HashMap<>();
        ArrayList<DirectionEnum> playerOneDirections = new ArrayList<>();
        ArrayList<DirectionEnum> playerTwoDirections = new ArrayList<>();
        // starting viableDirections for playerOne
        playerOneDirections.add(DirectionEnum.SW);
        playerOneDirections.add(DirectionEnum.SE);
        setViableDirections(getPlayerOne(), playerOneDirections);
        // starting viableDirections for playerTwo
        playerTwoDirections.add(DirectionEnum.NW);
        playerTwoDirections.add(DirectionEnum.NE);
        setViableDirections(getPlayerTwo(), playerTwoDirections);
    }

    /**
     * Simple accessor for the viableDirections HashMap.
     * @return A player's viable directions, as a HashMap containing a Character and an ArrayList of DirectionEnums
     *
     * @pre None
     *
     * @post getViableDirections = viableDirections AND board = #board AND pieceCount = #pieceCount AND BOARD_DIMENSION
     * = #boardDimension AND playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing = #playerOneKing AND
     * playerTwoKing = #playerTwoKing AND viableDirections = #viableDirections
     */
    @Override
    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections() {
        return viableDirections;
    }

    /**
     * Simple accessor for the pieceCount HashMap.
     * @return A player's piece count, as a HashMap containing a Character and an Integer
     *
     * @pre None
     *
     * @post getPieceCounts = #pieceCount AND board = #board AND pieceCount = #pieceCount AND BOARD_DIMENSION =
     * #boardDimension AND playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing = #playerOneKing
     * AND playerTwoKing = #playerTwoKing AND viableDirections = #viableDirections
     */
    @Override
    public HashMap<Character, Integer> getPieceCounts() {
        return pieceCount;
    }

    /**
     * Sets a given BoardPosition to the board HashMap with the char given by player. Also, updates pieceCount.
     * Accepts two parameters.
     * @param pos The current position on the checkerboard, as a BoardPosition
     * @param pieceToPlace The letter that represents a player's name and pieces, as a char
     *
     * @pre pos != null AND player != null AND 0 < [position row number] < boardSize AND 0 < [position column number] <
     * boardSize
     *
     * @post placePiece = [a player's piece as a value in the given boardArray position] AND pieceCount = pieceCount + 1
     * AND board = #board AND pieceCount = #pieceCount AND BOARD_DIMENSION = #boardDimension AND playerOne = #playerOne
     * AND playerTwo = #playerTwo AND playerOneKing = #playerOneKing AND playerTwoKing = #playerTwoKing AND
     * viableDirections = #viableDirections
     */
    @Override
    public void placePiece(BoardPosition pos, char pieceToPlace) {
        char existingPiece = whatsAtPos(pos);
        int playerOnePieces = pieceCount.getOrDefault(getPlayerOne(), 0), playerTwoPieces = pieceCount.getOrDefault(getPlayerTwo(), 0);

        // removes any existing piece from the provided position before a piece gets placed there
        if (existingPiece != ' ') {
            removePiece(pos);
        }
        // adds the position to the ArrayList for the provided piece
        board.get(pieceToPlace).add(pos);
        // updates pieceCount for playerOne
        if (pieceToPlace == getPlayerOne() || pieceToPlace == getPlayerOneKing()) {
            playerOnePieces++;
            pieceCount.replace(getPlayerOne(), playerOnePieces);
        }
        // updates pieceCount for playerTwo
        else if (pieceToPlace == getPlayerTwo() || pieceToPlace == getPlayerTwoKing()) {
            playerTwoPieces++;
            pieceCount.replace(getPlayerTwo(), playerTwoPieces);
        }
    }

    /**
     * A standard accessor for the board Hashmap. Identifies the character located at a given BoardPosition.
     * Accepts one parameter.
     * @param pos The position on the checkerboard given by the boardArray, as a BoardPosition
     * @return What is located at the provided position, as a char
     *
     * @pre pos != null AND 0 < [position row number] < boardSize AND 0 < [position column number] < boardSize
     *
     * @post whatsAtPos = [what is located at pos in boardArray] AND board = #board AND pieceCount = #pieceCount AND
     * BOARD_DIMENSION = #boardDimension AND playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing =
     * #playerOneKing AND playerTwoKing = #playerTwoKing AND viableDirections = #viableDirections
     */
    @Override
    public char whatsAtPos(BoardPosition pos) {
        int row, column;
        char charInPos;

        // gets the row and column value from BoardPosition object and accesses the character located at that position
        row = pos.getRow();
        column = pos.getColumn();
        // identifies black tiles
        if ((row % EVEN_DIVISOR == 0 && column % EVEN_DIVISOR == 1) || (row % EVEN_DIVISOR == 1 && column % EVEN_DIVISOR == 0)) {
            charInPos = BLACK_TILE;
        }
        // loops through every position for every possible player piece
        else {
            charInPos = EMPTY_POS;
            if (getPlayerOne() != ICheckerBoard.NULL_POS) {
                for (BoardPosition p : board.get(playerOne)) {
                    if (pos.equals(p)) {
                        charInPos = playerOne;
                        break;
                    }
                }
            }
            if (getPlayerOneKing() != ICheckerBoard.NULL_POS) {
                for (BoardPosition p : board.get(playerOneKing)) {
                    if (pos.equals(p)) {
                        charInPos = playerOneKing;
                        break;
                    }
                }
            }
            if (getPlayerTwo() != ICheckerBoard.NULL_POS) {
                for (BoardPosition p : board.get(playerTwo)) {
                    if (pos.equals(p)) {
                        charInPos = playerTwo;
                        break;
                    }
                }
            }
            if (getPlayerTwoKing() != ICheckerBoard.NULL_POS) {
                for (BoardPosition p : board.get(playerTwoKing)) {
                    if (pos.equals(p)) {
                        charInPos = playerTwoKing;
                        break;
                    }
                }
            }
        }
        return charInPos;
    }

    /**
     * Removes an opponent's piece that has been jumped by a player's piece from the board. Accepts one parameter.
     * @param pos The board position of the piece the player wants to remove, as a BoardPosition
     *
     * @pre pos != null AND 0 < [pos row] < BOARD_DIMENSION AND 0 < [pos column] < BOARD_DIMENSION
     *
     * @post removePiece = [removes a player's piece that has been jumped from the board] AND pieceCount
     * = pieceCount - 1 AND board = #board AND pieceCount = #pieceCount AND BOARD_DIMENSION = #boardDimension AND
     * playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing = #playerOneKing AND playerTwoKing =
     * #playerTwoKing AND viableDirections = #viableDirections
     */
    @Override
    public void removePiece(BoardPosition pos) {
        char pieceToRemove = whatsAtPos(pos);
        ArrayList<BoardPosition> positionsForPiece = board.get(pieceToRemove);
        int pieces;

        // removes the position from the list for the provided piece
        positionsForPiece.remove(pos);
        // replaces the array of positions for the provided piece
        board.replace(pieceToRemove, positionsForPiece);
        // decreases the pieceCount for the player whose piece got jumped
        // case for playerOne
        if (pieceToRemove == getPlayerOne() || pieceToRemove == getPlayerOneKing()) {
            pieces = pieceCount.get(getPlayerOne());
            pieces--;
            pieceCount.replace(getPlayerOne(), pieces);
        }
        // case for playerTwo
        if (pieceToRemove == getPlayerTwo() || pieceToRemove == getPlayerTwoKing()) {
            pieces = pieceCount.get(getPlayerTwo());
            pieces--;
            pieceCount.replace(getPlayerTwo(), pieces);
        }
    }

    /**
     * Simple accessor for the row integer of the checkerboard's size.
     * @return The maximum row number of the checkerboard's size, as an int
     *
     * @pre None
     *
     * @post getRowNum = [BOARD_DIMENSION] AND board = #board AND pieceCount = #pieceCount AND BOARD_DIMENSION =
     * #boardDimension AND playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing = #playerOneKing AND
     * playerTwoKing = #playerTwoKing AND viableDirections = #viableDirections
     */
    @Override
    public int getRowNum() {
        return getBoardSize();
    }

    /**
     * Simple accessor for the column integer of the checkerboard's size.
     * @return The maximum column number of the checkerboard's size, as an int
     *
     * @pre None
     *
     * @post getColNum = [BOARD_DIMENSION] AND board = #board AND pieceCount = #pieceCount AND BOARD_DIMENSION =
     * #boardDimension AND playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing = #playerOneKing AND
     * playerTwoKing = #playerTwoKing AND viableDirections = #viableDirections
     */
    @Override
    public int getColNum() {
        return getBoardSize();
    }

    /**
     * Simple accessor for the board size of the game's checkerboard.
     * @return The checkerBoard's maximum dimension, as an int
     *
     * @pre None
     *
     * @post getBoardSize = BOARD_DIMENSION AND board = #board AND pieceCount = #pieceCount AND
     * BOARD_DIMENSION = #boardDimension AND playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing =
     * #playerOneKing AND playerTwoKing = #playerTwoKing AND viableDirections = #viableDirections
     */
    @Override
    public int getBoardSize() {
        return BOARD_DIMENSION;
    }

    /**
     * Simple accessor for getting player one's name/piece name.
     * @return Player one's name/piece name, as a char
     *
     * @pre None
     *
     * @post getPlayerOne = playerOne AND board = #board AND pieceCount = #pieceCount AND BOARD_DIMENSION =
     * #boardDimension AND playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing = #playerOneKing AND
     * playerTwoKing = #playerTwoKing AND viableDirections = #viableDirections
     */
    @Override
    public char getPlayerOne() {
        return playerOne;
    }

    /**
     * Simple setter for player one's name/piece name. Also, assigns a starting pieceCount to the provided piece.
     * Accepts one parameter.
     * @param piece The piece associated with player one, as a char
     *
     * @pre piece != null AND 'a' <= piece <= 'z'
     *
     * @post setPlayerOne = [playerOne gets the value of piece and a starting pieceCount is assigned to piece] AND
     * playerOne = #piece AND pieceCount = #0 AND board = #board AND BOARD_DIMENSION = #boardDimension AND playerTwo
     * = #playerTwo AND playerOneKing = #playerOneKing AND playerTwoKing = #playerTwoKing AND viableDirections =
     * #viableDirections
     */
    @Override
    public void setPlayerOne(char piece) {
        this.playerOne = Character.toLowerCase(piece);
        board.putIfAbsent(playerOne, new ArrayList<>());
        pieceCount.putIfAbsent(playerOne, 0);
    }

    /**
     * Simple accessor for getting player one's king piece name.
     * @return Player one's king piece name, as a char
     *
     * @pre None
     *
     * @post getPlayerOneKing = playerOneKing AND board = #board AND pieceCount = #pieceCount AND BOARD_DIMENSION =
     * #boardDimension AND playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing = #playerOneKing AND
     * playerTwoKing = #playerTwoKing AND viableDirections = #viableDirections
     */
    @Override
    public char getPlayerOneKing() {
        return playerOneKing;
    }

    /**
     * Simple setter for player one king's name/piece name. Also, assigns a starting pieceCount to the provided piece.
     * Accepts one parameter.
     * @param piece The piece associated with player one king, as a char
     *
     * @pre piece != null AND 'a' <= piece <= 'z'
     *
     * @post setPlayerOneKing = [playerOneKing gets the value of piece and a starting pieceCount is assigned to piece]
     * AND playerOneKing = #piece AND pieceCount = #0 AND board = #board AND BOARD_DIMENSION = #boardDimension AND
     * playerOne = #playerOne AND playerTwo = #playerTwo AND playerTwoKing = #playerTwoKing AND viableDirections =
     * #viableDirections
     */
    @Override
    public void setPlayerOneKing(char piece) {
        this.playerOneKing = Character.toUpperCase(piece);
        board.putIfAbsent(playerOneKing, new ArrayList<>());
        pieceCount.putIfAbsent(Character.toLowerCase(playerOneKing), 0);
    }

    /**
     * Simple accessor for getting player two's name/piece name.
     * @return Player two's name/piece name, as a char
     *
     * @pre None
     *
     * @post getPlayerTwo = playerTwo AND board = #board AND pieceCount = #pieceCount AND BOARD_DIMENSION =
     * #boardDimension AND playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing = #playerOneKing AND
     * playerTwoKing = #playerTwoKing AND viableDirections = #viableDirections
     */
    @Override
    public char getPlayerTwo() {
        return playerTwo;
    }

    /**
     * Simple setter for player two's name/piece name. Also, assigns a starting pieceCount to the provided piece.
     * Accepts one parameter.
     * @param piece The piece associated with player two, as a char
     *
     * @pre piece != null AND 'a' <= piece <= 'z'
     *
     * @post setPlayerTwo = [playerTwo gets the value of piece and a starting pieceCount is assigned to piece] AND
     * playerTwo = #piece AND pieceCount = #0 AND board = #board AND BOARD_DIMENSION = #boardDimension AND playerOne
     * = #playerOne AND playerOneKing = #playerOneKing AND playerTwoKing = #playerTwoKing AND viableDirections =
     * #viableDirections
     */
    @Override
    public void setPlayerTwo(char piece) {
        this.playerTwo = Character.toLowerCase(piece);
        board.putIfAbsent(playerTwo, new ArrayList<>());
        pieceCount.putIfAbsent(playerTwo, 0);
    }

    /**
     * Simple accessor for getting player two's king piece name.
     * @return Player two's king piece name, as a char
     *
     * @pre None
     *
     * @post getPlayerTwoKing = playerTwoKing AND board = #board AND pieceCount = #pieceCount AND BOARD_DIMENSION =
     * #boardDimension AND playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing = #playerOneKing AND
     * playerTwoKing = #playerTwoKing AND viableDirections = #viableDirections
     */
    @Override
    public char getPlayerTwoKing() {
        return playerTwoKing;
    }

    /**
     * Simple setter for player two king's name/piece name. Also, assigns a starting pieceCount to the provided piece.
     * Accepts one parameter.
     * @param piece The piece associated with player one king, as a char
     *
     * @pre piece != null AND 'a' <= piece <= 'z'
     *
     * @post setPlayerTwoKing = [playerTwoKing gets the value of piece and a starting pieceCount is assigned to piece]
     * AND playerTwoKing = #piece AND pieceCount = #0 AND board = #board AND BOARD_DIMENSION = #boardDimension AND
     * playerOne = #playerOne AND playerTwo = #playerTwo AND playerOneKing = #playerOneKing AND viableDirections =
     * #viableDirections
     */
    @Override
    public void setPlayerTwoKing(char piece) {
        this.playerTwoKing = Character.toUpperCase(piece);
        board.putIfAbsent(playerTwoKing, new ArrayList<>());
        pieceCount.putIfAbsent(Character.toLowerCase(playerTwoKing), 0);
    }

    /**
     * Sets a player's viableDirections. This method adds directions to the viableDirections HashMap by determining
     * valid directions a player could move in given a board position. Accepts two parameters.
     * @param player The character that represents a player, as a char
     * @param possibleDirections The list of directions a player's piece can move, as an ArrayList<DirectionEnum>
     *
     * @pre player != null AND possibleDirections != null
     *
     * @post setViableDirections = [player and ArrayList get updated in viableDirections] AND board = #board AND
     * pieceCount = #pieceCount AND BOARD_DIMENSION = #boardDimension AND playerOne = #playerOne AND playerTwo =
     * #playerTwo AND playerOneKing = #playerOneKing AND playerTwoKing = #playerTwoKing AND viableDirections =
     * #viableDirections
     */
    @Override
    public void setViableDirections(char player, ArrayList<DirectionEnum> possibleDirections) {
        // when a player is represented by a king piece, the player's name gets reassigned to its lowercase equivalent
        if (player == getPlayerOneKing()) {
            player = getPlayerOne();
        }
        if (player == getPlayerTwoKing()) {
            player = getPlayerTwo();
        }
        // updates viableDirections for a player, and puts a player's directions in viableDirections if they're not there
        if (viableDirections.containsKey(player)) {
            viableDirections.replace(player, possibleDirections);
        }
        else {
            viableDirections.put(player, possibleDirections);
        }
    }
}

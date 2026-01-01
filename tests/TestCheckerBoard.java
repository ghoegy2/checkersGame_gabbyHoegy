package cpsc2150.extendedCheckers.tests;
import cpsc2150.extendedCheckers.models.BoardPosition;
import cpsc2150.extendedCheckers.models.CheckerBoard;
import cpsc2150.extendedCheckers.models.ICheckerBoard;
import cpsc2150.extendedCheckers.util.DirectionEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * TestCheckerBoard contains test case functions to test some of the pivotal functions contained in the CheckerBoard
 * class. The functions it tests include CheckerBoard(int), whatsAtPos(BoardPosition), placePiece(BoardPosition, char),
 * getPieceCounts(void), getViableDirections(void), getRowNum(void), getColNum(void), checkPlayerWin(Character),
 * crownPiece(BoardPosition), movePiece(BoardPosition, DirectionEnum), jumpPiece(BoardPosition, DirectionEnum),
 * scanSurroundingPositions(BoardPosition), and getDirection(DirectionEnum). TestCheckerBoard contains a function
 * that makes accessing the CheckerBoard constructor easier, and a functions that turns the board array into a String.
 *
 * @invariant [player pieces cannot occupy the positions of black tiles] AND [a player's number of pieces can
 * never be negative] AND [pieces cannot go past the boardSize row and columns of the board]
 */
public class TestCheckerBoard {
    /**
     * Accesses and returns the CheckerBoard constructor with the provided dimension size as input. Accepts one
     * parameter.
     * @param aDimension The size of the board, as an int
     * @return The CheckerBoard constructor given a dimension size, which creates a new checkerboard, as a CheckerBoard
     *
     * @pre aDimension != null AND aDimension = [8, 10, 12, 14, or 16]
     *
     * @post makeBoard = [a new board with a board size of the provided dimension]
     */
    private ICheckerBoard makeBoard(int aDimension) {
        return new CheckerBoard(aDimension);
    }

    /**
     * Takes a 2D char array of the checkerboard and turns it into a string.
     * @param charArray The array of characters that makes up the checkerboard, as a 2D character array
     * @return A string representation of the 2D char array checkerboard, as a String
     *
     * @pre charArrayToBoardString != null AND charArray.length = [8, 10, 12, 14, or 16]
     *
     * @post charArrayToBoardString = [A string representation of the 2D char array checkerboard]
     */
    private String charArrayToBoardString(char[][] charArray) {
        StringBuilder result = new StringBuilder();
        int boardSize = charArray.length;
        int row, column;
        char boardChar;

        result.append("|  |");
        for (column = 0; column < boardSize; column++) {
            result.append(String.format("%2d|", column));
        }
        result.append("\n");
        for (row = 0; row < boardSize; row++) {
            result.append(String.format("|%-2d|", row));
            for (column = 0; column < boardSize; column++) {
                boardChar = charArray[row][column];
                result.append(boardChar).append(" |");
            }
            result.append("\n");
        }
        return result.toString();
    }

    // CheckerBoard(int) test #1 - boundary test - 8 is the minimum board size dimension that can be input
    @Test
    public void testCheckerBoard_Integer_8() {
        char[][] expected = {
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
        };
        assertEquals(charArrayToBoardString(expected), makeBoard(8).toString());
    }

    // CheckerBoard(int) test #2 - boundary test - 16 is the maximum board size dimension that can be input
    @Test
    public void testCheckerBoard_Integer_16() {
        char[][] expected = {
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
        };
        assertEquals(charArrayToBoardString(expected), makeBoard(16).toString());
    }

    // CheckerBoard(int) test #3 - routine test - 12 is a common board size dimension that can be input
    @Test
    public void testCheckerBoard_Integer_12() {
        char[][] expected = {
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
        };
        assertEquals(charArrayToBoardString(expected), makeBoard(12).toString());
    }

    // whatsAtPos(BoardPosition) test #1 - boundary test - (0,0) is the upper left position of the board when board size is 8
    @Test
    public void testWhatsAtPos_BoardPosition_0_0() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(0, 0);

        assertEquals(' ', board.whatsAtPos(pos));
    }

    // whatsAtPos(BoardPosition) test #2 - boundary test - (0,7) is the upper right position of the board when board size is 8
    @Test
    public void testWhatsAtPos_BoardPosition_0_7() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(0, 7);

        assertEquals('*', board.whatsAtPos(pos));
    }

    // whatsAtPos(BoardPosition) test #3 - boundary test - (7,0) is the lower left position of the board when board size is 8
    @Test
    public void testWhatsAtPos_BoardPosition_7_0() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(7, 0);

        assertEquals('*', board.whatsAtPos(pos));
    }

    // whatsAtPos(BoardPosition) test #4 - boundary test - (7,7) is the lower right position of the board when board size is 8
    @Test
    public void testWhatsAtPos_BoardPosition_7_7() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(7, 7);

        assertEquals(' ', board.whatsAtPos(pos));
    }

    // whatsAtPos(BoardPosition) test #5 - routine test - (4,4) is a common position on the board when board size is 8
    @Test
    public void testWhatsAtPos_BoardPosition_4_4() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(4, 4);

        assertEquals(' ', board.whatsAtPos(pos));
    }

    // placePiece(BoardPosition, char) test #1 - boundary test - (0,0) is the upper left position of the board when board size is 8
    @Test
    public void testPlacePiece_BoardPosition_0_0_char_x() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(0, 0);
        char[][] expected = {
                {'x', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
        };

        board.placePiece(pos, 'x');
        assertEquals(charArrayToBoardString(expected), board.toString());
    }

    // placePiece(BoardPosition, char) test #2 - boundary test - (0,7) is the upper right position of the board when board size is 8
    @Test
    public void testPlacePiece_BoardPosition_0_7_char_x() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(0, 7);
        char[][] expected = {
                {' ', '*', ' ', '*', ' ', '*', ' ', 'x'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
        };

        board.placePiece(pos, 'x');
        assertEquals(charArrayToBoardString(expected), board.toString());
    }

    // placePiece(BoardPosition, char) test #3 - boundary test - (7,0) is the lower left position of the board when board size is 8
    @Test
    public void testPlacePiece_BoardPosition_7_0_char_o() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(7, 0);
        char[][] expected = {
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'o', ' ', '*', ' ', '*', ' ', '*', ' '},
        };

        board.placePiece(pos, 'o');
        assertEquals(charArrayToBoardString(expected), board.toString());
    }

    // placePiece(BoardPosition, char) test #4 - boundary test - (7,7) is the lower right position of the board when board size is 8
    @Test
    public void testPlacePiece_BoardPosition_7_7_char_o() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(7, 7);
        char[][] expected = {
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', 'o'},
        };

        board.placePiece(pos, 'o');
        assertEquals(charArrayToBoardString(expected), board.toString());
    }

    // placePiece(BoardPosition, char) test #5 - routine test - (4,4) is a common position on the board when board size is 8
    @Test
    public void testPlacePiece_BoardPosition_4_4_char_o() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(4, 4);
        char[][] expected = {
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', 'o', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
        };

        board.placePiece(pos, 'o');
        assertEquals(charArrayToBoardString(expected), board.toString());
    }

    // getPieceCounts(void) test #1 - routine test - 12 is the number of starting pieces for both players when board size is 8
    @Test
    public void testGetPieceCounts_void_forPlayers_x_o() {
        ICheckerBoard board = makeBoard(8);
        board.putPlayersOnNewBoard('x', 'o');
        int actualPlayerOnePieces = board.getPieceCounts().get('x');
        int actualPlayerTwoPieces = board.getPieceCounts().get('o');

        assertEquals(12, actualPlayerOnePieces);
        assertEquals(12, actualPlayerTwoPieces);
    }

    // getViableDirections(void) test #1 - routine test - SW and SE are the starting directions for playerOne, and NW and NE are the starting directions for playerTwo
    @Test
    public void testGetViableDirections_void_forPlayers_x_o() {
        ICheckerBoard board = makeBoard(8);
        board.putPlayersOnNewBoard('x', 'o');
        HashMap<Character, ArrayList<DirectionEnum>> viableDirections = board.getViableDirections();
        ArrayList<DirectionEnum> playerOneDirections = new ArrayList<>(), playerTwoDirections = new ArrayList<>();

        playerOneDirections.add(DirectionEnum.SW);
        playerOneDirections.add(DirectionEnum.SE);
        playerTwoDirections.add(DirectionEnum.NW);
        playerTwoDirections.add(DirectionEnum.NE);

        assertEquals(playerOneDirections, viableDirections.get('x'));
        assertEquals(playerTwoDirections, viableDirections.get('o'));
    }

    // getRowNum(void) test #1 - routine test - 8 is the number of rows on the checkerboard when board size is 8
    @Test
    public void testGetRowNum_void_boardSize_8() {
        ICheckerBoard board = makeBoard(8);
        int actual = board.getRowNum();

        assertEquals(8, actual);
    }

    // getColNum(void) test #1 - routine test - 8 is the number of columns on the checkerboard when board size is 8
    @Test
    public void testGetColNum_void_boardSize_8() {
        ICheckerBoard board = makeBoard(8);
        int actual = board.getColNum();

        assertEquals(8, actual);
    }

    // checkPlayerWin(Character) test #1 - routine test - when playerTwo has no pieces on the board and playerOne has pieces on the board, playerOne wins
    @Test
    public void testCheckPlayerWin_Character_x_winningScenario() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(4, 4);
        board.setPlayerOne('x');
        board.setPlayerTwo('o');
        board.placePiece(pos, 'x');

        boolean expectedPlayerOneWin = board.checkPlayerWin(board.getPlayerOne());

        assertEquals(true, expectedPlayerOneWin);
    }

    // checkPlayerWin(Character) test #2 - routine test - when playerOne and playerTwo still have pieces on the board, nobody wins the game yet
    @Test
    public void testCheckPlayerWin_Character_x_noWinningScenario() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition playerOnePos = new BoardPosition(4, 4);
        BoardPosition playerTwoPos = new BoardPosition(4, 2);
        board.setPlayerOne('x');
        board.setPlayerTwo('o');
        board.placePiece(playerOnePos, 'x');
        board.placePiece(playerTwoPos, 'o');

        boolean expectedPlayerOneWin = board.checkPlayerWin(board.getPlayerOne());

        assertEquals(false, expectedPlayerOneWin);
    }

    // crownPiece(BoardPosition) test #1 - boundary test - (0,0) is the upper left position of the board when board size is 8. 'o' becomes 'O'
    @Test
    public void testCrownPiece_BoardPosition_0_0() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(0, 0);

        board.setPlayerTwo('o');
        board.setPlayerTwoKing('O');
        board.placePiece(pos, board.getPlayerTwo());
        board.crownPiece(pos);

        char[][] expected = {
                {'O', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
        };

        assertEquals(charArrayToBoardString(expected), board.toString());
    }

    // crownPiece(BoardPosition) test #2 - boundary test - (7,7) is the lower right position of the board when board size is 8. 'x' becomes 'X'
    @Test
    public void testCrownPiece_BoardPosition_7_7() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(7, 7);

        board.setPlayerOne('x');
        board.setPlayerOneKing('X');
        board.placePiece(pos, board.getPlayerOne());
        board.crownPiece(pos);

        char[][] expected = {
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', 'X'},
        };

        assertEquals(charArrayToBoardString(expected), board.toString());
    }

    // crownPiece(BoardPosition) test #3 - routine test - (0,4) is a common position on the board when board size is 8. 'o' becomes 'O'
    @Test
    public void testCrownPiece_BoardPosition_0_4() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition pos = new BoardPosition(0, 4);

        board.setPlayerTwo('o');
        board.setPlayerTwoKing('O');
        board.placePiece(pos, board.getPlayerTwo());
        board.crownPiece(pos);

        char[][] expected = {
                {' ', '*', ' ', '*', 'O', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
        };

        assertEquals(charArrayToBoardString(expected), board.toString());
    }

    // movePiece(BoardPosition, DirectionEnum) test #1 - boundary test - (0,0) is the upper left position of the board when board size is 8. 'x' moves SE from (0,0) to (1,1)
    @Test
    public void testMovePiece_BoardPosition_0_0_DirectionEnum_SE() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition startingPos = new BoardPosition(0, 0);
        BoardPosition expectedPos = new BoardPosition(1, 1);

        board.setPlayerOne('x');
        board.placePiece(startingPos, board.getPlayerOne());
        BoardPosition actualPos = board.movePiece(startingPos, DirectionEnum.SE);

        assertEquals(expectedPos, actualPos);
    }

    // movePiece(BoardPosition, DirectionEnum) test #2 - boundary test - (7,7) is the lower right position of the board when board size is 8. 'o' moves NW from (7,7) to (6,6)
    @Test
    public void testMovePiece_BoardPosition_7_7_DirectionEnum_NW() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition startingPos = new BoardPosition(7, 7);
        BoardPosition expectedPos = new BoardPosition(6, 6);

        board.setPlayerTwo('o');
        board.placePiece(startingPos, board.getPlayerTwo());
        BoardPosition actualPos = board.movePiece(startingPos, DirectionEnum.NW);

        assertEquals(expectedPos, actualPos);
    }

    // movePiece(BoardPosition, DirectionEnum) test #3 - routine test - (5,3) is a common position on the board when board size is 8. 'o' moves NE from (5,3) to (4,4)
    @Test
    public void testMovePiece_BoardPosition_5_3_DirectionEnum_NE() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition startingPos = new BoardPosition(5, 3);
        BoardPosition expectedPos = new BoardPosition(4, 4);

        board.setPlayerTwo('o');
        board.placePiece(startingPos, board.getPlayerTwo());
        BoardPosition actualPos = board.movePiece(startingPos, DirectionEnum.NE);

        assertEquals(expectedPos, actualPos);
    }

    // jumpPiece(BoardPosition, DirectionEnum) test #1 - boundary test - (0,0) is the upper left position of the board when board size is 8. 'x' jumps SE from (0,0) to (2,2)
    @Test
    public void testJumpPiece_BoardPosition_0_0_DirectionEnum_SE() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition startingPos = new BoardPosition(0, 0);
        BoardPosition expectedPos = new BoardPosition(2, 2);
        BoardPosition playerTwoPos = new BoardPosition(1, 1);

        board.setPlayerOne('x');
        board.placePiece(startingPos, board.getPlayerOne());
        board.setPlayerTwo('o');
        board.placePiece(playerTwoPos, board.getPlayerTwo());
        BoardPosition actualPos = board.jumpPiece(startingPos, DirectionEnum.SE);

        assertEquals(expectedPos, actualPos);
    }

    // jumpPiece(BoardPosition, DirectionEnum) test #2 - boundary test - (7,7) is the lower right position of the board when board size is 8. 'o' jumps NW from (7,7) to (5,5)
    @Test
    public void testJumpPiece_BoardPosition_7_7_DirectionEnum_NW() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition startingPos = new BoardPosition(7, 7);
        BoardPosition expectedPos = new BoardPosition(5, 5);
        BoardPosition playerOnePos = new BoardPosition(6, 6);

        board.setPlayerTwo('o');
        board.placePiece(startingPos, board.getPlayerTwo());
        board.setPlayerOne('x');
        board.placePiece(playerOnePos, board.getPlayerOne());
        BoardPosition actualPos = board.jumpPiece(startingPos, DirectionEnum.NW);

        assertEquals(expectedPos, actualPos);
    }

    // jumpPiece(BoardPosition, DirectionEnum) test #3 - routine test - (5,3) is a common position on the board when board size is 8. 'o' jumps NE from (5,3) to (3,5)
    @Test
    public void testJumpPiece_BoardPosition_5_3_DirectionEnum_NE() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition startingPos = new BoardPosition(5, 3);
        BoardPosition expectedPos = new BoardPosition(3, 5);
        BoardPosition playerOnePos = new BoardPosition(4, 4);

        board.setPlayerTwo('o');
        board.placePiece(startingPos, board.getPlayerTwo());
        board.setPlayerOne('x');
        board.placePiece(playerOnePos, board.getPlayerOne());
        BoardPosition actualPos = board.jumpPiece(startingPos, DirectionEnum.NE);

        assertEquals(expectedPos, actualPos);
    }

    // scanSurroundingPositions(BoardPosition) test #1 - boundary test - (0,0) is the upper left position of the board when board size is 8.
    @Test
    public void testScanSurroundingPositions_BoardPosition_0_0() {
        ICheckerBoard board = makeBoard(8);

        board.setPlayerOne('x');
        board.setPlayerTwo('o');
        board.putPlayersOnNewBoard(board.getPlayerOne(), board.getPlayerTwo());
        BoardPosition startingPos = new BoardPosition(0, 0);
        HashMap<DirectionEnum, Character> actualPos = board.scanSurroundingPositions(startingPos);

        HashMap<DirectionEnum, Character> expectedPos = new HashMap<>();
        expectedPos.put(DirectionEnum.SE, board.getPlayerOne());

        assertEquals(expectedPos, actualPos);
    }

    // scanSurroundingPositions(BoardPosition) test #2 - boundary test - (7,7) is the lower right position of the board when board size is 8.
    @Test
    public void testScanSurroundingPositions_BoardPosition_7_7() {
        ICheckerBoard board = makeBoard(8);

        board.setPlayerOne('x');
        board.setPlayerTwo('o');
        board.putPlayersOnNewBoard(board.getPlayerOne(), board.getPlayerTwo());
        BoardPosition startingPos = new BoardPosition(7, 7);
        HashMap<DirectionEnum, Character> actualPos = board.scanSurroundingPositions(startingPos);

        HashMap<DirectionEnum, Character> expectedPos = new HashMap<>();
        expectedPos.put(DirectionEnum.NW, board.getPlayerTwo());

        assertEquals(expectedPos, actualPos);
    }

    // scanSurroundingPositions(BoardPosition) test #3 - routine test - (2,4) is a common position on the board when board size is 8.
    @Test
    public void testScanSurroundingPositions_BoardPosition_2_4() {
        ICheckerBoard board = makeBoard(8);

        board.setPlayerOne('x');
        board.setPlayerTwo('o');
        board.putPlayersOnNewBoard(board.getPlayerOne(), board.getPlayerTwo());
        BoardPosition startingPos = new BoardPosition(2, 4);
        HashMap<DirectionEnum, Character> actualPos = board.scanSurroundingPositions(startingPos);

        HashMap<DirectionEnum, Character> expectedPos = new HashMap<>();
        expectedPos.put(DirectionEnum.NE, board.getPlayerOne());
        expectedPos.put(DirectionEnum.NW, board.getPlayerOne());
        expectedPos.put(DirectionEnum.SE, board.EMPTY_POS);
        expectedPos.put(DirectionEnum.SW, board.EMPTY_POS);

        assertEquals(expectedPos, actualPos);
    }

    // getDirection(DirectionEnum) test #1 - routine test - SW is one of four directions that can be obtained
    @Test
    public void testGetDirection_DirectionEnum_SW() {
        ICheckerBoard board = makeBoard(8);
        BoardPosition expectedPos = new BoardPosition(1, -1);
        BoardPosition actualPos = ICheckerBoard.getDirection(DirectionEnum.SW);

        assertEquals(expectedPos, actualPos);
    }
}


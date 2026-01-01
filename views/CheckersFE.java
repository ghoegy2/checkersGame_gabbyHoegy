package cpsc2150.extendedCheckers.views;
import cpsc2150.extendedCheckers.models.BoardPosition;
import cpsc2150.extendedCheckers.models.CheckerBoard;
import cpsc2150.extendedCheckers.models.CheckerBoardMem;
import cpsc2150.extendedCheckers.models.ICheckerBoard;
import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * CheckersFE is an object that stores information pertaining to the flow of a checkers game. CheckersFE.java
 * contains a main function for interacting with the user via the terminal and making calls to other class
 * objects/functions, so that the game is playable.
 *
 * @invariant [args from compilation are always obtained via the terminal]
 */
public class CheckersFE {
    // represents the integer piece numbers for player one and player two
    private static final int PLAYER_ONE_NUM = 1;
    private static final int PLAYER_TWO_NUM = 2;
    // represents the piece chars for player one and player two
    private static char playerOne, playerTwo;

    /**
     * Simple accessor for the char associated with player one. This char represents player one's piece names and player one's name.
     * @return The name that represents player one on the checkerboard, as a char
     *
     * @pre None
     *
     * @post getPlayerOne = playerOne AND PLAYER_ONE_NUM = #PLAYER_ONE_NUM AND PLAYER_TWO_NUM = #PLAYER_TWO_NUM AND
     * playerOne = #playerOne AND playerTwo = #playerTwo
     */
    public static char getPlayerOne() {
        return playerOne;
    }

    /**
     * Simple accessor for the char associated with player two. This char represents player two's piece names and player two's name.
     * @return The name that represents player two on the checkerboard, as a char
     *
     * @pre None
     *
     * @post getPlayerTwo = playerTwo AND PLAYER_ONE_NUM = #PLAYER_ONE_NUM AND PLAYER_TWO_NUM = #PLAYER_TWO_NUM AND
     * playerOne = #playerOne AND playerTwo = #playerTwo
     */
    public static char getPlayerTwo() {
        return playerTwo;
    }

    // used to check whether the number of turn counts is even or odd
    public static final int EVEN_DIVISOR = 2;
    // the number of chars in a string for a piece can only be one
    public static final int PLAYER_PIECE_LENGTH = 1;
    // represents the boundary of piece chars that players can choose to play as
    public static final char PIECE_LETTER_MIN = 'a';
    public static final char PIECE_LETTER_MAX = 'z';
    // represents the chars a user can choose from when determining whether to play a fast game or a memory efficient game
    public static final char GAME_FAST = 'f';
    public static final char GAME_MEM_EFFICIENT = 'm';
    // represents the minimum and maximum board sizes a user can choose to play on
    public static final int BOARD_SIZE_MIN = 8;
    public static final int BOARD_SIZE_MAX = 16;

    /**
     * Checks whether a user's input position numbers for row and column are numbers between 0 and
     * checkerBoard.getBoardSize(). Accepts two parameters.
     * @param aRow The row of a desired board position, as an int
     * @param aCol The column of a desired board position, as an int
     * @param checkerBoard The object that represents the checkerboard, as an ICheckerBoard
     * @return True when the input variables are between 0 and checkerBoard.getBoardSize() and false otherwise, as
     * a boolean
     *
     * @pre aRow != null AND aCol != null AND checkerBoard != null
     *
     * @post validInputPos = [True when the input variables are between 0 and checkerBoard.getBoardSize() and false
     * otherwise] AND PLAYER_ONE_NUM = #PLAYER_ONE_NUM AND PLAYER_TWO_NUM = #PLAYER_TWO_NUM AND playerOne = #playerOne
     * AND playerTwo = #playerTwo
     */
    private static boolean validInputPos(int aRow, int aCol, ICheckerBoard checkerBoard) {
        boolean valid = true;
        // row numbers are invalid when they are not between 0 and the maximum board size
        if (aRow < 0 || aRow > checkerBoard.getBoardSize()) {
            valid = false;
        }
        // column numbers are invalid when they are not between 0 and the maximum board size
        else if (aCol < 0 || aCol > checkerBoard.getBoardSize()) {
            valid = false;
        }
        return valid;
    }

    /**
     * Checks whether a user's input position yields a valid character for a specific player. Characters are valid for
     * player one when they are not a black tile, player two's pieces, or an empty position. Characters are valid for
     * player two when they are not a black tile, player one's pieces, or an empty position. Accepts three parameters.
     * @param aPlayerName The letter associated with a player's name and piece names, as a char
     * @param pos The board position that holds the character that is being analyzed, as a BoardPosition
     * @param currentBoard The current checkerboard, as an ICheckerBoard
     * @return True when the player is player one and the board position does not equal a black tile, player two's
     * pieces, or an empty position, OR, true when the player is player two and the board position does not equal a
     * black tile, player one's pieces, or an empty position
     *
     * @pre aPlayerName != null AND aPlayerName = player one or player two AND pos != null AND currentBoard != null
     *
     * @post validInputPosChar = [True when the player is player one and the board position does not equal a black tile,
     * player two's pieces, or an empty position, OR, true when the player is player two and the board position does
     * not equal a black tile, player one's pieces, or an empty position] AND PLAYER_ONE_NUM = #PLAYER_ONE_NUM AND
     * PLAYER_TWO_NUM = #PLAYER_TWO_NUM AND playerOne = #playerOne AND playerTwo = #playerTwo
     */
    private static boolean validInputPosChar(char aPlayerName, BoardPosition pos, ICheckerBoard currentBoard) {
        boolean valid = true;
        // an input char is invalid for player one when it is a black tile, player two's piece (normal or king piece), or an empty position. An input char is valid for player one when it's player one's piece
        if (aPlayerName == currentBoard.getPlayerOne()) {
            if (currentBoard.whatsAtPos(pos) == CheckerBoard.BLACK_TILE || currentBoard.whatsAtPos(pos) == currentBoard.getPlayerTwo() || currentBoard.whatsAtPos(pos) == CheckerBoard.EMPTY_POS || currentBoard.whatsAtPos(pos) == currentBoard.getPlayerTwoKing()) {
                valid = false;
            }
            else if (currentBoard.whatsAtPos(pos) == currentBoard.getPlayerOne() || currentBoard.whatsAtPos(pos) == currentBoard.getPlayerOneKing()) {
                valid = true;
            }
        }
        // an input char is invalid for player two when it is a black tile, player one's piece (normal or king piece), or an empty position. An input char is valid for player two when it's player two's piece
        else if (aPlayerName == currentBoard.getPlayerTwo()) {
            if (currentBoard.whatsAtPos(pos) == CheckerBoard.BLACK_TILE || currentBoard.whatsAtPos(pos) == currentBoard.getPlayerOne() || currentBoard.whatsAtPos(pos) == CheckerBoard.EMPTY_POS || currentBoard.whatsAtPos(pos) == currentBoard.getPlayerOneKing()) {
                valid = false;
            }
            else if (currentBoard.whatsAtPos(pos) == currentBoard.getPlayerTwo() || currentBoard.whatsAtPos(pos) == currentBoard.getPlayerTwoKing()) {
                valid = true;
            }
        }
        return valid;
    }

    /**
     * Prompts a player to input the char piece they want to play and represent their name with during the game. Also,
     * validates the player's input. Accepts two parameters.
     * @param scan The user's input, as a Scanner
     * @param playerNum The number representation of the player being prompted, as an int
     * @return The piece the player chose to play and represent their name with for the game, as a char
     *
     * @pre scan != null AND playerNum = 1 or playerNum = 2
     *
     * @post getPlayerPiece = [The piece the player chose to play and represent their name with for the game, as a
     * char] AND PLAYER_ONE_NUM = #PLAYER_ONE_NUM AND PLAYER_TWO_NUM = #PLAYER_TWO_NUM AND playerOne = #playerOne
     * AND playerTwo = #playerTwo
     */
    private static char getPlayerPiece(Scanner scan, int playerNum) {
        boolean pieceIsValid = false;
        String playerInput;
        char piece = ' ';

        // prompts a player to input a char they want to play with
        System.out.printf("Player %d, enter your piece:\n", playerNum);
        while (pieceIsValid == false) {
            playerInput = scan.nextLine();
            // when a player inputs one char and it falls between the piece letter minimum and maximum values, it is valid and gets initialized
            if (playerInput.length() == PLAYER_PIECE_LENGTH && playerInput.charAt(0) >= PIECE_LETTER_MIN && playerInput.charAt(0) <= PIECE_LETTER_MAX) {
                piece = playerInput.charAt(0);
                pieceIsValid = true;
                break;
            }
            // re-prompts a player to input a char they want to play with while the input is invalid
            else {
                System.out.println("Please enter only a single lowercase letter character\n");
            }
        }
        return piece;
    }

    /**
     * Prompts a user to input whether they want to play a fast game or a memory efficient game. Also, validates the
     * user's input. Accepts one parameter.
     * @param scan The user's input, as a Scanner
     * @return The type of game the user chose to play (a fast or a memory efficient game), as a char
     *
     * @pre scan != null
     *
     * @post getGameFastOrMemEfficient = [The type of game the user chose to play (a fast or a memory efficient game),
     * as a char] AND PLAYER_ONE_NUM = #PLAYER_ONE_NUM AND PLAYER_TWO_NUM = #PLAYER_TWO_NUM AND playerOne = #playerOne
     * AND playerTwo = #playerTwo
     */
    private static char getGameFastOrMemEfficient(Scanner scan) {
        boolean gameTypeIsValid = false;
        String playerInput;
        char gameType = ' ';

        // prompts a player to input a char that corresponds with choosing to play a fast game or a memory efficient game
        System.out.println("Do you want a fast game (F/f) or a memory efficient game (M/m)?");
        while (gameTypeIsValid == false) {
            playerInput = scan.nextLine();
            // when a player inputs a char that corresponds with a fast game or a memory efficient game, it is valid and gets initialized
            if (playerInput.equalsIgnoreCase(Character.toString(GAME_FAST)) || playerInput.equalsIgnoreCase(Character.toString(GAME_MEM_EFFICIENT))) {
                gameType = playerInput.charAt(0);
                gameTypeIsValid = true;
            }
            // re-prompts a player to input a char that corresponds with a fast game or a memory efficient game while the input is invalid
            else {
                System.out.println("Do you want a fast game (F/f) or a memory efficient game (M/m)?");
            }
        }
        return gameType;
    }

    /**
     * Prompts a user to input a desired board size for the checkerboard they will play the game on. Also, validates the
     * user's input. Accepts one parameter.
     * @param scan The user's input, as a Scanner
     * @return The numeric dimension associated with the desired input board size, as an int
     *
     * @pre scan != null
     *
     * @post getBoardSize = [The numeric dimension associated with the desired input board size, as an int] AND
     * PLAYER_ONE_NUM = #PLAYER_ONE_NUM AND PLAYER_TWO_NUM = #PLAYER_TWO_NUM AND playerOne = #playerOne AND playerTwo
     * = #playerTwo
     */
    private static int getBoardSize(Scanner scan) {
        boolean boardSizeIsValid = false;
        int playerInput = 0;

        // prompts a player to input a desired board size for the game
        System.out.println("How big should the board be? It can be 8x8, 10x10, 12x12, 14x14, or 16x16. Enter one number:");
        while (boardSizeIsValid == false) {
            playerInput = scan.nextInt();
            // when a player inputs an int that is between the minimum and maximum board sizes available and is an even number, it is valid and gets initialized
            if (playerInput >= BOARD_SIZE_MIN && playerInput <= BOARD_SIZE_MAX && playerInput % EVEN_DIVISOR == 0) {
                scan.nextLine();
                boardSizeIsValid = true;
            }
            // re-prompts a player to input an int that matches an available board size option while the input is invalid
            else {
                System.out.println("How big should the board be? It can be 8x8, 10x10, 12x12, 14x14, or 16x16. Enter one number:");
            }
        }
        return playerInput;
    }

    /**
     * Main function for the checkers game. This is where print statements occur and where user input is obtained. This
     * is also where functions from classes are called to perform the game actions/procedures. Accepts one parameter.
     * @param args Takes in command line arguments, as an array of Strings
     *
     * @pre None
     *
     * @post main = [prints user prompts, turn summaries, visual representations of the board and pieces, and
     * final results] AND PLAYER_ONE_NUM = #PLAYER_ONE_NUM AND PLAYER_TWO_NUM = #PLAYER_TWO_NUM AND playerOne
     * = #playerOne AND playerTwo = #playerTwo
     */
    public static void main(String[] args) {
        boolean gameWon, inputDirectionValid, keepPlaying = true;
        int turnCount = 0, boardSize;
        Scanner scan = new Scanner(System.in);
        ArrayList<DirectionEnum> possibleDirections;
        HashMap<Character, ArrayList<DirectionEnum>> viableDirections;
        HashMap<DirectionEnum, Character> surroundingPositions;
        String keepPlayingResponse;
        char playerOneChosenPiece, playerTwoChosenPiece, typeOfGameResponse;

        // gets input from the user regarding desired player piece chars, game type (fast or memory efficient), and board size
        System.out.println("Welcome to Checkers!");
        playerOneChosenPiece = getPlayerPiece(scan, PLAYER_ONE_NUM);
        playerOne = playerOneChosenPiece;
        // checks that the player piece characters are not the same. While they are the same, player two is re-prompted to enter a different piece
        do {
            playerTwoChosenPiece = getPlayerPiece(scan, PLAYER_TWO_NUM);
            playerTwo = playerTwoChosenPiece;
        } while (playerOneChosenPiece == playerTwoChosenPiece);
        typeOfGameResponse = getGameFastOrMemEfficient(scan);
        boardSize = getBoardSize(scan);

        // creates the checkerboard with the user's provided board size
        ICheckerBoard checkerBoard;
        if (typeOfGameResponse == GAME_FAST) {
            // when user's the desired game type is fast, a CheckerBoard object is used
            checkerBoard = new CheckerBoard(boardSize);
        }
        else {
            // when user's the desired game type is memory efficient, a CheckerBoardMem object is used
            checkerBoard = new CheckerBoardMem(boardSize);
        }
        // fills in the checkerboard
        checkerBoard.putPlayersOnNewBoard(playerOneChosenPiece, playerTwoChosenPiece);

        // when the user chooses to continue playing the checkers game, multiple games can be played
        while(keepPlaying) {
            gameWon = false;
            char playerName = checkerBoard.getPlayerOne();
            char playerNameKing = checkerBoard.getPlayerOneKing();
            char opponentName = checkerBoard.getPlayerTwo();
            char opponentNameKing = checkerBoard.getPlayerTwoKing();
            String inputDirection = " ";
            DirectionEnum playerDirection;
            int outerBoardSizeBoundary = boardSize - 1;

            // when a player has not won the current game, the game continues
            while (gameWon == false) {
                turnCount++;
                // prints the state of the current board, so that players can see it
                System.out.printf("%s\n", checkerBoard);
                int row = 0, column = 0;
                BoardPosition positionOnBoard = new BoardPosition(row, column);
                boolean inputPosResult = false;

                // when the turn count is even, it's player two's turn. When turn count is odd, it's player one's turn
                if (turnCount % EVEN_DIVISOR == 0) {
                    playerName = checkerBoard.getPlayerTwo();
                } else if (turnCount % EVEN_DIVISOR != 0) {
                    playerName = checkerBoard.getPlayerOne();
                }

                // when a player inputs a desired board position (a row and column value) that is invalid, they get re-prompted to input a valid position
                while (inputPosResult == false) {
                    // gets player input
                    System.out.printf("player %c, which piece do you wish to move? Enter the row followed by a space followed by the column.\n", playerName);
                    row = scan.nextInt();
                    column = scan.nextInt();
                    scan.nextLine();
                    positionOnBoard = new BoardPosition(row, column);

                    // when the input board position row and column values are not between 0 and the board size, an error message shows and re-prompting occurs
                    if (validInputPos(row, column, checkerBoard) == false) {
                        System.out.printf("Invalid input. Please enter values between 0 and %d.\n", outerBoardSizeBoundary);
                    }
                    // when the input board position contains a char that does not belong to a player, an error message shows and re-prompting occurs
                    else if (validInputPosChar(playerName, positionOnBoard, checkerBoard) == false) {
                        System.out.printf("Player %c, that isn't your piece. Pick one of your pieces.\n", playerName);
                    } else {
                        checkerBoard.determineViableDirections(positionOnBoard);
                        possibleDirections = checkerBoard.getViableDirections().get(playerName);
                        // when there are no directions to move in, an error message shows and re-prompting occurs
                        if (possibleDirections.isEmpty()) {
                            System.out.println("Invalid piece. No available directions to move to. Pick another piece.");
                        }
                        // otherwise, the entered board position is valid
                        else {
                            inputPosResult = true;
                        }
                    }
                }

                // prompts a player to choose a direction from a list of directions they are allowed to move their chosen piece in
                inputDirectionValid = false;
                System.out.println("In which direction do you wish to move the piece? Enter one of these options:");
                viableDirections = checkerBoard.getViableDirections();
                // prints the viable directions that correspond to the chosen piece's board position
                for (int i = 0; i < viableDirections.get(playerName).size(); i++) {
                    System.out.printf("%s\n", viableDirections.get(playerName).get(i));
                }
                // checks whether a player entered valid input for the direction they want to move
                while (inputDirectionValid == false) {
                    inputDirection = scan.nextLine();
                    for (int j = 0; j < viableDirections.get(playerName).size(); j++) {
                        if (inputDirection.equalsIgnoreCase(viableDirections.get(playerName).get(j).toString())) {
                            inputDirectionValid = true;
                            break;
                        }
                    }
                    // when input is not valid, an error message shows and re-prompting occurs
                    if (inputDirectionValid == false) {
                        System.out.printf("Invalid input. Please enter a valid direction.\n");
                        System.out.println("In which direction do you wish to move the piece? Enter one of these options:");
                    }
                }
                // initializes a player's chosen direction
                playerDirection = DirectionEnum.valueOf(inputDirection.toUpperCase().trim());

                // the positions surrounding a player's board position are checked to see if moving and/or jumping is possible
                surroundingPositions = checkerBoard.scanSurroundingPositions(positionOnBoard);
                // when a board position is empty, a player might be able to move there
                if (surroundingPositions.get(playerDirection) == CheckerBoard.EMPTY_POS) {
                    positionOnBoard = checkerBoard.movePiece(positionOnBoard, playerDirection);
                }
                // when a board position contains the opponent's piece, a player might be able to jump it
                else if (surroundingPositions.get(playerDirection) == opponentName || surroundingPositions.get(playerDirection) == opponentNameKing) {
                    positionOnBoard = checkerBoard.jumpPiece(positionOnBoard, playerDirection);
                }

                // crowns a piece if it reaches the other side of the board
                if (checkerBoard.pieceAtOtherSide(positionOnBoard)) {
                    checkerBoard.crownPiece(positionOnBoard);
                }

                // switches the current player to the other player (player one or player two) to recognize changing turns
                // current player switches
                if (playerName == checkerBoard.getPlayerOne()) {
                    playerName = checkerBoard.getPlayerTwo();
                } else {
                    playerName = checkerBoard.getPlayerOne();
                }
                // player king switches
                if (playerName == checkerBoard.getPlayerOne()) {
                    playerNameKing = checkerBoard.getPlayerOneKing();
                } else {
                    playerNameKing = checkerBoard.getPlayerTwoKing();
                }
                // opponent player switches
                if (playerName == checkerBoard.getPlayerOne()) {
                    opponentName = checkerBoard.getPlayerTwo();
                } else {
                    opponentName = checkerBoard.getPlayerOne();
                }
                // opponent king switches
                if (playerName == checkerBoard.getPlayerOne()) {
                    opponentNameKing = checkerBoard.getPlayerTwoKing();
                } else {
                    opponentNameKing = checkerBoard.getPlayerOneKing();
                }

                // determines if a player has won the game. If a player did win, the game ends
                if (checkerBoard.checkPlayerWin(checkerBoard.getPlayerOne())) {
                    gameWon = true;
                    System.out.printf("Player %c has won!\n", checkerBoard.getPlayerOne());
                } else if (checkerBoard.checkPlayerWin(checkerBoard.getPlayerTwo())) {
                    gameWon = true;
                    System.out.printf("Player %c has won!\n", checkerBoard.getPlayerTwo());
                }
            }

            // asks the user if they want to play another game of checkers. If the user says yes, a new game begins
            System.out.printf("Would you like to play again? Enter 'Y' or 'N'\n");
            keepPlayingResponse = scan.nextLine();
            if (keepPlayingResponse.equalsIgnoreCase("N")) {
                keepPlaying = false;
            }
        }
    }
}

import java.awt.*;
import java.util.*;

public class Board {
    static final int BOARD_LENGTH = 8;
    static final int BOARD_WIDTH = 8;

    private boolean whiteTurn;
    private King whiteKing;
    private King blackKing;

    private Tile[][] board;

    private Board() {
        board = new Tile[BOARD_LENGTH][BOARD_WIDTH];

        /* Filling the board with Tile objects */
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                Coordinate coordinate = new Coordinate(j, i);
                Color color = ((i + j) % 2 == 0)? Color.WHITE: Color.BLACK;
                board[i][j] = new Tile(coordinate, color, this);
            }
        }

        /* Initializing the Tiles with pieces */
        for (int i = 0; i < 8; i++) {
            board[1][i].setPiece(new Pawn(Color.black));
            board[6][i].setPiece(new Pawn(Color.white));
        }

        board[0][0].setPiece(new Rook(Color.black));
        board[0][7].setPiece(new Rook(Color.black));
        board[7][0].setPiece(new Rook(Color.white));
        board[7][7].setPiece(new Rook(Color.white));

        board[0][1].setPiece(new Knight(Color.black));
        board[0][6].setPiece(new Knight(Color.black));
        board[7][1].setPiece(new Knight(Color.white));
        board[7][6].setPiece(new Knight(Color.white));

        board[0][2].setPiece(new Bishop(Color.black));
        board[0][5].setPiece(new Bishop(Color.black));
        board[7][2].setPiece(new Bishop(Color.white));
        board[7][5].setPiece(new Bishop(Color.white));

        board[0][4].setPiece(new Queen(Color.black));
        board[7][3].setPiece(new Queen(Color.white));

        blackKing = new King(Color.black);
        whiteKing = new King(Color.white);
        board[0][3].setPiece(blackKing);
        board[7][4].setPiece(whiteKing);

        whiteTurn = true;
    }

    private void printBoard() { /* Fancy method for movement visualization - shall be removed soon */
        StringBuilder[][] boardedBoard = new StringBuilder[BOARD_LENGTH + 1][BOARD_WIDTH + 1];
        boardedBoard[0][0] = new StringBuilder("/");
        for (int i = 1; i <= BOARD_LENGTH; i++) {
            int letter = 96;
            boardedBoard[0][i] = new StringBuilder("  ");
            (boardedBoard[0][i]).append((char) (letter + i));

            //creating the number index
            boardedBoard[i][0] = new StringBuilder();
            (boardedBoard[i][0]).append(i);
        }

        for (int j = 1; j <= BOARD_LENGTH; j++) {
            for (int i = 1; i <= BOARD_WIDTH; i++) {
                //filling all the board with |*|
                boardedBoard[j][i] = new StringBuilder();
                //Printing the first letter of each piece in it's place in the board
                int start = 0;
                int end = 3;
                if (board[j - 1][i - 1].getPiece() != null) {
                    switch (board[j - 1][i - 1].getPiece().getShortName()) {
                        case "P":
                            boardedBoard[j][i].replace(start, end, "|P|");
                            break;
                        case "R":
                            boardedBoard[j][i].replace(start, end, "|R|");
                            break;
                        case "Kn":
                            boardedBoard[j][i].replace(start, end, "|Kn|");
                            break;
                        case "B":
                            boardedBoard[j][i].replace(start, end, "|B|");
                            break;
                        case "K":
                            boardedBoard[j][i].replace(start, end, "|K|");
                            break;
                        case "Q":
                            boardedBoard[j][i].replace(start, end, "|Q|");
                            break;
                    }
                } else {
                    boardedBoard[j][i].replace(start, end, "|*|");
                }
            }
        }
        System.out.println(Arrays.deepToString(boardedBoard).replace("], ", "]\n"));
        System.out.println();
    }

    LinkedList<Piece> getAllPiecesWithColor(Color color) {
        LinkedList<Piece> listOfPieces = new LinkedList<Piece>();
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                if (!board[i][j].isEmpty() && board[i][j].getPiece().getColor() == color) {
                    listOfPieces.add(board[i][j].getPiece());
                }
            }
        }

        return listOfPieces;
    }

    public King getKing(Color color) {
        return color == Color.WHITE? whiteKing: blackKing;
    }
    Tile getTile(Coordinate coordinate) {
        return board[coordinate.getY()][coordinate.getX()];
    }

    /* Driver function for the chess game */
    private void play() {
        printBoard();
        Scanner sc = new Scanner(System.in);

        /* Main loop of game */
        while (whiteKing.isAlive() && blackKing.isAlive()) {
            /* Obtain the source coordinate from user input */
            System.out.print("Enter the coordinates of the tile you wish to move its piece: ");
            Coordinate sourceCoordinate = new Coordinate(sc.next().toLowerCase());
            if (!sourceCoordinate.isValidCoordinate() || getTile(sourceCoordinate).isEmpty()) {
                continue;
            }

            /* Obtain the destination coordinate from user input */
            System.out.print("Enter the new Destination: ");
            Coordinate destinationCoordinate = new Coordinate(sc.next().toLowerCase());
            if (!destinationCoordinate.isValidCoordinate() ||
                    sourceCoordinate.equals(destinationCoordinate)) {
                continue;
            }

            Piece pieceToMove = getTile(sourceCoordinate).getPiece();
            Tile destinationTile = getTile(destinationCoordinate);

            /* Ensure correct player turn */
            if ((whiteTurn && pieceToMove.getColor() == Color.BLACK) ||
                    (!whiteTurn && pieceToMove.getColor() == Color.WHITE)) {
                System.out.println("Oops, It is " + (whiteTurn? "white": "black") + "'s turn!");
                continue;
            }
            // TODO: force the player to resolve a check for his king if at a situation.

            if (pieceToMove.canMove(destinationTile)) {
                pieceToMove.move(destinationTile);
                whiteTurn = !whiteTurn;
            } else {
                System.out.println("Invalid move for piece: " + pieceToMove.getClass().getSimpleName());
            }

            printBoard();
        }

        System.out.println("Game is over.");
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.play();
    }
}

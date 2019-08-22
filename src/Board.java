import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class Board {
    static final int BOARDlENGTH = 8;
    static final int BOARDWIDTH = 8;
    private static boolean areKingsAlive, isWhiteKingAlive, isBlackKingAlive;
    private boolean check = false;
    private boolean whiteTurn = true;
    private Tile[][] board;

    public Board() {
        board = new Tile[BOARDlENGTH][BOARDWIDTH];

        for (int i = 0; i < BOARDlENGTH; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                Tile newTile;
                String Coordinate = (char) (j + 'a') + Integer.toString(i + 1);
                if ((i + j) % 2 == 0) {
                    newTile = new Tile(Coordinate, Color.WHITE);
                } else {
                    newTile = new Tile(Coordinate, Color.BLACK);
                }
                board[i][j] = newTile;
            }
        }

        for (int i = 0; i < 8; i++) {
            board[1][i].setPiece(new Pawn(Color.black));
            board[6][i].setPiece(new Pawn(Color.white));
        }

        board[0][0].setPiece(new Rook(Color.black));
        board[0][7].setPiece(new Rook(Color.black));

        board[0][1].setPiece(new Knight(Color.black));
        board[0][6].setPiece(new Knight(Color.black));

        board[0][2].setPiece(new Bishop(Color.black));
        board[0][5].setPiece(new Bishop(Color.black));

        board[0][3].setPiece(new King(Color.black));
        board[0][4].setPiece(new Queen(Color.black));

        board[7][0].setPiece(new Rook(Color.white));
        board[7][7].setPiece(new Rook(Color.white));

        board[7][1].setPiece(new Knight(Color.white));
        board[7][6].setPiece(new Knight(Color.white));

        board[7][2].setPiece(new Bishop(Color.white));
        board[7][5].setPiece(new Bishop(Color.white));

        board[7][3].setPiece(new Queen(Color.white));
        board[7][4].setPiece(new King(Color.white));

        areKingsAlive = true;
        isBlackKingAlive = true;
        isWhiteKingAlive = true;
        check = false;
        whiteTurn = true;

    }

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        Board board = new Board();
        String userSelectedTile, userNextMove;

        while (isAreKingsAlive()) {
            board.printBoard();
            System.out.println("Enter Pieces' index  you want to move: \n");
            userSelectedTile = userInput.next();

            if (!board.getTile(userSelectedTile).isEmpty()) {
                System.out.println("Enter the new Place: \n");
                userNextMove = userInput.next();
                board.move(userSelectedTile, userNextMove);
            } else {
                System.out.println("Please Select a Piece, this tile does not contain any piece");
            }
        }
    }

    private static boolean isAreKingsAlive() {
        return areKingsAlive;
    }

    public void setAreKingsAlive(boolean kingStillAlive) {
        Board.areKingsAlive = kingStillAlive;
    }

    private Tile getTile(String index) {
        int xAxis = index.charAt(0) - 'a';
        int yAxis = index.charAt(1) - '1';
        return board[yAxis][xAxis];
    }

    public void isCheck(boolean check) {
        this.check = check;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void setWhiteTurn(boolean whiteTurn) {
        this.whiteTurn = whiteTurn;
    }

    private static boolean isValidCoordinate(String coordinate) {
        return (coordinate.charAt(0) >= 'a' && coordinate.charAt(0) <= 'h') &&
                (coordinate.charAt(1) >= '1' && coordinate.charAt(1) <= '8');
    }

    public void move(String sourceCoordinate, String destinationCoordinate) {
        if (!isValidCoordinate(sourceCoordinate) || !isValidCoordinate(destinationCoordinate)) {
            System.out.println("Bad values for coordinates.");
            return;
        }
        if (sourceCoordinate.equals(destinationCoordinate)) {
            System.out.println("Bad input: Source and destination coordinates are the same.");
            return;
        }

        Tile sourceTile = getTile(sourceCoordinate);
        Tile destinationTile = getTile(destinationCoordinate);
        if (sourceTile.isEmpty()) {
            System.out.println("Please select a tile that contains a piece");
            return;
        }

        Piece pieceToMove = getTile(sourceCoordinate).getPiece();
        boolean destinationTileHasFriendlyTroops = !destinationTile.isEmpty() &&
                destinationTile.getPiece().getColor() == pieceToMove.getColor();

        if (!destinationTileHasFriendlyTroops) {
            pieceToMove.move(destinationTile);
            System.out.println("Piece Moved Successfully");
        } else {
            System.out.println("Please Enter a Valid Coordinate, this tile is occupied by a piece from your own army");
        }
    }

    public boolean isCheck() {
        return check;
    }

    private void printBoard() {
        StringBuilder[][] boardedBoard = new StringBuilder[BOARDlENGTH + 1][BOARDWIDTH + 1];
        boardedBoard[0][0] = new StringBuilder("/");
        for (int i = 1; i <= BOARDlENGTH; i++) {
            int letter = 96;
            boardedBoard[0][i] = new StringBuilder("  ");
            (boardedBoard[0][i]).append((char) (letter + i));

            //creating the number index
            boardedBoard[i][0] = new StringBuilder();
            (boardedBoard[i][0]).append(i);
        }

        for (int j = 1; j <= BOARDlENGTH; j++) {
            for (int i = 1; i <= BOARDWIDTH; i++) {
                //filling all the board with |*|
                boardedBoard[j][i] = new StringBuilder();
                //Printing the first letter of each piece in it's place in the board
                int start = 0;
                int end = 3;
                if (board[j-1][i-1].getPiece() != null) {
                    switch (board[j-1][i - 1].getPiece().getName()) {
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
}

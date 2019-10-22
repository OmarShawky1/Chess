import javafx.scene.paint.Color;
import java.util.*;

public class Board {
    static final int BOARD_LENGTH = 8;
    static final int BOARD_WIDTH = 8;

    public boolean whiteTurn;
    private King whiteKing;
    private King blackKing;
    private Tile[][] board;

    public Board() {
        board = new Tile[BOARD_LENGTH][BOARD_WIDTH];

        /* Filling the board with Tile objects */
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                Coordinate coordinate = new Coordinate(j, i);
                String color = ((i + j) % 2 == 0) ? "yellow" : "brown";
                board[i][j] = new Tile(coordinate, color, this);
            }
        }

        /* Initializing the Tiles with pieces */
        for (int i = 0; i < 8; i++) {
            board[1][i].setPiece(new Pawn(Color.BLACK));
            board[6][i].setPiece(new Pawn(Color.WHITE));
        }

        board[0][0].setPiece(new Rook(Color.BLACK));
        board[0][7].setPiece(new Rook(Color.BLACK));
        board[7][0].setPiece(new Rook(Color.WHITE));
        board[7][7].setPiece(new Rook(Color.WHITE));

        board[0][1].setPiece(new Knight(Color.BLACK));
        board[0][6].setPiece(new Knight(Color.BLACK));
        board[7][1].setPiece(new Knight(Color.WHITE));
        board[7][6].setPiece(new Knight(Color.WHITE));

        board[0][2].setPiece(new Bishop(Color.BLACK));
        board[0][5].setPiece(new Bishop(Color.BLACK));
        board[7][2].setPiece(new Bishop(Color.WHITE));
        board[7][5].setPiece(new Bishop(Color.WHITE));

        board[0][3].setPiece(new Queen(Color.BLACK));
        board[7][3].setPiece(new Queen(Color.WHITE));

        blackKing = new King(Color.BLACK);
        whiteKing = new King(Color.WHITE);
        board[0][4].setPiece(blackKing);
        board[7][4].setPiece(whiteKing);

        whiteTurn = true;
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
        return color == Color.WHITE ? whiteKing : blackKing;
    }

    Tile getTile(Coordinate coordinate) {
        return board[coordinate.getY()][coordinate.getX()];
    }

    private void checkIfEnemyGotChecked(Color enemyColor) {
        Color currentPlayerColor = enemyColor == Color.WHITE ? Color.BLACK: Color.WHITE;
        LinkedList<Piece> currentPlayerArmy = getAllPiecesWithColor(currentPlayerColor);
        Piece enemyKing = getKing(enemyColor);
        for (Piece piece : currentPlayerArmy) {
            if (piece.canMove(enemyKing.tile)) {
                System.out.println("The " + (enemyColor == Color.WHITE ? "White" : "Black") + " king's is in check");
            }
        }
    }

    public void play(Coordinate sourceCoordinate, Coordinate destinationCoordinate) {

        if (whiteKing.isAlive() && blackKing.isAlive()) {

            Piece pieceToMove = getTile(sourceCoordinate).getPiece();
            Tile destinationTile = getTile(destinationCoordinate);

            if (pieceToMove.canMove(destinationTile)) {
                pieceToMove.move(destinationTile);
                whiteTurn = !whiteTurn;
            } else {
                System.out.println("Invalid move for piece: " + pieceToMove.getClass().getName());
            }

            Color enemyColor = pieceToMove.color == Color.WHITE ? Color.BLACK : Color.WHITE;
            this.checkIfEnemyGotChecked(enemyColor);
        }else {
            System.out.println("Game is over.");
        }
    }
}

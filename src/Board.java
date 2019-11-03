import javafx.scene.paint.Color;

import java.util.*;

public class Board {
    static final int BOARD_LENGTH = 8;
    static final int BOARD_WIDTH = 8;

    boolean whiteTurn;
    private King whiteKing, blackKing;
    private boolean whiteKingChecked, blackKingChecked;
    boolean whiteKingAlive, blackKingAlive;


    private Tile[][] board;

    Board() {
        board = new Tile[BOARD_LENGTH][BOARD_WIDTH];
        /* Filling the board with Tile objects */
        for (int row = 0; row < BOARD_LENGTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                Coordinate coordinate = new Coordinate(col, row);
                String color = ((row + col) % 2 == 0) ? "yellow" : "brown";
                board[row][col] = new Tile(coordinate, color, this, coordinate.toString());
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
        whiteKingChecked = false;
        blackKingChecked = false;
        whiteKingAlive = true;
        blackKingAlive = true;

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

    King getKing(Color color) {
        return color == Color.WHITE ? whiteKing : blackKing;
    }

    Tile getTile(Coordinate coordinate) {
        return board[coordinate.getY()][coordinate.getX()];
    }

    void play(Tile sourceTile, Tile destinationTile) {
        if (whiteKingAlive && blackKingAlive) {
            removeEnPassant();

            //getPiece to move, there is no need to check if the sourceTile is Empty because it is already checked in GUI
            Piece pieceToMove = sourceTile.getPiece();
            Color pieceColor = pieceToMove.getColor();
            boolean rightPlayersTurn = (pieceColor == Color.WHITE && whiteTurn) || (pieceColor == Color.BLACK && !whiteTurn);
            if (rightPlayersTurn) {
                if (pieceToMove.canMove(destinationTile)) {

                    pieceToMove.move(destinationTile);

                    whiteTurn = !whiteTurn;

                    whiteKingAlive = whiteKing.isAlive();
                    blackKingAlive = blackKing.isAlive();
                    //print who lost and exit the game
                    if (!whiteKingAlive) {
                        GUI.gameStatusBar.setText("White Lost");
                        return;
                    }
                    if (!blackKingAlive) {
                        GUI.gameStatusBar.setText("Black Lost");
                        return;
                    }

                    whiteKingChecked = whiteKing.isBeingChecked();
                    blackKingChecked = blackKing.isBeingChecked();
                    //Print who got checked
                    if (whiteKingChecked) {
                        GUI.gameStatusBar.setText("White King Got Checked");
                    }
                    if (blackKingChecked) {
                        GUI.gameStatusBar.setText("Black King Got Checked");
                    }

                } else {
//                System.out.println("Invalid move for piece: " + pieceToMove.getClass().getName());
                    GUI.gameStatusBar.setText("Invalid Move For Piece: " + pieceToMove.getClass().getName());
                }
            } else {
                boolean whosTurn = pieceColor == Color.BLACK;
                GUI.gameStatusBar.setText("This is " + (whosTurn? "White's":"Black's") + " Turn");
            }
        }
    }

    private void removeEnPassant (){
        LinkedList<Piece> pieces = whiteTurn? getAllPiecesWithColor(Color.WHITE):getAllPiecesWithColor(Color.BLACK);
        for (Piece piece : pieces){
            if (piece instanceof Pawn){
                Pawn pawn = (Pawn) piece;
                pawn.setCanEnPassantMe();
            }
        }
    }
}

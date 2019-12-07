import java.io.IOException;
import java.util.*;

//there are accumilated unnecessary checks here that was updated and implemented in different way, i will not remove it until i know them
// all, examples to that; rightPlayersTurn and so on

class Board {
    static final int BOARD_LENGTH = 8;
    static final int BOARD_WIDTH = 8;

    boolean whiteTurn;
    private King whiteKing, blackKing;
    private boolean whiteKingChecked, blackKingChecked;
    boolean whiteKingAlive, blackKingAlive;
    private Tile[][] board;
    private GUI gui; //This is so new

    Board(GUI gui) {
        //This is so new
        this.gui = gui;


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
            board[1][i].setPiece(new Pawn("black"));
            board[6][i].setPiece(new Pawn("white"));
        }

        board[0][0].setPiece(new Rook("black"));
        board[0][7].setPiece(new Rook("black"));
        board[7][0].setPiece(new Rook("white"));
        board[7][7].setPiece(new Rook("white"));

        board[0][1].setPiece(new Knight("black"));
        board[0][6].setPiece(new Knight("black"));
        board[7][1].setPiece(new Knight("white"));
        board[7][6].setPiece(new Knight("white"));

        board[0][2].setPiece(new Bishop("black"));
        board[0][5].setPiece(new Bishop("black"));
        board[7][2].setPiece(new Bishop("white"));
        board[7][5].setPiece(new Bishop("white"));

        board[0][3].setPiece(new Queen("black"));
        board[7][3].setPiece(new Queen("white"));

        blackKing = new King("black");
        whiteKing = new King("white");
        board[0][4].setPiece(blackKing);
        board[7][4].setPiece(whiteKing);

        whiteTurn = true;
        whiteKingChecked = false;
        blackKingChecked = false;
        whiteKingAlive = true;
        blackKingAlive = true;

    }

    LinkedList<Piece> getAllPiecesWithColor(String color) {
        LinkedList<Piece> listOfPieces = new LinkedList<Piece>();
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                if (!board[i][j].isEmpty() && board[i][j].getPiece().getColor().equals(color)) {
                    listOfPieces.add(board[i][j].getPiece());
                }
            }
        }

        return listOfPieces;
    }

    King getKing(String color) {
//        return color == Color.WHITE ? whiteKing : blackKing;
        return color.equalsIgnoreCase("white") ? whiteKing : blackKing;
    }

    Tile getTile(Coordinate coordinate) {
        return board[coordinate.getY()][coordinate.getX()];
    }

    void play(Tile sourceTile, Tile destinationTile, boolean receiveThread) throws IOException {
//        System.out.println("Play Was Called");
//        System.out.println("whiteTurn: " + whiteTurn);
        if (whiteKingAlive && blackKingAlive) {
            removeEnPassant();

            //getPiece to move, there is no need to check if the sourceTile is Empty because it is already checked in GUI
            Piece pieceToMove = sourceTile.getPiece();
            String pieceColor = pieceToMove.getColor();
            boolean rightPlayersTurn =
                    (pieceColor.equalsIgnoreCase("white") && whiteTurn) || (pieceColor.equalsIgnoreCase("black") && !whiteTurn);
            if (rightPlayersTurn) {
                if (pieceToMove.canMove(destinationTile)) {
                    pieceToMove.move(destinationTile);
                    whiteTurn = !whiteTurn;

                    /** This is so new and i do not know the consequences**/
                    if (!receiveThread){
                        gui.sendMovement();
                    }

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
                    GUI.gameStatusBar.setText("Invalid Move For Piece: " + pieceToMove.getClass().getName());
                }
            } else {
                boolean whosTurn = pieceColor.equalsIgnoreCase("black");
                GUI.gameStatusBar.setText("This is " + (whosTurn ? "White's" : "Black's") + " Turn");
            }
        }
    }

    private void removeEnPassant() {
        LinkedList<Piece> pieces = whiteTurn ? getAllPiecesWithColor("white") : getAllPiecesWithColor("black");
        for (Piece piece : pieces) {
            if (piece instanceof Pawn) {
                Pawn pawn = (Pawn) piece;
                pawn.setCanEnPassantMe();
            }
        }
    }

}
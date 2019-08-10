import java.awt.*;

public class Board {

    static final int numberOfTiles = 64;

    private Tile[] board;

    public Board() {

        board = new Tile[numberOfTiles];

        for (int i = 0; i < numberOfTiles; i++) {

            board[i] = new Tile(i);
        }


        //setting black and white colors for the board
        for (int i = 0; i < numberOfTiles; i += 2) {

            if (i % 2 == 0) {

                board[i].setColor(Color.WHITE);
            } else {
                board[i].setColor(Color.BLACK);
            }
        }


        //create the places for the Black Pawns
        for (int i = 8; i <= 16; i++) {

            board[i].setPiece(new Pawn(Color.black));
        }

        //create the places for the White Pawns
        for (int i = 48; i <= 54; i++) {

            board[i].setPiece(new Pawn(Color.white));
        }

        //setting the main pieces for the Black
        board[0].setPiece(new Rook(Color.black)); //equiv for [0][0]
        board[7].setPiece(new Rook(Color.black));

        board[1].setPiece(new King(Color.black));
        board[6].setPiece(new King(Color.black));

        board[2].setPiece(new Bishop(Color.black));
        board[5].setPiece(new Bishop(Color.black));

        board[3].setPiece(new King(Color.black));
        board[4].setPiece(new Queen(Color.black));


        //setting the main pieces for the White
        board[56].setPiece(new Rook(Color.white)); //equiv for [8][0]
        board[63].setPiece(new Rook(Color.white));

        board[57].setPiece(new Knight(Color.white));
        board[62].setPiece(new Knight(Color.white));

        board[58].setPiece(new Bishop(Color.white));
        board[61].setPiece(new Bishop(Color.white));

        board[59].setPiece(new King(Color.white));
        board[60].setPiece(new Queen(Color.white));
    }

    public static void main(String args[]) {

    }
}

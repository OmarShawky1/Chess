import java.awt.*;

public class Board {

    static final int NUMBEROFTILES = 64;
    static final int X_AXIS = 8;
    static final int Y_AXIS = 8;

    private Tile[][] board;

    public boolean canMove(Piece piece,
                           String newCoordinate) {

        //cannot move to his own same place
        if ((piece.getOldCoordinate()).equals(newCoordinate)) {

            return false;
        }

        //cannot move to a place that contains a piece from it's color

        int newXAxis = piece.getOldCoordinate().charAt(0);
        int newYAxis = piece.getOldCoordinate().charAt(1);


        if (piece.getColor() == board[newXAxis][newYAxis]
                .getPiece().getColor()) {

            return false;
        }


        //cannot move if their is a check on the king unless he
        // will protect him

        //missing

        if(getCheck()){

            return false;
        }


        //cannot move to a place beyond a piece
        //i truly do not know how to make this one, probably from
        // the each piece subclass itself

        return true;

    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    private boolean check = false;

    //Creating the board of 64 pile (constructor)
    public Board() {

        //creating 8X8 Tiles
        board = new Tile[X_AXIS][Y_AXIS];

        for (int i = 97; i < (NUMBEROFTILES + 97); i++) {

            for (int j = 0; j < Y_AXIS; j++) {

                char horizontalAxis = (char) i;
                String Coordinate =
                        horizontalAxis + Integer.toString(j);
                board[i][j] = new Tile(Coordinate);
//              board[i][j] = new Tile(((char) i) + Integer.toString(j));
            }
        }

        //setting black and white colors for the board
        for (int i = 0; i < X_AXIS; i++) {

            for (int j = 0; j < Y_AXIS; j++) {

                if (i % 2 == 0) {

                    board[i][j].setColor(Color.WHITE);
                } else {
                    board[i][j].setColor(Color.BLACK);
                }
            }
        }


        //creating the border of the Pile (Pile means group of
        // Tiles), the border is composed of letters in the
        // horizontal axis and numbers in vertical axis, each is 8
        // squares length


        /////Placing every piece in its/////


        //create the places for the White & Black Pawns
        for (int i = 0; i <= 8; i++) {

            board[i][1].setPiece(new Pawn(Color.black));
            board[i][6].setPiece(new Pawn(Color.white));
        }


        //setting the main pieces for the Black
        board[0][0].setPiece(new Rook(Color.black));
        board[7][0].setPiece(new Rook(Color.black));

        board[1][0].setPiece(new King(Color.black));
        board[6][0].setPiece(new King(Color.black));

        board[2][0].setPiece(new Bishop(Color.black));
        board[5][0].setPiece(new Bishop(Color.black));

        board[3][0].setPiece(new King(Color.black));
        board[4][0].setPiece(new Queen(Color.black));


        //setting the main pieces for the White
        board[0][7].setPiece(new Rook(Color.white));
        board[7][7].setPiece(new Rook(Color.white));

        board[1][7].setPiece(new Knight(Color.white));
        board[6][7].setPiece(new Knight(Color.white));

        board[2][7].setPiece(new Bishop(Color.white));
        board[5][7].setPiece(new Bishop(Color.white));

        board[3][7].setPiece(new King(Color.white));
        board[4][7].setPiece(new Queen(Color.white));
    }


    public static void main(String[] args) {


    }


}

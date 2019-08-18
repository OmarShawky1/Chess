import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class Board {

    static final int NUMBEROFTILES = 64;
    static final int BOARDlENGTH = 8;
    static final int BOARDWIDTH = 8;

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

        return !getCheck();


        //cannot move to a place beyond a piece
        //i truly do not know how to make this one, probably from
        // the each piece subclass itself

    }

    public boolean getCheck() {
        return check;
    }

    public void isCheck(boolean check) {
        this.check = check;
    }

    private boolean check = false;

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void setWhiteTurn(boolean whiteTurn) {
        this.whiteTurn = whiteTurn;
    }

    private boolean whiteTurn = true;

    private void printBoard() {

        Scanner userInput = new Scanner(System.in);

        StringBuilder[][] boardedBoard =
                new StringBuilder[BOARDlENGTH][BOARDWIDTH];

        for (int i = 1; i <= 9; i++) {

            //creating the letters index
            int letter = 96;
            (boardedBoard[i][0]).append((char) (letter + i));

            //creating the number index
            (boardedBoard[0][i]).append(i - 1);
        }

        //placing every empty place in the board with |*|, then
        //Placing every Piece in a Tile with it's name first letter

        for (int i = 1; i <= BOARDlENGTH + 1; i++) {

            for (int j = 1; j < BOARDWIDTH + 1; j++) {

                boardedBoard[i][j].append("|*|");

                //Printing the first name of each piece in it's place in
                // the board
                switch (this.board[i - 1][j - 1].getPiece().getClass().getName()) {

                    case "P":
                        boardedBoard[i][j].append("|P|");
                        break;

                    case "R":
                        boardedBoard[i][j].append("|R|");
                        break;

                    case "Kn":
                        boardedBoard[i][j].append("|Kn|");
                        break;

                    case "B":
                        boardedBoard[i][j].append("|B|");
                        break;

                    case "K":
                        boardedBoard[i][j].append("|K|");
                        break;

                    case "Q":
                        boardedBoard[i][j].append("|Q|");
                        break;
                }
            }
        }

        //prints the entire 2D array that was just created
        System.out.println(Arrays.deepToString(boardedBoard));
    }

    //Creating the board of 64 pile (constructor)
    public Board() {

        //creating 8X8 Tiles
        board = new Tile[BOARDlENGTH][BOARDWIDTH];

        for (int i = 97; i < (NUMBEROFTILES + 97); i++) {

            for (int j = 0; j < BOARDWIDTH; j++) {

                char horizontalAxis = (char) i;
                String Coordinate =
                        horizontalAxis + Integer.toString(j);
                board[i][j] = new Tile(Coordinate);
//              board[i][j] = new Tile(((char) i) + Integer.toString(j));
            }
        }

        //setting black and white colors for the board
        for (int i = 0; i < BOARDlENGTH; i++) {

            for (int j = 0; j < BOARDWIDTH; j++) {

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

        Board board = new Board();
        board.printBoard();

        while (true) {

            //perhaps i put here the name of the piece and where to
            // go?
        }


    }


}

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class Board {

    static final int NUMBEROFTILES = 64;
    static final int BOARDlENGTH = 8;
    static final int BOARDWIDTH = 8;

    public static boolean kingStillAlive = true;

    private Tile[][] board;
    private boolean check = false;
    private boolean whiteTurn = true;

    //Creating the board of 64 pile (constructor)
    public Board() {

        //creating 8X8 Tiles
        board = new Tile[BOARDlENGTH][BOARDWIDTH];

        for (int i = 0; i < BOARDlENGTH; i++) {

            for (int j = 0; j < BOARDWIDTH; j++) {

                char horizontalAxis = (char) (i + 97);
                String Coordinate = horizontalAxis + Integer.toString(j);
                board[i][j] = new Tile(Coordinate);
                //board[i][j] = new Tile(((char) i) + Integer.toString(j));
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


        /////Placing every piece in its corresponding place in board/////


        //create the places for the White & Black Pawns
        for (int i = 0; i < 8; i++) {

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

        Scanner userInput = new Scanner(System.in);
        Board board = new Board();
        board.printBoard();

        String userSelectedTile, userNextMove;

        while (isKingStillAlive()) {

            System.out.println("Enter Pieces' index  you want to " + "move: \n");

            userSelectedTile = userInput.next(); //does not differ that much from nextLine() ??

            Piece userSelectedPiece;
            if (board.getTile(userSelectedTile).isEmpty) {

                userSelectedPiece = board.getTile(userSelectedTile).getPiece();

                System.out.println("Enter the new Place: \n");

                userNextMove = userInput.next();

                userSelectedPiece.move(userNextMove);
            } else {

                System.out.println("Please Select a Piece");
            }

        }

    }

    public static boolean isKingStillAlive() {
        return kingStillAlive;
    }

    public void setKingStillAlive(boolean kingStillAlive) {
        Board.kingStillAlive = kingStillAlive;
    }

    public Tile getTile(String index) {
        int xAxis = index.charAt(0) - 'a';
        int yAxis = index.charAt(1) - '1';
        return board[xAxis][yAxis];
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

    public boolean canMove(Piece piece, String newCoordinate) {

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

        return !isCheck();


        //cannot move to a place beyond a piece
        //i truly do not know how to make this one, probably from
        // the each piece subclass itself

    }

    public boolean isCheck() {
        return check;
    }

    private void printBoard() {

        StringBuilder[][] boardedBoard = new StringBuilder[BOARDlENGTH + 1][BOARDWIDTH + 1];

        //filling the unwanted part in the boardedBoard (0,0)
        boardedBoard[0][0] = new StringBuilder("/0/");

        //creating the borders of the board (letters horizontally and numbers vertically)
        for (int i = 1; i <= BOARDlENGTH; i++) {

            //creating the letters index
            int letter = 96;
            boardedBoard[0][i] = new StringBuilder("  ");
            (boardedBoard[0][i]).append((char) (letter + i));

            //creating the number index
            boardedBoard[i][0] = new StringBuilder();
            (boardedBoard[i][0]).append(i - 1);
        }


        //Printing every Empty space in the board by |*|, or |pieceFirstLetter|
        for (int i = 1; i < BOARDlENGTH + 1; i++) {

            for (int j = 1; j < BOARDWIDTH + 1; j++) {

                //filling all the board with |*|
                boardedBoard[i][j] = new StringBuilder();
                boardedBoard[i][j].append("|*|");


                //Printing the first name of each piece in it's place in the board
                if (!this.board[i - 1][j - 1].isEmpty) {

                    switch (this.board[i - 1][j - 1].getPiece().getClass().getName()) {

                        case "P":
                            boardedBoard[i][j].replace(0, 2, "|P|");
                            break;

                        case "R":
                            boardedBoard[i][j].replace(0, 2, "|R|");
                            break;

                        case "Kn":
                            boardedBoard[i][j].replace(0, 2, "|Kn|");
                            break;

                        case "B":
                            boardedBoard[i][j].replace(0, 2, "|B|");
                            break;

                        case "K":
                            boardedBoard[i][j].replace(0, 2, "|K|");
                            break;

                        case "Q":
                            boardedBoard[i][j].replace(0, 2, "|Q|");
                            break;
                    }
                } else {

                    boardedBoard[i][j].replace(0, 2, "|*|"); //this line causes somehow error, it prints || twice in some lines
                }
            }
        }

        //prints the entire 2D array that was just created
        //System.out.println(Arrays.deepToString(boardedBoard));
        System.out.println(Arrays.deepToString(boardedBoard).replace("], ", "]\n"));
    }

}

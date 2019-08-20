import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class Board {

    static final int NUMBEROFTILES = 64;
    static final int BOARDlENGTH = 8;
    static final int BOARDWIDTH = 8;

    private static boolean areKingsAlive, isWhiteKingAlive, isBlackKingAlive;
    private boolean check = false;
    private boolean whiteTurn = true;

    private Tile[][] board;


    //Creating the board of 64 pile (constructor)
    public Board() {

        //creating chess board Tiles
        board = new Tile[BOARDlENGTH][BOARDWIDTH];

        for (int i = 0; i < BOARDlENGTH; i++) {

            for (int j = 0; j < BOARDWIDTH; j++) {

                char horizontalAxis = (char) (i + 97);
                String Coordinate = horizontalAxis + Integer.toString(j);
                board[i][j] = new Tile(Coordinate);
                //board[i][j] = new Tile(((char) i) + Integer.toString(j));
            }
        }

        //setting the color of the chess board tiles
        for (int i = 0; i < BOARDlENGTH; i++) {

            for (int j = 0; j < BOARDWIDTH; j++) {

                if (i % 2 == 0) {

                    board[i][j].setTileColor(Color.WHITE);
                } else {
                    board[i][j].setTileColor(Color.BLACK);
                }
            }
        }


        /////Placing every piece in its corresponding place in board/////


        //Creating the Black & White Pawns
        for (int i = 0; i < 8; i++) {

            board[1][i].setPiece(new Pawn(Color.black));
            board[6][i].setPiece(new Pawn(Color.white));
        }


        //setting the main pieces for the Black
        board[0][0].setPiece(new Rook(Color.black));
        board[0][7].setPiece(new Rook(Color.black));

        board[0][1].setPiece(new Knight(Color.black));
        board[0][6].setPiece(new Knight(Color.black));

        board[0][2].setPiece(new Bishop(Color.black));
        board[0][5].setPiece(new Bishop(Color.black));

        board[0][3].setPiece(new King(Color.black));
        board[0][4].setPiece(new Queen(Color.black));


        //setting the main pieces for the White
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

            userSelectedTile = userInput.next(); //does not differ that much from nextLine() ??

            if (!board.getTile(userSelectedTile).isTileEmpty()) {

                System.out.println("Enter the new Place: \n");

                userNextMove = userInput.next();

                board.move(userSelectedTile, userNextMove);

            } else {

                System.out.println("Please Select a Piece, this tile does not contain any piece");
            }

        }

    }

    public static boolean isAreKingsAlive() {
        return areKingsAlive;
    }

    public void setAreKingsAlive(boolean kingStillAlive) {
        Board.areKingsAlive = kingStillAlive;
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

    public void move(String pieceCoordinate, String newCoordinate) {

        Piece movingPiece;

        //checks that the input is in the board
        if ((pieceCoordinate.charAt(0) >= 'a' && pieceCoordinate.charAt(0) <= 'f') &&
                (pieceCoordinate.charAt(1) >= '1' && pieceCoordinate.charAt(1) <= '8') &&
                (newCoordinate.charAt(0) >= 'a' && newCoordinate.charAt(1) <= '8')) {

            //checks if the tile itself contains a piece
            if (!board[(pieceCoordinate.charAt(0) - 'a')][(pieceCoordinate.charAt(1) - '1')].isTileEmpty()) {

                movingPiece = board[(pieceCoordinate.charAt(0) - 'a')][(pieceCoordinate.charAt(1) - '1')].getPiece();

                //cannot move to his own same place
                if (!pieceCoordinate.equals(newCoordinate)) {

                    //all checks are correct except the is new coordinate empty or not, it gives yes although it is empty
                    //try a2 as the piece, and a3 as the new place
                    System.out.println("This is the condition 2 before the moving of the piece");
                    System.out.println("MovingPiece color is: " + movingPiece.getColor());
                    System.out.println("newCoordinate empty or not: " + board[newCoordinate.charAt(0) - 'a'][newCoordinate.charAt(1) - '1'].isTileEmpty());
                    System.out.println("newCoordinate pieces' Color is: " + board[newCoordinate.charAt(0) - 'a']
                            [newCoordinate.charAt(1) - '1'].getPiece().getColor());

                    //checks if the the newCoordinate (nextMove) is not in an empty tile in order not to get an error while getting the
                    // piece inside of it
                    if (!board[newCoordinate.charAt(0) - 'a'][newCoordinate.charAt(1) - '1'].isTileEmpty()) {

                        //cannot move to a place that contains a piece from it's color
                        if (!(movingPiece.getColor() == board[newCoordinate.charAt(0) - 'a']
                                [newCoordinate.charAt(1) - '1'].getPiece().getColor())) {


                            //cannot move if their is a check on the king unless he
                            // will protect him


                            //cannot move to a place beyond a piece

                            //finally, move piece

                            //holding the moving piece in a temp
                            movingPiece = board[(pieceCoordinate.charAt(0) - 'a')][(pieceCoordinate.charAt(1) - '1')].getPiece();

                            movingPiece.move(pieceCoordinate, newCoordinate);
                            board[(newCoordinate.charAt(0) - 'a')][(newCoordinate.charAt(1) - '1')].setEmpty(false);

                            //removing the piece from old tile and setting it empty
                            board[(pieceCoordinate.charAt(0) - 'a')][(pieceCoordinate.charAt(1) - '1')].setPiece(null);
                            board[(pieceCoordinate.charAt(0) - 'a')][(pieceCoordinate.charAt(1) - '1')].setEmpty(true);

                            System.out.println("Piece Moved Successfuly");
                        } else {

                            System.out.println("This Piece did not move, please check why");
                        }
                    } else

                        System.out.println("Please Enter a Valid Coordinate, this tile is occupied by a piece from your own army");
                } else {

                    System.out.println("Please Enter a Valid Coordinate, the new coordinate is the same as the old");
                    return;
                }
            } else {

                System.out.println("Please select a tile that contains a piece");
                return;
            }
        } else {

            System.out.println("Please enter Coordinates inside the board");
            return;
        }

    }

    public boolean isCheck() {

        return check;
    }

    private void printBoard() {

        StringBuilder[][] boardedBoard = new StringBuilder[BOARDlENGTH + 1][BOARDWIDTH + 1];

        //replacing "NULL" by /0/ at (0,0)
        boardedBoard[0][0] = new StringBuilder("/0/");

        //creating the borders of the board (letters horizontally and numbers vertically)
        for (int i = 1; i <= BOARDlENGTH; i++) {

            //creating the letters index
            int letter = 96;
            boardedBoard[0][i] = new StringBuilder("  ");
            (boardedBoard[0][i]).append((char) (letter + i));

            //creating the number index
            boardedBoard[i][0] = new StringBuilder();
            (boardedBoard[i][0]).append(i);
        }


        //Printing every Empty space in the board by |*|, or |pieceFirstLetter|
        for (int j = 1; j <= BOARDlENGTH; j++) {

            for (int i = 1; i <= BOARDWIDTH; i++) {

                //filling all the board with |*|
                boardedBoard[j][i] = new StringBuilder();
                boardedBoard[j][i].append("|*|");

                //Printing the first letter of each piece in it's place in the board
                int start = 0;
                int end = 3;
                if (!this.board[j - 1][i - 1].isTileEmpty()) {


                    switch (this.board[j - 1][i - 1].getPiece().getName()) {

                        //i tried to write start and end here, but it refused....why?
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

        //prints the entire 2D array that was just created
        //System.out.println(Arrays.deepToString(boardedBoard));
        System.out.println(Arrays.deepToString(boardedBoard).replace("], ", "]\n"));
        System.out.println();
    }

}

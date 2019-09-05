import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class Board {
    static final int BOARDlENGTH = 8;
    static final int BOARDWIDTH = 8;
    private static boolean areKingsAlive, isWhiteKingAlive, isBlackKingAlive;
    private boolean check = false;
    private boolean whiteTurn = true;
    private String whiteKingCoordinate;
    public String getWhiteKingCoordinate() {
        return whiteKingCoordinate;
    }
    public void setWhiteKingCoordinate(String whiteKingCoordinate) {
        this.whiteKingCoordinate = whiteKingCoordinate;
    }
    private String blackKingCoordinate;
    public String getBlackKingCoordinate() {
        return blackKingCoordinate;
    }
    public void setBlackKingCoordinate(String blackKingCoordinate) {
        this.blackKingCoordinate = blackKingCoordinate;
    }

    private Tile[][] board;

    public Board() {
        board = new Tile[BOARDlENGTH][BOARDWIDTH];

        //coloring the board
        for (int i = 0; i < BOARDlENGTH; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                Tile newTile;
                String Coordinate = (char) (j + 'a') + Integer.toString(i + 1);
                if ((i + j) % 2 == 0) {
                    newTile = new Tile(Coordinate, Color.WHITE, this); // just pass a `this` to the Tile constructor
                } else {
                    newTile = new Tile(Coordinate, Color.BLACK, this);
                }
                board[i][j] = newTile;
            }
        }

        //Placing Pieces
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
        setWhiteKingCoordinate(board[7][4].getCoordinates());
        setBlackKingCoordinate(board[0][3].getCoordinates());
        setWhiteTurn(true);

    }

    public static void main(String[] args) {

        Board board = new Board();
        board.play();
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
                if (board[j - 1][i - 1].getPiece() != null) {
                    switch (board[j - 1][i - 1].getPiece().getName()) {
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

    private static boolean bothKingsAreAlive() {
        return areKingsAlive;
    }

    public void setAreKingsAlive(boolean kingStillAlive) {
        Board.areKingsAlive = kingStillAlive;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public void checkForCheck() {

        System.out.println("Checking for Check started");

        //initializing kings positions
        String whiteKingCoordinate = this.getWhiteKingCoordinate();
        String blackKingCoordinate = this.getBlackKingCoordinate();

        char XCoordinate;
        int YCoordinate;

        //getting the Kings Positions
        for (int j = 0; j < BOARDWIDTH; j++) {
            for (int i = 0; i < BOARDWIDTH; i++) {

                XCoordinate = (char) (i + 'a');
                YCoordinate = j + '1';
                String coordinate = XCoordinate + "" + YCoordinate;
                if (!this.getTile(coordinate).isEmpty()){
                    Piece piece = this.getTile(coordinate).getPiece();
                    if (piece.getName().length() == 1 && piece.getName().equals("K")) { //this might cause an issue as previously, it matched Kn in
                        // switch case with just K

                        if (piece.getColor() == Color.black) {

                            blackKingCoordinate = coordinate;
                        } else {
                            whiteKingCoordinate = coordinate;
                        }
                    }
                }else {
                    //this is for me
                }

            }
        }

        //checking if any of the enemy's pieces makes a check
        for (int j = 0; j < BOARDWIDTH; j++) {
            for (int i = 0; i < BOARDWIDTH; i++) {

                XCoordinate = (char) (i + 'a');
                YCoordinate = j + '1';
                String coordinate = XCoordinate + "" + YCoordinate;

                if (!this.getTile(coordinate).isEmpty()){

                    Piece piece = this.getTile(coordinate).getPiece();
//                boolean blackPieceCanKillWhiteKing = piece.canMove(getTile(whiteKingCoordinate)) && piece.getColor() == Color.BLACK;
//                boolean whitePieceCanKillBlackKing = piece.canMove(getTile(blackKingCoordinate)) && piece.getColor() == Color.WHITE;
                    boolean blackPieceCanKillWhiteKing =
                            piece.canMove(getTile(whiteKingCoordinate)) && piece.getColor() != getTile(whiteKingCoordinate).getPiece().getColor(); // this should not be a solution tho, it did solve the issue!
                    //this is
                    // causing error
                    // when i move
                    // e7-->e5
//                    System.out.println("whiteKingCoordinate: " + whiteKingCoordinate); //e8
//                    System.out.println(piece.getName()); //pawn
//                    System.out.println(piece.getTile().getCoordinates()); //e5
//                    System.out.println(piece.getColor()); //white
                    boolean whitePieceCanKillBlackKing =
                            piece.canMove(getTile(blackKingCoordinate)) && piece.getColor() != getTile(blackKingCoordinate).getPiece().getColor();

//                    System.out.println("piece.getName(): " + piece.getName());
//                    System.out.println("piece.getTile().getCoordinates(): " + piece.getTile().getCoordinates());
//                    System.out.println("blackPieceCanKillWhiteKing: " + blackPieceCanKillWhiteKing);


                    if(whitePieceCanKillBlackKing || blackPieceCanKillWhiteKing){

                        this.setCheck(true);
                    }else {
                        this.setCheck(false);
                    }
                }
            }
        }

        System.out.println("Checking for Check ended");
    }

    public Tile getTile(String index) {
        int xAxis = index.charAt(0) - 'a';
        int yAxis = index.charAt(1) - '1';
        return board[yAxis][xAxis];
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void setWhiteTurn(boolean whiteTurn) {
        this.whiteTurn = whiteTurn;
    }

    private static boolean isValidCoordinate(String coordinate) {

        if (coordinate.length() == 2) {

            return (coordinate.charAt(0) >= 'a' && coordinate.charAt(0) <= 'h') &&
                    (coordinate.charAt(1) >= '1' && coordinate.charAt(1) <= '8');
        } else {

            System.out.println("Please Enter a 2 characters String");
            return false;
        }

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

        getTile(sourceCoordinate).getPiece().move(destinationTile);

    }

    public String userSelectedCoordinate() {

        Scanner userInput = new Scanner(System.in);
        String coordinate;
        do {
            System.out.print("Enter Pieces' index  you want to move: ");
            coordinate = userInput.next().toLowerCase();
        }
        while (!isValidCoordinate(coordinate));
        return coordinate;

    }

    public void nextPlayerMove(String userSelectedCoordinate) {

        Scanner userNextMoveInput = new Scanner(System.in);

        if (!this.getTile(userSelectedCoordinate).isEmpty()) {

            Piece piece = this.getTile(userSelectedCoordinate).getPiece();

            boolean whitePlaysIfItIsItsTurn =
                    this.isWhiteTurn() && piece.getColor() == Color.WHITE;
            boolean blackPlaysIfItIsItsTurn =
                    !this.isWhiteTurn() && piece.getColor() == Color.BLACK;
            if (whitePlaysIfItIsItsTurn || blackPlaysIfItIsItsTurn) {
                System.out.println("The Piece you Selected is: " + this.getTile(userSelectedCoordinate).getPiece().getName());
                System.out.print("Enter the new Place: ");
                String userNextMove = userNextMoveInput.next();
                System.out.println();
                Board oldBoard = this;
                this.move(userSelectedCoordinate, userNextMove);
                this.checkForCheck(); //i think this have severe issues, like which player to check for TODO
                if (this.isCheck()){

                    System.out.println("This Move Is Refused Because Of Check, this is " + piece.getColor().toString() + " Color");
                    board = oldBoard.board; //idk what the fuck is this
                }
            } else {
                System.out.println("This Color can not play");
            }
        } else {
            System.out.println("Please Select a Piece, this tile does not contain any piece");
        }

    }

    public void play() {

        String userSelectedCoordinate;

        while (bothKingsAreAlive()) {
            this.printBoard();

            userSelectedCoordinate = this.userSelectedCoordinate();

            this.nextPlayerMove(userSelectedCoordinate);
        }


    }
}

import java.awt.*;
import java.util.Scanner;

public class Pawn extends Piece {

    private boolean pieceHadMoved;

    public Pawn(Color color) {
        pieceHadMoved = false;
        setColor(color);
    }

    @Override
    public void move(Tile destinationTile) {

        boolean isPawnInAttackMode = false;
        // this check is for making sure there is an enemy in that tile :)
        // that's all :) ok... you can go sleep, normally, when do you awake?
        if (!destinationTile.isEmpty() && color != destinationTile.getPiece().getColor()) {
            isPawnInAttackMode = true;
        }

        int max_Y_Steps = pieceHadMoved || isPawnInAttackMode ? 1 : 2;
        int user_X_Step = tile.user_X_Step(destinationTile);
        int user_Y_Step = tile.user_Y_Step(destinationTile);

        boolean isValidVerticalMove = (color == Color.BLACK && user_Y_Step <= max_Y_Steps) ||
                (color == Color.WHITE && user_Y_Step >= -max_Y_Steps);
        boolean isValidHorizontalMove = Math.abs(user_X_Step) == (isPawnInAttackMode ? 1 : 0);

        if (isValidHorizontalMove && isValidVerticalMove) {
            super.move(destinationTile);
            pieceHadMoved = true;

            if (destinationTile.getCoordinates().charAt(1)=='8' || destinationTile.getCoordinates().charAt(1)=='0'){

                System.out.println("Please Select Queen or Rook or Bishop or Knight to replace it with the pawn");

                Scanner userInput = new Scanner(System.in);

                String selectedPiece = userInput.next();

                while(true){
                    switch (selectedPiece.toUpperCase().charAt(0)){

                        case 'Q':
                            super.getTile().setPiece(new Queen(color));
                            break;
                        case 'R':
                            super.getTile().setPiece(new Rook(color));
                            break;
                        case 'B':
                            super.getTile().setPiece(new Bishop(color));
                            break;
                        case 'K':
                            super.getTile().setPiece(new Knight(color));
                            break;
                        default:
                            System.out.println("Please Enter a Valid Name");
                    }
                    break;
                }
            }
        }else {

            System.out.println("This is not a valid Pawn move");
        }

    }

    public String getName() {
        return "P";
    }
}

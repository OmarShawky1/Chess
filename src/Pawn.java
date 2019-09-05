import java.awt.*;
import java.util.Scanner;

public class Pawn extends Piece {

    private boolean pieceHadMoved;

    public Pawn(Color color) {
        pieceHadMoved = false;
        setColor(color);
    }

    public boolean canMove(Tile destinationTile) {

        boolean isPawnInAttackMode = false;
        if (!destinationTile.isEmpty() && color != destinationTile.getPiece().getColor()) {
            isPawnInAttackMode = true;
        }

        int max_Y_Steps = pieceHadMoved || isPawnInAttackMode ? 1 : 2;
        String destinationCoordinates = destinationTile.getCoordinates();
        String originalCoordinates = getTile().getCoordinates();
        int user_X_Step = destinationCoordinates.charAt(0) - originalCoordinates.charAt(0);
        int user_Y_Step = destinationCoordinates.charAt(1) - originalCoordinates.charAt(1);

        boolean isValidVerticalMove = (color == Color.BLACK && user_Y_Step <= max_Y_Steps) ||
                (color == Color.WHITE && user_Y_Step >= -max_Y_Steps);
        boolean isValidHorizontalMove = Math.abs(user_X_Step) == (isPawnInAttackMode ? 1 : 0);

        if (isValidHorizontalMove && isValidVerticalMove) {

            return true;
        } else {

            System.out.println("This is not a valid Pawn move: " + user_X_Step + "" + user_Y_Step);
        }
        return false;
    }

    public String getName() {
        return "P";
    }
}

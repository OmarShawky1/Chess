import java.awt.*;

public class Pawn extends Piece {

    public Pawn(Color color) {

        setColor(color);
    }


    @Override
    public String move(String newCoordinate) {

        int newXCoordinate = (int) newCoordinate.charAt(0);
        int newYCoordinate = newCoordinate.charAt(1);
        int oldXCoordinate = getOldCoordinate().charAt(0);
        int oldYCoordinate = getOldCoordinate().charAt(1);
        int changeInXCoordinate =
                Math.abs(newXCoordinate - oldXCoordinate);
        int changeInYCoordinate = newYCoordinate - oldYCoordinate;

        //all of the below do not check the tile before moving ahead
        //condition to just move forward
        if (changeInXCoordinate == 0 && changeInYCoordinate == 1) {

            return newCoordinate;
        }

        //condition to move forward and right or left at same time
        // to eat the enemy's piece


        return getOldCoordinate();
    }
}

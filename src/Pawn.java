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

        //condition to just move forward
        if (getColor()==Color.white){

            if (changeInXCoordinate == 0 && changeInYCoordinate == 1) {

                return newCoordinate;
            }
        }else{
            if (changeInXCoordinate == 0 && changeInYCoordinate == -1) {

                return newCoordinate;
            }
        }

        //condition to move forward and right or left at same time
        // to eat the enemy's piece


        return getOldCoordinate();
    }
}

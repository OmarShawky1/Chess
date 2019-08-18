import java.awt.*;

public class King extends Piece {


    public King(Color color) {

        setColor(color);
    }

    @Override
    public String move(String newCoordinate) {

        int newXCoordinate = newCoordinate.charAt(0);
        int newYCoordinate = newCoordinate.charAt(1);
        int oldXCoordinate = getOldCoordinate().charAt(0);
        int oldYCoordinate = getOldCoordinate().charAt(1);
        int changeInXCoordinate =
                Math.abs(newXCoordinate - oldXCoordinate);
        int changeInYCoordinate =
                Math.abs(newYCoordinate - oldYCoordinate);
        if ((changeInXCoordinate == 1 && changeInYCoordinate == 0) ||
                (changeInXCoordinate == 0 && changeInYCoordinate == 1) ||
                (changeInXCoordinate == 1 && changeInYCoordinate == 1)) {

            //there should be here some conditions mentioned in
            // piece class

            //should i check that his movement in in the board or not?

            return newCoordinate;
        }
        return getOldCoordinate();
    }

    public String getName (){

        return "K";
    }
}

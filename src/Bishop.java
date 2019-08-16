import java.awt.*;

public class Bishop extends Piece {


    public Bishop(Color color) {

        setColor(color);
    }

    @Override
    public String move(String newCoordinate) {

        //it is telling me here that there is duplicate code!

        int newXCoordinate = (int) newCoordinate.charAt(0);
        int newYCoordinate = newCoordinate.charAt(1);
        int oldXCoordinate = getOldCoordinate().charAt(0);
        int oldYCoordinate = getOldCoordinate().charAt(1);
        int changeInXCoordinate =
                Math.abs(newXCoordinate - oldXCoordinate);
        int changeInYCoordinate =
                Math.abs(newYCoordinate - oldYCoordinate);
        if (changeInXCoordinate == changeInYCoordinate) {

            //there should be here some conditions mentioned in
            // piece class

            //should i check that his movement in in the board or not?

            return newCoordinate;
        }
        return getOldCoordinate();
    }
}

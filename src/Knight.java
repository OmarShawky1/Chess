import java.awt.*;

public class Knight extends Piece {


    public Knight(Color color) {

        setColor(color);
    }


    @Override
    public String move(String oldCoordinate, String newCoordinate) {

        int newXCoordinate = newCoordinate.charAt(0);
        int newYCoordinate = newCoordinate.charAt(1);
        int oldXCoordinate = oldCoordinate.charAt(0);
        int oldYCoordinate = oldCoordinate.charAt(1);
        int changeInXCoordinate =
                Math.abs(newXCoordinate - oldXCoordinate);
        int changeInYCoordinate =
                Math.abs(newYCoordinate - oldYCoordinate);
        if ((changeInXCoordinate == 1 && changeInYCoordinate == 2) || (changeInXCoordinate == 2 && changeInYCoordinate == 1)) {

            //there should be here some conditions mentioned in
            // piece class

            //should i check that his movement in in the board or not?

            return newCoordinate;
        }
        return oldCoordinate;
    }

    public String getName (){

        return "Kn";
    }
}

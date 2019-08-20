import java.awt.*;

public class Bishop extends Piece {


    public Bishop(Color color) {

        setColor(color);
    }

    @Override
    public String move(String oldCoordinate, String newCoordinate) {

        //it is telling me here that there is duplicate code!

        int newXCoordinate = newCoordinate.charAt(0);
        int newYCoordinate = newCoordinate.charAt(1);
        int oldXCoordinate = oldCoordinate.charAt(0);
        int oldYCoordinate = oldCoordinate.charAt(1);
        int changeInXCoordinate =
                Math.abs(newXCoordinate - oldXCoordinate);
        int changeInYCoordinate =
                Math.abs(newYCoordinate - oldYCoordinate);
        if (changeInXCoordinate == changeInYCoordinate) {

            return newCoordinate;
        }
        return oldCoordinate;
    }

    public String getName (){

        return "B";
    }
}

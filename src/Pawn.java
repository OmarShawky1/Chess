import java.awt.*;

public class Pawn extends Piece {

    public Pawn(Color color) {

        setColor(color);
    }

    @Override
    public int move(int newCoordinate) {

        /*
        Conditions:
        1. making the Pawn move one/two steps forward

        2. making the Pawn move forward only (it does have a bug
        that the pawn from the black color for example cannot move
        his pawn except backward which is not wanted)

        3. there is no condition to let the Pawn move north east or
         west in case their is another piece he wants to eat
         */

        if ((newCoordinate % 8 == 0) &&
                (newCoordinate <= 16) &&
                (newCoordinate > getCoordinate())) {

            setCoordinate(newCoordinate);

        }
        return getCoordinate(); //this will cause an error,
        //as if the player plays a wrong play it will count the move
    }
}

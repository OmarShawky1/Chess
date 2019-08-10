import java.awt.*;

public class Bishop extends Piece {


    public Bishop(Color color) {

        setColor(color);
    }

    @Override
    public int move(int newCoordinate) {

        /*
        Conditions:
        1. Bishop moves towards the edges so he moves either north east
        or north west or south east or south west, all the cases are
        either divisible by 7 (as for left) or 9 for the right

        2. The Second condition is the limit for the piece not to
        exceed the number of tiles in the board

        3. Third condition is to check that the new place is not
        the old place
        */
        if (((newCoordinate % 9 == 0) ||
                (newCoordinate % 7 == 0)) &&
                (newCoordinate <= 63) &&
                (newCoordinate != getCoordinate())) {

            setCoordinate(newCoordinate);

        }

        return getCoordinate();
    }
}

import java.awt.*;

public class King extends Piece {


    public King(Color color) {

        setColor(color);
    }

    @Override
    public int move(int newCoordinate) {

        /*
        Conditions:
        1. making the king able to move forward (i.e. 8 tiles) or
        left (i.e. 7) or right (i.e. 9)

        2. The Second condition is the limit for the piece not to
        exceed the number of tiles in the board

        3. Third condition is to check that the new place is not
        the old place
        */

        if (((newCoordinate%8==0)||
                (newCoordinate%7==0)||
                (newCoordinate%9==0))&&
                (newCoordinate<=63)&&
                (newCoordinate!=getCoordinate())){

            setCoordinate(newCoordinate);
        }
        return getCoordinate();
    }
}

import java.awt.*;

public class Rook extends Piece {

    public Rook(Color color) {

        setColor(color);
    }

    @Override
    public int move(int newCoordinate) {

        /*
        Conditions:
        1. first condition to let the Rook move up and down
        Bugs: (their
        is a problem here, moving right or left means moving one
        tile, and i cannot get the remainder of 1 tile, it will
        always be true)
        (this condition lets the rook move freely even if their is
        a piece in it's way, it will not block it's movement)

        2. limiting the movement of the piece to the boarders of
        the board

        3. Third Condition checks if the new place is same as the old


         */

        if ((newCoordinate % 8 == 0) &&
                (newCoordinate <= 63) &&
                (newCoordinate != getCoordinate())) {

            setCoordinate(newCoordinate);

        }

        return getCoordinate();
    }
}

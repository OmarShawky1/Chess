import java.awt.*;

public class Rook extends Piece {

    public Rook(Color color) {

        setColor(color);
    }

    @Override
    public void move(Tile destinationTile) {

//        int newXCoordinate = newCoordinate.charAt(0);
//        int newYCoordinate = newCoordinate.charAt(1);
//        int oldXCoordinate = oldCoordinate.charAt(0);
//        int oldYCoordinate = oldCoordinate.charAt(1);
//        int changeInXCoordinate =
//                Math.abs(newXCoordinate - oldXCoordinate);
//        int changeInYCoordinate =
//                Math.abs(newYCoordinate - oldYCoordinate);
//        if ((changeInXCoordinate == 0 && changeInYCoordinate > 0) ||
//                (changeInXCoordinate > 0 && changeInYCoordinate == 0)) {
//
//            //there should be here some conditions mentioned in
//            // piece class
//
//            //should i check that his movement in in the board or not?
//
//            return newCoordinate;
//        }
//        return oldCoordinate;
    }

    public String getName (){

        return "R";
    }
}

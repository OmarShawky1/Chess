import java.awt.*;

public class Rook extends Piece {

    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean canMove(Tile destinationTile) {
        return false;
    }

    //    public boolean canMove(Tile destinationTile) {
//        String direction = super.getDirection(destinationTile);
//        boolean thisIsARookMove = direction.equals("NO") || direction.equals("SO") ||
//                direction.equals("EA") || direction.equals("WE");
//
//        System.out.println("Direction:" + direction.equals("SO"));
//
//        if (thisIsARookMove) {
//
//            if (super.pathIsEmptyAndDestinationIsFree(destinationTile)) {
//
//                return true;
//            }
//        } else {
//            System.out.println("This is not a valid Rook move");
//        }
//        return false;
//    }

    public String getShortName() {
        return "R";
    }
}

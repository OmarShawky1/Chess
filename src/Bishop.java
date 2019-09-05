import java.awt.*;

public class Bishop extends Piece {


    public Bishop(Color color) {

        setColor(color);

    }

    public boolean canMove (Tile destinationTile){


        String direction = super.getDirection(destinationTile);
        boolean thisIsABishopMove = direction.equals("NE") || direction.equals("SE") ||
                direction.equals("NW") || direction.equals("SW");
        if (thisIsABishopMove) {
            if (super.pathIsEmptyAndDestinationIsFree(destinationTile)) {

                return true;
            }
        } else {

            System.out.println("This is Not A Valid Bishop Move");
        }
        return false;
    }

    public String getName() {

        return "B";
    }

}


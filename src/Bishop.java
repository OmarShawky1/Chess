import java.awt.*;

public class Bishop extends Piece {


    public Bishop(Color color) {

        setColor(color);

    }

    @Override
    public void move(Tile destinationTile) {

        String direction = super.getDirection(destinationTile);
        boolean thisIsABishopMove = direction.equals("NE") || direction.equals("SE") ||
                direction.equals("NW") || direction.equals("SW");
        if (thisIsABishopMove) {

            if (super.pathIsEmptyAndDestinationIsFree(destinationTile)) {

                super.move(destinationTile);

            }
        } else {

            System.out.println("This is Not A Valid Bishop Move");
        }
    }

    public String getName() {

        return "B";
    }

}


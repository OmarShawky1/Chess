import java.awt.*;

public class King extends Piece {


    public King(Color color) {

        setColor(color);
    }

    public boolean canMove (Tile destinationTile){

        String origin = tile.getCoordinates();
        String destination = destinationTile.getCoordinates();

        int changeInX = Math.abs(destination.charAt(0) - origin.charAt(0));
        int changeInY = Math.abs(destination.charAt(1) - origin.charAt(1));

        if (changeInX <= 1 && changeInY <= 1){

            if (destination.isEmpty() || !destinationContainsAlly(destinationTile)){ //this was previosly pathIsEmptyAndDestinationIsFree

                return true;
            }
        }else{

            System.out.println("This is not a king Move");
        }

        return false;
    }

    public String getName() {

        return "K";
    }
}

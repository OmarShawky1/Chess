import java.awt.*;

public class Knight extends Piece {


    public Knight(Color color) {

        setColor(color);
    }

    public boolean canMove(Tile destinationTile) {

        String origin = tile.getCoordinates();
        String destination = destinationTile.getCoordinates();

        int changeInX = Math.abs(destination.charAt(0) - origin.charAt(0));
        int changeInY = Math.abs(destination.charAt(1) - origin.charAt(1));

        if ((changeInX <= 2 && changeInY <= 1) || (changeInX <= 1 && changeInY <= 2)) {

            if (destinationTile.isEmpty() && !destinationContainsAlly(destinationTile)) {

                return true;
            }
        } else {

            System.out.println("This is not a Knight Move");
        }

        return false;
    }

    public String getName() {

        return "Kn";
    }
}

import java.awt.*;

public class Knight extends Piece {


    public Knight(Color color) {
        super(color);
    }

    @Override
    public boolean canMove(Tile destinationTile) {

        int xDist = Math.abs(tile.xDiffFrom(destinationTile));
        int yDist = Math.abs(tile.yDiffFrom(destinationTile));
        boolean validKnightMove = (xDist == 2 && yDist == 1) || (xDist == 1 && yDist == 2);

        return validKnightMove && super.canMove(destinationTile);
    }

    public String getInitial() {

        return "Kn";
    }
}

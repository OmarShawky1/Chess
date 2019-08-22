import java.awt.*;

public class Pawn extends Piece {

    private boolean hadMoved;

    public Pawn(Color color) {
        hadMoved = false;
        setColor(color);
    }

    @Override
    public void move(Tile destinationTile) {
        boolean isPawnInAttackMode = false;
        if (!destinationTile.isEmpty() && color != destinationTile.getPiece().getColor()) {
            isPawnInAttackMode = true;
        }

        int maxYSteps = hadMoved || isPawnInAttackMode? 1: 2;
        int xDiff = tile.xDistanceTo(destinationTile);
        int yDiff = tile.yDistanceTo(destinationTile);

        boolean isValidVerticalMove = (color == Color.BLACK && yDiff <= maxYSteps) ||
                (color == Color.WHITE && yDiff <= -maxYSteps);
        boolean isValidHorizontalMove = Math.abs(xDiff) == (isPawnInAttackMode? 1: 0);
        if (isValidHorizontalMove && isValidVerticalMove) { /* valid move for a pawn */
            super.move(destinationTile);
            hadMoved = true;
        }
    }

    public String getName (){
        return "P";
    }
}

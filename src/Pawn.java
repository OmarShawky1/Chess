import java.awt.*;

public class Pawn extends Piece {

    private boolean hadMoved;

    public Pawn(Color color) {
        super(color);
        hadMoved = false;
    }

    public boolean canMove(Tile destinationTile) {
        boolean isPawnInAttackMode = false;
        if (!destinationTile.isEmpty() && color != destinationTile.getPiece().getColor()) {
            isPawnInAttackMode = true;
        }

        int maxYSteps = hadMoved || isPawnInAttackMode? 1: 2;
        int xDiff = tile.xDiffFrom(destinationTile);
        int yDiff = tile.yDiffFrom(destinationTile);

        boolean isCorrectVerticalDisplacement = (color == Color.BLACK && yDiff <= maxYSteps) ||
                (color == Color.WHITE && yDiff >= -maxYSteps);
        boolean isCorrectHorizontalDisplacement = Math.abs(xDiff) == (isPawnInAttackMode? 1: 0);

        if (isCorrectVerticalDisplacement && isCorrectHorizontalDisplacement) { /* valid move for a pawn */
            hadMoved = true;
            return super.canMove(destinationTile);
        } else {
            return false;
        }
    }

    public String getShortName() {
        return "P";
    }
}

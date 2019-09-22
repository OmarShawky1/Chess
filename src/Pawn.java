import java.awt.*;

public class Pawn extends Piece {

    private boolean hadMoved;

    public Pawn(Color color) {
        super(color);
        hadMoved = false;
    }

    public boolean canMove(Tile destinationTile) {

        boolean isPawnInAttackMode = false;
        if (!destinationTile.isEmpty() && color != destinationTile.getPiece().getColor()) {//this causes an error if the tile is to the
            // right for example
            isPawnInAttackMode = true;
        }
        int maxYSteps = hadMoved || isPawnInAttackMode ? 1 : 2;
        int xDiff = tile.xDiffFrom(destinationTile);//0
        int yDiff = tile.yDiffFrom(destinationTile);

        boolean isCorrectBlackPawnMove = (color == Color.BLACK && yDiff <= maxYSteps && yDiff > 0);
        boolean isCorrectWhitePawnMove = (color == Color.WHITE && yDiff >= -maxYSteps && yDiff < 0);
        boolean isCorrectVerticalMove = isCorrectBlackPawnMove || isCorrectWhitePawnMove;
        boolean isCorrectHorizontalMove = Math.abs(xDiff) == (isPawnInAttackMode ? 1 : 0);

        if (isCorrectVerticalMove && isCorrectHorizontalMove) { /* valid move for a pawn */
            hadMoved = true;
            return super.canMove(destinationTile);
        } else {
            return false;
        }
    }
    public String getInitial() {
        return "P";
    }
}

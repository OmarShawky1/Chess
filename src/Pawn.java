import java.awt.*;

public class Pawn extends Piece {

    private int numOfMovements;
    private int eatEnPassingDirection;

    public Pawn(Color color) {
        super(color);
        eatEnPassingDirection = 0;
        numOfMovements = 0;
    }

    public boolean canMove(Tile destinationTile) {
        boolean isPawnInAttackMode = false;
        if (!destinationTile.isEmpty() && color != destinationTile.getPiece().getColor()) {
            isPawnInAttackMode = true;
        }

        /* Determine if it can eat on passing */
        int xDiff = tile.xDiffFrom(destinationTile);
        int xDirection = xDiff == 0? 0: xDiff/ Math.abs(xDiff);

        Tile tilePassingBy = tile.getNeighbourTile(xDirection, 0);
        Piece piecePassingBy = tilePassingBy != null? tilePassingBy.getPiece(): null;

        if (piecePassingBy instanceof Pawn && piecePassingBy.getColor() != color &&
                ((Pawn) piecePassingBy).numOfMovements == 1) {
            eatEnPassingDirection = xDirection;
            isPawnInAttackMode = true;
        }

        int yDiff = tile.yDiffFrom(destinationTile);
        int maxYSteps = (numOfMovements > 0 || isPawnInAttackMode)? 1: 2;
        boolean isCorrectVerticalMove = (color == Color.BLACK && yDiff <= maxYSteps && yDiff > 0) ||
                (color == Color.WHITE && yDiff >= -maxYSteps && yDiff < 0);

        boolean isCorrectHorizontalMove = Math.abs(xDiff) == (isPawnInAttackMode? 1 : 0);

        if (isCorrectVerticalMove && isCorrectHorizontalMove && super.canMove(destinationTile)) {
            return true;
        } else {
            eatEnPassingDirection = 0;
            return false;
        }
    }

    void move(Tile destinationTile) {
        if (eatEnPassingDirection != 0) {
            Tile tilePassedBy = tile.getNeighbourTile(eatEnPassingDirection, 0);
            Piece piecePassedBy = tilePassedBy.getPiece();
            if (piecePassedBy != null) {
                piecePassedBy.setTile(null);
                tilePassedBy.setPiece(null);
            }
        }

        tile.setPiece(null);
        destinationTile.setPiece(this);

        numOfMovements++;
    }
}

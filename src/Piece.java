import java.awt.*;


public abstract class Piece {

    protected Color color;
    protected Tile tile;

    public Piece(Color color) {
        this.color = color;
    }

    Color getColor() {
        return color;
    }

    public abstract String getShortName();

    void setTile(Tile tile) {
        this.tile = tile;
    }

    public boolean canMove(Tile destinationTile) {
        boolean destinationContainsAlly = !destinationTile.isEmpty() &&
                destinationTile.getPiece().getColor() == color;

        Tile originalTile = tile;
        Piece originalPiece = destinationTile.getPiece();

        move(destinationTile);
        boolean willOwnPlayerKingBeChecked = tile.getBoard().getKing(color).isBeingChecked();

        /* relocate the pieces back to their original positions */
        move(originalTile);
        destinationTile.setPiece(originalPiece);

        return !destinationContainsAlly && !willOwnPlayerKingBeChecked;
    }

    void move(Tile destinationTile) {
        tile.setPiece(null);
        destinationTile.setPiece(this);
    }

    private boolean isPathClearTowards(Tile destTile) {
        int xDiff = tile.xDiffFrom(destTile);
        int yDiff = tile.yDiffFrom(destTile);
        int xDirection = xDiff == 0? 0: xDiff/ Math.abs(xDiff);
        int yDirection = yDiff == 0? 0: yDiff/ Math.abs(yDiff);

        Tile tileToCheck = tile.getNeighbourTile(xDirection, yDirection);
        while (tileToCheck != destTile) {
            if (!tileToCheck.isEmpty())
                return false;
            tileToCheck = tileToCheck.getNeighbourTile(xDirection, yDirection);
        }
        return true;
    }

    boolean isCorrectCornerMoveTowards(Tile destTile) {
        int xDist = Math.abs(tile.xDiffFrom(destTile));
        int yDist = Math.abs(tile.yDiffFrom(destTile));

        return xDist == yDist && isPathClearTowards(destTile);
    }

    boolean isCorrectStraightMoveTowards(Tile destTile) {
        return (tile.xDiffFrom(destTile) == 0 || tile.yDiffFrom(destTile) == 0) &&
                    isPathClearTowards(destTile);
    }
}

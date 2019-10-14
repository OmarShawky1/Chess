import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Piece {

    protected Color color;
    protected Tile tile;

    protected Image image;

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    void setTile(Tile tile) {
        this.tile = tile;
    }

    public boolean canMove(Tile destinationTile) {
        boolean destinationContainsAlly = !destinationTile.isEmpty() &&
                destinationTile.getPiece().getColor() == color;

        Tile originalTile = tile;
        King playerOwnKing = tile.getBoard().getKing(color);

        /* Check if the player's own king will be checked if this piece were moved out of the way. */
        tile.setPiece(null);
        tile = null;

        boolean willOwnPlayerKingBeChecked = playerOwnKing.isBeingChecked();

        tile = originalTile;
        tile.setPiece(this);

        return !destinationContainsAlly && !willOwnPlayerKingBeChecked;
    }

    void move(Tile destinationTile) {
        tile.setPiece(null);
        destinationTile.setPiece(this);
    }

    private boolean isPathClearTowards(Tile destTile) {
        int xDiff = tile.xDiffFrom(destTile);
        int yDiff = tile.yDiffFrom(destTile);
        int xDirection = xDiff == 0 ? 0 : xDiff / Math.abs(xDiff);
        int yDirection = yDiff == 0 ? 0 : yDiff / Math.abs(yDiff);

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

    public Image getImage() {
        return image;
    }
}

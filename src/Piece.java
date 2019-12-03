import javafx.scene.image.Image;

public abstract class Piece {

    protected String color;
    protected Tile tile;
    Image image;

    public Piece(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    void setTile(Tile tile) {
        this.tile = tile;
    }

    public boolean canMove(Tile destinationTile) {

        // Can move is the general steps that any piece should follow by. They are:
        // 1- Destination does not contain an Ally
        // 2- moving to Destination will not result in a check on his own king (the piece blocks a valid check from opponent)

        //this condition is important, this is used when doing internal functions such as isAlive or isBeingChecked
        boolean destinationContainsAlly = !destinationTile.isEmpty() &&
                destinationTile.getPiece().getColor().equalsIgnoreCase(color);

        if (destinationContainsAlly) {
            return false;
        }

        /* Check if the player's own king will be checked if this piece were moved out of the way. */
        Tile oldTile = tile;
        King king = tile.getBoard().getKing(color);
        Piece oldPiece = tile.getPiece();
        Piece oldDestinationPiece = destinationTile.getPiece();

        tile.setPiece(null);
        tile = destinationTile;
        tile.setPiece(oldPiece);

        boolean willOwnPlayerKingBeChecked = king.isBeingChecked();

        tile = oldTile;
        tile.setPiece(oldPiece);
        destinationTile.setPiece(oldDestinationPiece);

        return !willOwnPlayerKingBeChecked;
    }

    public void move(Tile destinationTile) {
        tile.setPiece(null);
        destinationTile.setPiece(this);

    }

    boolean isPathClearTowards(Tile destTile) {
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

    Image getImage() {
        return image;
    }
}
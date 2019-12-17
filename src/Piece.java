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
        if (king.isBeingChecked()){
            king.isChecked = false;
        }

        tile = oldTile;
        tile.setPiece(oldPiece);
        destinationTile.setPiece(oldDestinationPiece);

        return !willOwnPlayerKingBeChecked && cannotCheckWhenChecked();
    }

    public void move(Tile destinationTile) {
        tile.setPiece(null);
        destinationTile.setPiece(this);
    }

    boolean isPathClearTowards(Tile destinationTile) {
        int xDiff = tile.xDiffFrom(destinationTile);
        int yDiff = tile.yDiffFrom(destinationTile);
        int xDirection = xDiff == 0 ? 0 : xDiff / Math.abs(xDiff);
        int yDirection = yDiff == 0 ? 0 : yDiff / Math.abs(yDiff);

        Tile tileToCheck = tile.getNeighbourTile(xDirection, yDirection);
        while (tileToCheck != destinationTile) {
            if (!tileToCheck.isEmpty())
                return false;
            tileToCheck = tileToCheck.getNeighbourTile(xDirection, yDirection);
        }
        return true;
    }

    boolean isCorrectCornerMoveTowards(Tile destinationTile) {
        int xDist = Math.abs(tile.xDiffFrom(destinationTile));
        int yDist = Math.abs(tile.yDiffFrom(destinationTile));

        return xDist == yDist && isPathClearTowards(destinationTile);
    }

    boolean isCorrectStraightMoveTowards(Tile destinationTile) {
        return (tile.xDiffFrom(destinationTile) == 0 || tile.yDiffFrom(destinationTile) == 0) &&
                isPathClearTowards(destinationTile);
    }

    boolean cannotCheckWhenChecked(){
        King myKing = color.equals("white")? tile.getBoard().getKing("white"):tile.getBoard().getKing("black");
        return !myKing.isChecked;
    }

    Image getImage() {
        return image;
    }
}
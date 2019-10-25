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

        // Can move is the general steps that any piece should follow by. They are:
        // 1- Destination does not contain an Ally
        // 2- moving to Destination will not result in a check on his own king (the piece blocks a valid check from opponent)

        //this condition is important, this is used when doing internal functions such as isAlive or isBeingChecked
        boolean destinationContainsAlly = !destinationTile.isEmpty() &&
                destinationTile.getPiece().getColor() == color;

        if (destinationContainsAlly){
            return false;
        }

        /* Check if the player's own king will be checked if this piece were moved out of the way. */

        King king = tile.getBoard().getKing(color);
        Tile[][] oldBoard = tile.getBoard().getBoard();
        Tile oldTile = tile;
        //not sure why this is useful but without the previous line, Pieces where repeated in the board
        //this is why i putted this line to study more why the king isAlive is causing bugs
        Piece oldPiece = tile.getPiece();
        tile = destinationTile;
        Piece oldDestinationPiece = tile.getPiece();
        tile.setPiece(oldPiece);
        boolean willOwnPlayerKingBeChecked = king.isBeingChecked();
        tile.getBoard().setBoard(oldBoard);
        tile = oldTile;
        destinationTile.setPiece(oldDestinationPiece);
        tile.setPiece(oldPiece);

        //Start of Old Way
        //saving original tile contents
//        Tile sourceTile = tile;
//        King king = tile.getBoard().getKing(color);
//
//        tile.setPiece(null);
//        tile = destinationTile;
//
////        System.out.println("i am \"canMove\" in Piece and i will call \"isBeingChecked\"");
//        boolean willOwnPlayerKingBeChecked = king.isBeingChecked();
//
//        tile = sourceTile;
//        tile.setPiece(this);
        // End of Old Way

//        if (tile.getPiece() instanceof Queen) {
//            System.out.println("i am a queen and returned " + (!destinationContainsAlly && !willOwnPlayerKingBeChecked) + " from canMove " +
//                    "in Piece");
//        }
        return !willOwnPlayerKingBeChecked;
    }

    public void move(Tile destinationTile) {
        tile.setPiece(null);
        destinationTile.setPiece(this);
//        if (tile.getPiece() instanceof Queen){
//                System.out.println("I am a queen and i successfully moved to new tile which is " + tile.getCoordinates());
//        }
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

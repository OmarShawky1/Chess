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

    public abstract String getInitial();

    void setTile(Tile tile) {
        this.tile = tile;
    }

    public boolean canMove(Tile destinationTile) {

        boolean destinationContainsAlly = !destinationTile.isEmpty() && destinationTile.getPiece().getColor() == color;

        Tile originalTile = tile;
        Piece destinationTilePiece = destinationTile.getPiece();
        Tile enpassantOriginalTile;
        boolean willPlayerCheckHimself = false;
        if (!destinationTile.isEmpty() && originalTile.getPiece().getInitial().equalsIgnoreCase("P")){
            int x = destinationTile.getCoordinates().getX();
            int y = tile.getCoordinates().getY();
            Coordinate coordinate = new Coordinate(x,y);
            enpassantOriginalTile = tile.getBoard().getTile(coordinate);
            if (!enpassantOriginalTile.isEmpty() && enpassantOriginalTile.getPiece().getInitial().equalsIgnoreCase("P")){
                Piece pawn = enpassantOriginalTile.getPiece();
                move(destinationTile);
                willPlayerCheckHimself = tile.getBoard().getKing(color).isBeingChecked();
                move(originalTile);
                destinationTile.setPiece(destinationTilePiece);
                enpassantOriginalTile.setPiece(pawn);
            }
        }else {
            move(destinationTile);
            willPlayerCheckHimself = tile.getBoard().getKing(color).isBeingChecked();
            move(originalTile);
            destinationTile.setPiece(destinationTilePiece);
        }



        return !destinationContainsAlly && !willPlayerCheckHimself;
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
}

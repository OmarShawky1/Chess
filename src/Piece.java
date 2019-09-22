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
        //old technique for canMove that uses moving each peace and then return it back, causes issue with en passant

//        boolean destinationContainsAlly = !destinationTile.isEmpty() && destinationTile.getPiece().getColor() == color;
//
//        Tile originalTile = tile;
//        Piece originalPiece = destinationTile.getPiece();
//
//        move(destinationTile);
//        boolean willPlayerCheckHimself = tile.getBoard().getKing(color).isBeingChecked();
//        move(originalTile);
//        destinationTile.setPiece(originalPiece);
//
//        return !destinationContainsAlly && !willPlayerCheckHimself;

        //this is my first try and i do not know why it is not working and I MUST KNOW WHY TODO

//        System.out.println("counter " + counter);
//        counter++;
//
//        System.out.println("I'm " + getClass().getName() + " at " + tile.getCoordinates() + " and i moved to " + destinationTile.getCoordinates());
//
//
//        Tile[][] oldBoard = tile.getBoard().getBoard();
//
//        boolean destinationContainsAlly = !destinationTile.isEmpty() && destinationTile.getPiece().getColor() == color;
//
//        move(destinationTile);
//        boolean willPlayerCheckHimself = tile.getBoard().getKing(color).isBeingChecked();
//
//        tile.getBoard().setBoard(oldBoard);
//
//        return !destinationContainsAlly && !willPlayerCheckHimself;


        /*This also gives an extremely big error although  i'm playing and moving in a temporary board*/

//        testBoard = tile.getBoard();

        Board testBoard = tile.getBoard();

        boolean destinationContainsAlly = !destinationTile.isEmpty() && destinationTile.getPiece().getColor() == color;

        testBoard.getTile(tile.getCoordinates()).getPiece().move(destinationTile);
        boolean willPlayerCheckHimself = testBoard.getTile(tile.getCoordinates()).getBoard().getKing(color).isBeingChecked();

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

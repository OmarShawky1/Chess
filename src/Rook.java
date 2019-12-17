import javafx.scene.image.Image;

public class Rook extends Piece {

    Rook(String color) {
        super(color);

        if (color.equalsIgnoreCase("white")) {
            this.image = new Image("White_Rook.png");
        } else {
            this.image = new Image("Black_Rook.png");
        }
    }

    King getMyKing() {
        return tile.getBoard().getKing(color);
    }

    boolean kingIsOnMyRight() {

        King myKing = getMyKing();
        return (myKing.tile.getCoordinates().getX() - tile.getCoordinates().getX()) > 0;
    }

    int xCoordinateBeforeKing() {
        Tile myKingTile = getMyKing().tile;
        Coordinate myKingCoordinate = myKingTile.getCoordinates();
        return kingIsOnMyRight() ? myKingCoordinate.getX() - 1 : myKingCoordinate.getX() + 1;
    }

    Tile tileBeforeKing() {

        int x = xCoordinateBeforeKing();
        int y = tile.getCoordinates().getY();
        Coordinate coordinatesBeforeKing = new Coordinate(x, y);
        return tile.getBoard().getTile(coordinatesBeforeKing);
    }

    boolean canCastle(Tile destinationTile) {


        Tile myKingTile = getMyKing().tile;
        Piece destinationTilePiece = destinationTile.getPiece();
        boolean destinationContainsMyKing = !destinationTile.isEmpty() && destinationTilePiece instanceof King && destinationTilePiece.getColor().equals(color);

        if (destinationContainsMyKing) {

            Coordinate myKingCoordinate = myKingTile.getCoordinates();

            boolean rookAndKingOnSameRow = tile.getCoordinates().getY() == myKingCoordinate.getY();
            boolean kingIsNotChecked = !getMyKing().isChecked;

            if (!getMyKing().hasMoved && rookAndKingOnSameRow && kingIsNotChecked) {

                return isCorrectStraightMoveTowards(destinationTile);
            }
        }
        return false;
    }

    @Override
    public boolean canMove(Tile destinationTile) {
        if (isCorrectStraightMoveTowards(destinationTile)) {

            if (canCastle(destinationTile)) {
                return canMoveCastleSpecial(destinationTile);
//                return true;
            }
            return super.canMove(destinationTile);
        }

        return false;
    }

    boolean canMoveCastleSpecial(Tile destinationTile) {

        // Can move is the general steps that any piece should follow by. They are:
        // 1- Destination does not contain an Ally
        // 2- moving to Destination will not result in a check on his own myKing (the piece blocks a valid check from opponent)
        //this condition is important, this is used when doing internal functions such as isAlive or isBeingChecked
        /* Check if the player's own myKing will be checked if this piece were moved out of the way. */
        Tile rookOldTile = tile;
        King myKing = (!destinationTile.isEmpty() && destinationTile.getPiece() instanceof King) ? (King) destinationTile.getPiece() : null;

        //move the rook
        this.tile = destinationTile;
        destinationTile.setPiece(this);

        //move king
        int x = xCoordinateBeforeKing();
        int y = tile.getCoordinates().getY();
        Coordinate newCoordinateForKing = new Coordinate(x, y);
        Tile newKingTile = tile.getBoard().getTile(newCoordinateForKing);
        if (myKing != null) {
            myKing.tile = newKingTile;
            newKingTile.setPiece(myKing);

            boolean willOwnPlayerKingBeChecked = myKing.isBeingChecked();
            //revert the isBeingChecked if it happened and it occurred only due to this canMove
            if (myKing.isChecked) {
                myKing.isChecked = false;
            }

            //return the rook
            this.tile = rookOldTile;
            rookOldTile.setPiece(this);

            //return the king
            myKing.tile.setPiece(null);
            myKing.tile = destinationTile;
            destinationTile.setPiece(myKing);

            return !willOwnPlayerKingBeChecked;
        }
        return false;
    }

    public void move(Tile destinationTile) {

        if (canCastle(destinationTile)) {

            Tile tileBeforeKing = tileBeforeKing();

            getMyKing().move(tileBeforeKing);
        }
        super.move(destinationTile);
    }

}

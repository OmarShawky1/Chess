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

        System.out.println("I Entered canCastle");

        Tile myKingTile = getMyKing().tile;
        boolean thereIsAKingInDestination = !destinationTile.isEmpty() && destinationTile.getPiece() instanceof King;
        boolean destinationContainsMyKing = getMyKing().getColor().equals(color);

        if (thereIsAKingInDestination && destinationContainsMyKing) {

            Coordinate myKingCoordinate = myKingTile.getCoordinates();

            boolean rookAndKingOnSameRow = tile.getCoordinates().getY() == myKingCoordinate.getY();
            boolean kingIsNotChecked = !getMyKing().isChecked;

//            System.out.println("!getMyKing().hasMoved: " + !getMyKing().hasMoved);
//            System.out.println("rookAndKingOnSameRow: " + rookAndKingOnSameRow);
//            System.out.println("kingIsNotChecked: " + kingIsNotChecked);
            if (!getMyKing().hasMoved && rookAndKingOnSameRow && kingIsNotChecked) {

                boolean destinationTileContainsMyKing = myKingCoordinate == destinationTile.getCoordinates();

                System.out.println("canCastle: " + (isCorrectStraightMoveTowards(tileBeforeKing()) && destinationTileContainsMyKing));

                return isCorrectStraightMoveTowards(tileBeforeKing()) && destinationTileContainsMyKing;
            }
        }
        return false;
    }

    @Override
    public boolean canMove(Tile destinationTile) {
        System.out.println("Rook at: " + tile.getCoordinates() + " trying to move to: " + destinationTile.getCoordinates());
//        System.out.println("isCorrectStraightMoveTowards(destinationTile): " + isCorrectStraightMoveTowards(destinationTile));
        if (isCorrectStraightMoveTowards(destinationTile)) {

            if (canCastle(destinationTile)) {
//            return canMoveCastleSpecial(destinationTile);
                return true;
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
        Tile oldTile = tile;
        King myKing = getMyKing();
        Piece thisPiece = tile.getPiece();

        tile.setPiece(null);
        tile = destinationTile;
        tile.setPiece(thisPiece);

        int x = xCoordinateBeforeKing();
        int y = tile.getCoordinates().getY();
        Coordinate newCoordinateForKing = new Coordinate(x, y);
        tile.getBoard().getTile(newCoordinateForKing).setPiece(myKing);
        myKing.tile = tile.getBoard().getTile(newCoordinateForKing);

        boolean willOwnPlayerKingBeChecked = myKing.isBeingChecked();
        if (myKing.isBeingChecked()) {
            myKing.isChecked = false;
        }

        tile = oldTile;
        tile.setPiece(thisPiece);
        destinationTile.setPiece(myKing);
        myKing.tile = destinationTile;

        return !willOwnPlayerKingBeChecked && cannotCheckWhenChecked();
    }

    public void move(Tile destinationTile) {

        System.out.println("I entered move in Rook");
        if (canCastle(destinationTile)) {

            System.out.println("I Entered canCastle destinationTile");
            Tile tileBeforeKing = tileBeforeKing();

            getMyKing().move(tileBeforeKing);
        }
        super.move(destinationTile);
    }

}

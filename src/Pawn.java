import java.awt.*;

public class Pawn extends Piece {

    private boolean hadMoved;
    private boolean firstTwoSteps;
    private boolean canEatMe;

    public Pawn(Color color) {
        super(color);
        hadMoved = false;
    }

    public boolean canMove(Tile destinationTile) {

        boolean isPawnInAttackMode = false;
        if (!destinationTile.isEmpty() && color != destinationTile.getPiece().getColor()) {//this causes an error if the tile is to the
            // right for example
            isPawnInAttackMode = true;
        }
        makeEnPassant(destinationTile);
        int maxYSteps = hadMoved || isPawnInAttackMode ? 1 : 2;
        int xDiff = tile.xDiffFrom(destinationTile);//0
        int yDiff = tile.yDiffFrom(destinationTile);

        boolean isACorrectBlackPawnMove = (color == Color.BLACK && yDiff <= maxYSteps && yDiff > 0);
        boolean isACorrectWhitePawnMove = (color == Color.WHITE && yDiff >= -maxYSteps && yDiff < 0);
        boolean isCorrectVerticalDisplacement = isACorrectBlackPawnMove || isACorrectWhitePawnMove;
        boolean isCorrectHorizontalDisplacement = Math.abs(xDiff) == (isPawnInAttackMode ? 1 : 0);

        if (isCorrectVerticalDisplacement && isCorrectHorizontalDisplacement) { /* valid move for a pawn */
            if (Math.abs(yDiff) == 2) {
                firstTwoSteps = true;
            } else {
                firstTwoSteps = false;
            }
            setCanEatMe(destinationTile);
            hadMoved = true;
            return super.canMove(destinationTile);
        } else {
            return false;
        }
    }

    boolean checkIfPawnIsNotAtTheCorner (Coordinate coordinate){
        Board board = tile.getBoard();
        if (coordinate.isValidCoordinate()) {
            Tile cornerTile = board.getTile(coordinate);
            Pawn cornerPawn = null;
            boolean thereIsACornerPawn = false;
            if (!cornerTile.isEmpty() && cornerTile.getPiece().getShortName().equals("P")) {

                thereIsACornerPawn = this.color != cornerTile.getPiece().getColor() ? true : false;
                cornerPawn = (Pawn) cornerTile.getPiece();
            }
            boolean canEatRightPawn = (thereIsACornerPawn && cornerPawn != null && cornerPawn.getCanEatMe()) ? true : false;
            if (canEatRightPawn) {
                return true;
            }
        }
    }

    public boolean getCanEatMe() {
        return canEatMe;
    }

    void setCanEatMe(Tile destinationTile) {

        /*if first two steps, you should scan for nearby enemy pawns
         * after scanning for them, if they are in this pawn right or left, we CanEatMe to true, when the other pawn tries to move behind
         * this pawn, it will check if their is a pawn down or up of the tile, and if yes, we see if canEatMe is true, then we see if we
         * can move, this can be "ored" with isPawnInAttackMode in the canMove*/

        if (this.firstTwoSteps) {
            int right = destinationTile.getCoordinates().getX() + 1;
            int left = destinationTile.getCoordinates().getX() - 1;
            int yAxis = destinationTile.getCoordinates().getY();
            Coordinate rightNeighborCoordinate = new Coordinate(right, yAxis);
            Coordinate leftNeighborCoordinate = new Coordinate(left, yAxis);
            Pawn rightPawn = null;
            Pawn leftPawn = null;
            if (rightNeighborCoordinate.isValidCoordinate()){
                Tile rightTile = tile.getBoard().getTile(rightNeighborCoordinate);
                rightPawn = (!rightTile.isEmpty() && rightTile.getPiece().getShortName().equals("P")) ? (Pawn) rightTile.getPiece() : null;

            }
            if (leftNeighborCoordinate.isValidCoordinate()){
                Tile leftTile = tile.getBoard().getTile(leftNeighborCoordinate);
                leftPawn = (!leftTile.isEmpty() && leftTile.getPiece().getShortName().equals("P")) ? (Pawn) leftTile.getPiece() : null;
            }


            this.canEatMe = (rightPawn != null || leftPawn != null) ? true : false;
        }
    }

    boolean canEnPassant(Tile destinationTile) {

        /*when moving to a corner, i must check that there is a Pawn before it in order to eat it and move to the destination or no*/
        Board board = tile.getBoard();
        Coordinate rightDownCorner, leftDownCorner;
        /*getting coordinates for nearby Pawn*/
        int right = destinationTile.getCoordinates().getX() + 1;
        int left = destinationTile.getCoordinates().getX() - 1;
        int down = color == Color.black ? destinationTile.getCoordinates().getY() - 1 : destinationTile.getCoordinates().getY() + 1;
        rightDownCorner = new Coordinate(right, down);
        leftDownCorner = new Coordinate(left, down);
        /* check if the pawn is not in the corner to proceed in the rest of the function*/
        if (rightDownCorner.isValidCoordinate()) {
            Tile rightDownCornerTile = board.getTile(rightDownCorner);
            Pawn rightPawn = null;
            boolean thereIsARightPawn = false;
            if (!rightDownCornerTile.isEmpty() && rightDownCornerTile.getPiece().getShortName().equals("P")) {

                thereIsARightPawn = this.color != rightDownCornerTile.getPiece().getColor() ? true : false;
                rightPawn = (Pawn) rightDownCornerTile.getPiece();
            }
            boolean canEatRightPawn = (thereIsARightPawn && rightPawn != null && rightPawn.getCanEatMe()) ? true : false;
            if (canEatRightPawn) {
                return true;
            }
        }
        if (leftDownCorner.isValidCoordinate()){
            Tile leftDownCornerTile = board.getTile(leftDownCorner);
            Pawn leftPawn = null;
            boolean thereIsALeftPawn = false;
            if (!leftDownCornerTile.isEmpty() && leftDownCornerTile.getPiece().getShortName().equals("P")) {
                thereIsALeftPawn = this.color != leftDownCornerTile.getPiece().getColor() ? true : false;
                leftPawn = (Pawn) leftDownCornerTile.getPiece();
            }
            boolean canEatLeftPawn = (thereIsALeftPawn && leftPawn != null && leftPawn.getCanEatMe()) ? true : false;
            if (canEatLeftPawn){
                return true;
            }

        }
        return false;
    }

    void makeEnPassant(Tile destinationTile) {

        /*when moving to a corner, i must check that there is a Pawn before it in order to eat it and move to the destination or no*/
        if (canEnPassant(destinationTile)) {
            int down = destinationTile.getCoordinates().getY();
            int horizontal = destinationTile.getCoordinates().getX();
            if (color == Color.BLACK) {

                down--;
            } else {
                down++;
            }

            Coordinate downCoordinate = new Coordinate(down, horizontal);
            Tile downTile = tile.getBoard().getTile(downCoordinate);
            downTile.setPiece(null);
        }

    }

    public String getShortName() {
        return "P";
    }
}

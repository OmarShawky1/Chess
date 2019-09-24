import java.awt.*;

public class Pawn extends Piece {

    private boolean hadMoved;
    private boolean canEnPassantMe = false;
    private int canEnPassantMeAt;

    public Pawn(Color color) {
        super(color);
        hadMoved = false;
    }

    public boolean canMove(Tile destinationTile) {

        boolean isPawnInAttackMode = false;
        if (!destinationTile.isEmpty() && color != destinationTile.getPiece().getColor()) {
            isPawnInAttackMode = true;
        }

        int movementCounts = tile.getBoard().getMovementCounts();
        boolean canMakeEnPassant = canMakeEnPassant(destinationTile, movementCounts);
        int maxYSteps = hadMoved || isPawnInAttackMode || canMakeEnPassant ? 1 : 2;
        int xDiff = tile.xDiffFrom(destinationTile);
        int yDiff = tile.yDiffFrom(destinationTile);

        boolean isCorrectVerticalBlackPawnMove = (color == Color.BLACK && yDiff <= maxYSteps && yDiff > 0);
        boolean isCorrectVerticalWhitePawnMove = (color == Color.WHITE && yDiff >= -maxYSteps && yDiff < 0);
        boolean isCorrectVerticalMove = isCorrectVerticalBlackPawnMove || isCorrectVerticalWhitePawnMove;
        boolean isCorrectHorizontalMove = Math.abs(xDiff) == (isPawnInAttackMode || canMakeEnPassant ? 1 : 0);

        if (isCorrectVerticalMove && isCorrectHorizontalMove && super.canMove(destinationTile)) { /* valid move for a pawn */
            hadMoved = true;
            setCanEnPassantMe(destinationTile, tile.getBoard().getMovementCounts());
//            return super.canMove(destinationTile);
            return true;

        }
        return false;
    }

    public boolean canMakeEnPassant(Tile destinationTile, int counter) {
//        System.out.println("I Reached canMakeEnPassant");
        int x = destinationTile.getCoordinates().getX();
        int y = tile.getCoordinates().getY();
        Coordinate coor = new Coordinate(x, y);
        Tile enpassantTile = tile.getBoard().getTile(coor);
        System.out.println("Coordinates of enpassantTile: " + enpassantTile.getCoordinates());
        boolean ifThereIsNearEnemyPawn =
                !enpassantTile.isEmpty() && enpassantTile.getPiece().getInitial().equalsIgnoreCase("P") && enpassantTile.getPiece().getColor() != color;
        Pawn nearEnemyPawn;
        System.out.println("ifThereIsNearEnemyPawn: " + ifThereIsNearEnemyPawn);
        if (ifThereIsNearEnemyPawn) {
            nearEnemyPawn = (Pawn) enpassantTile.getPiece();
            System.out.println("nearEnemyPawn: " + nearEnemyPawn.getClass().getName() + " and it's color: " + nearEnemyPawn.getColor());
            System.out.println("getCanEnPassantMe(): " + nearEnemyPawn.getCanEnPassantMe() + " board counter: " + tile.getBoard().getMovementCounts()+
                    " and counter: " + counter);
            if (nearEnemyPawn.getCanEnPassantMe() && getCanEnPassantMeCounter() == counter) {
                return true;
            }
        }
        return false;
    }

    public void setCanEnPassantMe(Tile destinationTile, int counter) {

        boolean myFirstTwoStepMovement = Math.abs(destinationTile.getCoordinates().getY() - tile.getCoordinates().getY()) == 2;
        if (myFirstTwoStepMovement) {
            int right = destinationTile.getCoordinates().getX() + 1;
            int left = destinationTile.getCoordinates().getX() - 1;
            int y = destinationTile.getCoordinates().getY();
            Coordinate rightCoor = new Coordinate(right, y);
            Coordinate leftCoor = new Coordinate(left, y);
            Tile rightTile = null;
            Tile leftTile = null;
            if (rightCoor.isValidCoordinate()){
                rightTile = tile.getBoard().getTile(rightCoor);
            }
            if (leftCoor.isValidCoordinate()){
                    leftTile = tile.getBoard().getTile(leftCoor);
            }

            boolean thereIsEnemyPawnOnMyRight =
                    rightTile != null && !rightTile.isEmpty() && rightTile.getPiece().getInitial().equalsIgnoreCase("P");
            boolean thereIsEnemyPawnOnMyLeft =
                    leftTile != null && !leftTile.isEmpty() && leftTile.getPiece().getInitial().equalsIgnoreCase("P");

            if ((thereIsEnemyPawnOnMyRight || thereIsEnemyPawnOnMyLeft)) {
                canEnPassantMe = true;
                canEnPassantMeAt = counter + 1;
            }
        }
    }

    public boolean getCanEnPassantMe() {
        return canEnPassantMe;
    }

    public int getCanEnPassantMeCounter() {
        return canEnPassantMeAt;
    }

    public String getInitial() {
        return "P";
    }
}

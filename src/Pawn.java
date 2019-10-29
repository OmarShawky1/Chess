import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Pawn extends Piece {

    private boolean firstTwoStepMovement;
    private boolean canEnPassantMe;


    public Pawn(Color color) {
        super(color);

        if (color == Color.WHITE) {
            this.image = new Image("White_Pawn.png");
        } else {
            this.image = new Image("Black_Pawn.png");
        }
        firstTwoStepMovement = false;
    }

    public boolean isCanEnPassantMe() {
        return canEnPassantMe;
    }

    public void setCanEnPassantMe(boolean canEnPassantMe) {
        this.canEnPassantMe = canEnPassantMe;
    }

    public boolean canMove(Tile destinationTile) {
        if (isForwardMove(destinationTile)) {
            return super.canMove(destinationTile);
        } else return isValidCornerMove(destinationTile);

    }

    private boolean isForwardMove(Tile destinationTile) {

        int yDiff = tile.yDiffFrom(destinationTile);
        int xDiff = tile.xDiffFrom(destinationTile);
        int maxYSteps = firstTwoStepMovement ? 1 : 2;

        boolean isCorrectVerticalMove = (color == Color.BLACK && yDiff <= maxYSteps && yDiff > 0) ||
                (color == Color.WHITE && yDiff >= -maxYSteps && yDiff < 0);
        boolean isCorrectHorizontalMove = Math.abs(xDiff) == 0;

        if (isCorrectVerticalMove && isCorrectHorizontalMove && destinationTile.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean isValidCornerMove(Tile destinationTile) {
        //if i can move to corner because there is a piece in the corner
        if (isCornerMove(destinationTile)) {
            // if the corner tile is empty, check if i can en passant
            if (!destinationTile.isEmpty()) {
                return true;
            } else return iCanEnPassant(destinationTile);
        }
        return false;
    }

    private boolean isCornerMove(Tile destinationTile) {
        int yDiff = tile.yDiffFrom(destinationTile);
        int xDiff = tile.xDiffFrom(destinationTile);

        boolean isCorrectVerticalMove = (color == Color.BLACK && yDiff == 1) || (color == Color.WHITE && yDiff == -1);
        boolean isCorrectHorizontalMove = Math.abs(xDiff) == 1;
        if(isCorrectVerticalMove && isCorrectHorizontalMove){
            return true;
        }
        return false;
    }

    private boolean iCanEnPassant(Tile destinationTile) {
        Pawn rightPawn = sidePawn(destinationTile, 1);
        Pawn leftPawn = sidePawn(destinationTile, -1);

        if ((rightPawn != null && rightPawn.isCanEnPassantMe()) || (leftPawn != null && leftPawn.isCanEnPassantMe())){
            return true;
        }
        return false;
    }

    private Pawn sidePawn(Tile destinationTile, int direction) {
        int side = tile.getCoordinates().getX() + direction;
        Coordinate sideCoor = new Coordinate(side, tile.getCoordinates().getY());
        Pawn sidePawn = null;

        if (sideCoor.isValidCoordinate()){
        Tile sideTile = tile.getBoard().getTile(sideCoor);
        Piece sidePiece = sideTile.isEmpty() ? null : sideTile.getPiece();
        sidePawn = sidePiece instanceof Pawn ? (Pawn) sidePiece : null;
        }
        return sidePawn;
    }

    public void move(Tile destinationTile) {

        Pawn rightPawn = sidePawn(destinationTile, 1);
        Pawn leftPawn = sidePawn(destinationTile, -1);

        int x = Math.abs(destinationTile.getCoordinates().getY() - tile.getCoordinates().getY());
        if (x == 2) {
            if (rightPawn != null || leftPawn != null) {
                canEnPassantMe = true;
            }
        }
        //eat the enPassant Pawn
        if (iCanEnPassant(destinationTile)){

            if (rightPawn != null && rightPawn.isCanEnPassantMe()){
                rightPawn.tile.setPiece(null);
            }
            if (leftPawn != null && leftPawn.isCanEnPassantMe()){
                leftPawn.tile.setPiece(null);
            }
        }
        super.move(destinationTile);
    }
}

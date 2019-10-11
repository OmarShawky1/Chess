import javafx.scene.paint.Color;

public class Pawn extends Piece {

    private boolean firstTwoStepMovement;
    private int eatEnPassingDirection;

    public Pawn(Color color) {
        super(color);
        eatEnPassingDirection = 0;
        firstTwoStepMovement = false;
    }

    public boolean canMove(Tile destinationTile) {
        //TODO el 3askry maybetra2ash
        //TODO pawn can move forward although if there is anything in frond of him

        /* Check if trying to attack an enemy */
        boolean isPawnInAttackMode = false;
        if (!destinationTile.isEmpty() && color != destinationTile.getPiece().getColor()) {
            isPawnInAttackMode = true;
        }

        /* Determine if trying to eat an enemy on the passing */
        int xDiff = tile.xDiffFrom(destinationTile);
        int xDirection = xDiff == 0 ? 0 : xDiff / Math.abs(xDiff);

        Tile tilePassingBy = tile.getNeighbourTile(xDirection, 0);
        Piece piecePassingBy = tilePassingBy != null ? tilePassingBy.getPiece() : null;

        if (piecePassingBy instanceof Pawn && piecePassingBy.getColor() != color &&
                ((Pawn) piecePassingBy).firstTwoStepMovement == true) {
            eatEnPassingDirection = xDirection;
            isPawnInAttackMode = true;
        } else {
            eatEnPassingDirection = 0;
        }

        /* Check correct movement for the Pawn */
        int yDiff = tile.yDiffFrom(destinationTile);
        int maxYSteps = (firstTwoStepMovement == true || isPawnInAttackMode) ? 1 : 2;

        boolean isCorrectVerticalMove = (color == Color.BLACK && yDiff <= maxYSteps && yDiff > 0) ||
                (color == Color.WHITE && yDiff >= -maxYSteps && yDiff < 0);
        boolean isCorrectHorizontalMove = Math.abs(xDiff) == (isPawnInAttackMode ? 1 : 0);

        return isCorrectVerticalMove &&
                isCorrectHorizontalMove &&
                super.canMove(destinationTile);
    }

    void move(Tile destinationTile) {
        if (eatEnPassingDirection != 0) {
            Tile tilePassedBy = tile.getNeighbourTile(eatEnPassingDirection, 0);
            Piece piecePassedBy = tilePassedBy.getPiece();

            if (piecePassedBy != null) {
                piecePassedBy.setTile(null);
                tilePassedBy.setPiece(null);
            }
        }
        eatEnPassingDirection = 0;

        tile.setPiece(null);
        destinationTile.setPiece(this);

//        boolean moved = Math.abs(destinationTile.getCoordinates().getY() - tile.getCoordinates().getY()) == 2;
//        System.out.println("moved: " + moved);
//        if (moved){
//            firstTwoStepMovement = true;
//        }
        System.out.println("Here");
        firstTwoStepMovement = true;


    }
}

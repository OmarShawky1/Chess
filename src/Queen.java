import javafx.scene.paint.Color;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color);
    }

    public boolean canMove(Tile destinationTile) {
        boolean isValidQueenMove = isCorrectCornerMoveTowards(destinationTile) ||
                isCorrectStraightMoveTowards(destinationTile);

        return isValidQueenMove && super.canMove(destinationTile);
    }
}

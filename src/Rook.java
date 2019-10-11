import javafx.scene.paint.Color;

public class Rook extends Piece {

    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean canMove(Tile destinationTile) {
        return isCorrectStraightMoveTowards(destinationTile) && super.canMove(destinationTile);
    }
}

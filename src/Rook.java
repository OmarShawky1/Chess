import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Rook extends Piece {

    public Rook(Color color) {
        super(color);

        if (color == Color.WHITE) {
            this.image = new Image("White_Rook.png");
        } else {
            this.image = new Image("Black_Rook.png");
        }
    }

    @Override
    public boolean canMove(Tile destinationTile) {
        if (isCorrectStraightMoveTowards(destinationTile)) {
            boolean superCanMove = super.canMove(destinationTile);
            return superCanMove;
        }
        return false;
    }
}

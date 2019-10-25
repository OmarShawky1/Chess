import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Bishop extends Piece {

    Bishop(Color color) {
        super(color);

        if (color == Color.WHITE) {
            this.image = new Image("White_Bishop.png");
        } else {
            this.image = new Image("Black_Bishop.png");
        }

    }

    public boolean canMove(Tile destinationTile) {

        if (isCorrectCornerMoveTowards(destinationTile)) {
            boolean superCanMove = super.canMove(destinationTile);

            return superCanMove;
        }
        return false;
    }
}


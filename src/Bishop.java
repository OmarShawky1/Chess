import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Bishop extends Piece {

    Bishop(String color) {
        super(color);

        if (color.equalsIgnoreCase("white")) {
            this.image = new Image("White_Bishop.png");
        } else {
            this.image = new Image("Black_Bishop.png");
        }

    }

    public boolean canMove(Tile destinationTile) {

        if (isCorrectCornerMoveTowards(destinationTile)) {

            return super.canMove(destinationTile);
        }
        return false;
    }
}


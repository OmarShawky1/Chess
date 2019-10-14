import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color);

        if (color == Color.WHITE){
            this.image = new Image("White_Queen.png");
        }else {
            this.image = new Image("Black_Queen.png");
        }
    }

    public boolean canMove(Tile destinationTile) {
        boolean isValidQueenMove = isCorrectCornerMoveTowards(destinationTile) ||
                isCorrectStraightMoveTowards(destinationTile);

        return isValidQueenMove && super.canMove(destinationTile);
    }
}

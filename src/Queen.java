import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Queen extends Piece {
    Queen(String color) {
        super(color);

        if (color.equalsIgnoreCase("white")){
            this.image = new Image("White_Queen.png");
        }else {
            this.image = new Image("Black_Queen.png");
        }
    }

    public boolean canMove(Tile destinationTile) {
        boolean isValidQueenMove = isCorrectCornerMoveTowards(destinationTile) ||
                isCorrectStraightMoveTowards(destinationTile);

        if (isValidQueenMove){
            //this is written that way because it was and will be used in so many calculations, so no need to do it over and over
            return super.canMove(destinationTile);
        }
        return false;
    }
}

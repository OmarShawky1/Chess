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

//        System.out.println("i was trying to move to destination tile: " + destinationTile.getCoordinates());
//
//        boolean superCanMove = super.canMove(destinationTile);
//        System.out.println("isValidQueenMove returned "+ isValidQueenMove + " and superCanMove returned " + superCanMove );
//
//        boolean queenMove = isValidQueenMove && superCanMove;
//        System.out.println("i returned " + queenMove + " from Queen");
//
//        return queenMove;
//        System.out.println("isValidQueenMove: " + isValidQueenMove);
        if (isValidQueenMove){
            //this is written that way because it was and will be used in so many calculations, so no need to do it over and over
            boolean superCanMove = super.canMove(destinationTile);
            return superCanMove;
        }
        return false;
    }
}

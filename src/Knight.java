import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Knight extends Piece {


    Knight(Color color) {
        super(color);

        if (color == Color.WHITE){
            this.image = new Image("White_Knight.png");
        }else {
            this.image = new Image("Black_Knight.png");
        }
    }

    @Override
    public boolean canMove(Tile destinationTile) {

        int xDist = Math.abs(tile.xDiffFrom(destinationTile));
        int yDist = Math.abs(tile.yDiffFrom(destinationTile));
        boolean validKnightMove = (xDist == 2 && yDist == 1) || (xDist == 1 && yDist == 2);

        if (validKnightMove){
            return super.canMove(destinationTile);
        }
        return false;
    }
}

import javafx.scene.paint.Color;

public class Bishop extends Piece {

    String string = null;

//    image = new Image("file:src/ChessPiece/White_Bishop.png");
    image = new Image(string);

    Bishop(Color color) {
        super(color);
    }

    public boolean canMove (Tile destinationTile){
        return isCorrectCornerMoveTowards(destinationTile) &&
                super.canMove(destinationTile);
    }
}


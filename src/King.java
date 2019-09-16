import java.awt.*;
import java.util.LinkedList;

public class King extends Piece {

    King(Color color) {
        super(color);
    }

    boolean isBeingChecked() {
        Color enemyColor = color == Color.WHITE? Color.BLACK: Color.WHITE;
        LinkedList<Piece> enemyPieces = tile.getBoard().getAllPiecesWithColor(enemyColor);

        for (Piece piece : enemyPieces) {
            if (piece.canMove(tile)) {
                return true;
            }
        }
        return false;
    }

    boolean isAlive() {


        LinkedList<Piece> army = tile.getBoard().getAllPiecesWithColor(color);

        /*For Each piece, move it to each place; then check if it the enemy's king can be unchecked, if it is unchecked return true*/
        for (Piece piece : army) {
            for (int i = 0; i < tile.getBoard().BOARD_WIDTH; i++) {
                for (int j = 0; j < tile.getBoard().BOARD_LENGTH; j++) {
                    Coordinate coordinates = new Coordinate(i, j);
                    if (piece.canMove(tile.getBoard().getTile(coordinates))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canMove (Tile destinationTile){
        int xDist = Math.abs(tile.xDiffFrom(destinationTile));
        int yDist = Math.abs(tile.yDiffFrom(destinationTile));

        return xDist <= 1 && yDist <= 1 && super.canMove(destinationTile);
    }

    public String getShortName() {
        return "K";
    }
}

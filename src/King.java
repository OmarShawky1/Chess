import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class King extends Piece {

    King(Color color) {
        super(color);

        if (color == Color.WHITE) {
            this.image = new Image("White_King.png");
        } else {
            this.image = new Image("Black_King.png");
        }
    }

    boolean isBeingChecked() {
        Color enemyColor = color == Color.WHITE ? Color.BLACK : Color.WHITE;
        LinkedList<Piece> enemyPieces = tile.getBoard().getAllPiecesWithColor(enemyColor);

        for (Piece piece : enemyPieces) {
            if (piece.canMove(tile)) {
                return true;
            }
        }
        return false;
    }

    boolean isAlive() {
        if (isBeingChecked()) {
            /* Check if the king can move anywhere to avoid the check mate */
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    Tile neighbourTile = tile.getNeighbourTile(i, j);
                    if (neighbourTile != null && canMove(neighbourTile))
                        return true;
                }
            }
            // TODO: check if any other piece can protect the king

            for (Piece piece : tile.getBoard().getAllPiecesWithColor(color)) {
                for (int i = 0; i < tile.getBoard().BOARD_LENGTH; i++) {
                    for (int j = 0; j < tile.getBoard().BOARD_WIDTH; j++) {
                        Coordinate coordinate = new Coordinate(i, j);
                        if (piece.canMove(tile.getBoard().getTile(coordinate))) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public boolean canMove(Tile destinationTile) {
        int xDist = Math.abs(tile.xDiffFrom(destinationTile));
        int yDist = Math.abs(tile.yDiffFrom(destinationTile));

        return xDist <= 1 && yDist <= 1 && super.canMove(destinationTile);
    }
}

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
        if (isBeingChecked()) {
            /* Check if the king can move anywhere to avoid the check mate */
            // TODO: check if any other piece can protect the king
            for (int i =-1; i <= 1; i++) {
                for (int j=-1; j <= 1; j++) {
                    Tile neighbourTile = tile.getNeighbourTile(i, j);
                    if (neighbourTile != null && canMove(neighbourTile))
                        return true;
                }
            }
            return false;
        } else {
            return true;
        }
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

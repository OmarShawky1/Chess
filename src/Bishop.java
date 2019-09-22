import java.awt.*;

public class Bishop extends Piece {

    Bishop(Color color) {
        super(color);
    }

    public boolean canMove (Tile destinationTile){
        return isCorrectCornerMoveTowards(destinationTile) &&
                super.canMove(destinationTile);
    }

    public String getInitial() {
        return "B";
    }
}


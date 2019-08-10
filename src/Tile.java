import java.awt.*;

public class Tile {

    int coordinate;
    Piece piece;
    Color color;
    boolean isEmpty;

    public boolean isTileEmpty() {

        return isEmpty;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int coordinate) {
        this.coordinate = coordinate;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {

        this.piece = piece;
        this.isEmpty = false;
    }

    public void setColor(Color color) {

        this.color = color;

    }

    public Tile(int coordinate) {

        this.coordinate = coordinate;
        this.isEmpty = true;
    }

}


import java.awt.*;

public class Tile {

    private String coordinate;
    private Piece piece;
    private Color color;

    private boolean isEmpty;

    public Tile(String coordinate) {

        this.coordinate = coordinate;
        this.isEmpty = true;
    }

    public boolean isTileEmpty() {

        return isEmpty;
    }

    public Piece getPiece() {

        return piece;
    }

    public void setPiece(Piece piece) {

        this.piece = piece;
        this.isEmpty = false;
    }

    public void setTileColor(Color color) {

        this.color = color;

    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getCoordinate() {

        return coordinate;
    }

    //unused and probably waste of lines
    public void setCoordinate(String coordinate) {

        this.coordinate = coordinate;
    }

}


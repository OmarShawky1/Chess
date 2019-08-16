import java.awt.*;

public class Tile {

    String xCoordinate;
    String yCoordinate; //string as to be concatinated with xCoor
    String coordinate;
    Piece piece;
    Color color;
    boolean isEmpty;

    public boolean isTileEmpty() {

        return isEmpty;
    }

    public Tile(String coordinate) {

        this.coordinate = coordinate;
        this.isEmpty = true;
    }

    public String getCoordinate() {
        return (xCoordinate + yCoordinate);
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

    public void setCoordinate(String xCoordinate,
                              String yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.coordinate = xCoordinate + yCoordinate;
    }

}


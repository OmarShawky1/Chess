import java.awt.*;

public abstract class Piece {


    private Color color;

    private int oldCoordinate;

    public int getCoordinate() {
        return oldCoordinate;
    }

    public void setCoordinate(int oldCoordinate) {
        this.oldCoordinate = oldCoordinate;
    }

    public Color getColor() {

        return color;
    }

    public void setColor(Color color) {

        this.color = color;
    }

    public abstract int move(int newCoordinate);

}

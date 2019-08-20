import java.awt.*;


public abstract class Piece {


    private Color color;

    public Color getColor() {

        return color;
    }

    public void setColor(Color color) {

        this.color = color;
    }


    public abstract String move(String oldCoordinate, String newCoordinate);

    public abstract String getName();

}

import java.awt.*;

//should i make in each piece constructor an initialization for
// it's coordinates?

public abstract class Piece {


    private Color color;

    private String oldCoordinate;

    public String getOldCoordinate() {

        return oldCoordinate;
    }

    public void setOldCoordinate(String oldCoordinate) {

        this.oldCoordinate = oldCoordinate;
    }

    public Color getColor() {

        return color;
    }

    public void setColor(Color color) {

        this.color = color;
    }



    public abstract String move(String newCoordinate);

}

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

    public boolean canMove(String oldCoordinate,
                           String newCoordinate) {

        //cannot move to his own same place
        return !oldCoordinate.equals(newCoordinate);//cannot move to a tile containing other piece from his own
        // army


        //cannot move if their is a check on the king unless he
        // will protect him


        //cannot move to a place beyond a piece

        //abstract movement of piece

    }

    public abstract String move(String newCoordinate);

}

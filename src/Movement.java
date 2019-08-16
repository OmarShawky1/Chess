//cannot see how this class is important or why did i make it
public abstract class Movement {

    public boolean canMove(String oldPlace, String newPlace) {

        //cannot move to his own same place
        return !oldPlace.equals(newPlace);//cannot move to a tile containing other piece from his own
        // army


        //cannot move if their is a check on the king unless he
        // will protect him


        //cannot move to a place beyond a piece

        //abstract movement of piece

    }


}

import java.awt.*;

public class Queen extends Piece {
    public Queen(Color color) {

        setColor(color);

    }

    @Override
    public void move(Tile destinationTile) {

        //ik that this condition checking is redundant, but i might change things later, so this saves me extra time from debugging issues
        String direction = super.getDirection(destinationTile);
        boolean thisIsAQueenMove = direction.equals("NE") ||direction.equals("NW") || direction.equals("SE") ||
                direction.equals("SW") || direction.equals("NO") ||direction.equals("SO") || direction.equals("EA") ||
                direction.equals("WE");
        if (thisIsAQueenMove){

            if (super.pathIsEmptyAndDestinationIsFree(destinationTile)){

                super.move(destinationTile);
            }
        }else {

            System.out.println("This is not a valid Queen Move");
        }

    }

    public String getName (){

        return "Q";
    }


}

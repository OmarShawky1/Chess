import java.awt.*;

public class Bishop extends Piece {


    public Bishop(Color color) {

        setColor(color);

    }

    @Override
    public void move(Tile destinationTile) {

        if (thereIsNoPieceInMyWay(destinationTile)) {
            if ((!destinationTile.isEmpty()) && (color != destinationTile.getPiece().getColor())) {
                super.move(destinationTile);
            }
        }
//        while (thereIsNoPieceInMyWay){
//            iWillMoveOneStep
//                    whenIFindAPieceInTheDestination,
//                    iWillCheckIfWeBothHaveSameColor,
//                    ifNot, iWillEatThePiece
//        }

    }

    private boolean thereIsNoPieceInMyWay(Tile destinationTile) {

        String origin = this.getTile().getCoordinates();
        String direction = null;

        int changeInX = destinationTile.getCoordinates().charAt(0) - tile.getCoordinates().charAt(0); //if negative then it moves west, else
        // east
        int changeInY = -(destinationTile.getCoordinates().charAt(1) - tile.getCoordinates().charAt(1)); //if negative then it moves south,
        // else north

        //setting the value for direction
        if (changeInX > 0 && changeInY > 0) {

            direction = "NE";
        } else if (changeInX > 0 && changeInY < 0) {

            direction = "SE";
        } else if (changeInX < 0 && changeInY > 0) {

            direction = "NW";
        } else if (changeInX < 0 && changeInY < 0) {

            direction = "SW";
        }


        //this is a counter used in the following while in order not to change an actual value
        StringBuilder coordinatesOfTileCounter = new StringBuilder(origin);
        StringBuilder tmpXCoor = new StringBuilder();
        StringBuilder tmpYCoor = new StringBuilder();

        System.out.println("coordinatesOfTileCounter Before the while: "+coordinatesOfTileCounter);
        System.out.println("destinationTile.getCoordinates() before the While: "+destinationTile.getCoordinates());
        //if we did not reach the destination tile, move one tile and get the the piece on it, to obvs check if the path is empty
        while (!coordinatesOfTileCounter.equals(destinationTile.getCoordinates())) {
            switch (direction) {

                case "NE":

                    tmpXCoor.append((char) (coordinatesOfTileCounter.charAt(0) + 1));
                    tmpYCoor.append((char) (coordinatesOfTileCounter.charAt(1) - 1));

                    coordinatesOfTileCounter.delete(0, coordinatesOfTileCounter.length());
                    coordinatesOfTileCounter.append(tmpXCoor);
                    coordinatesOfTileCounter.append(tmpYCoor);

                    tmpXCoor.delete(0, tmpXCoor.length());
                    tmpYCoor.delete(0, tmpYCoor.length());

                    System.out.println("coordinatesOfTileCounter NE: " + coordinatesOfTileCounter);
                    break;

                case "SE":

                    tmpXCoor.append((char) (coordinatesOfTileCounter.charAt(0) + 1));
                    tmpYCoor.append((char) (coordinatesOfTileCounter.charAt(1) + 1));

                    coordinatesOfTileCounter.delete(0, coordinatesOfTileCounter.length());
                    coordinatesOfTileCounter.append(tmpXCoor);
                    coordinatesOfTileCounter.append(tmpYCoor);

                    tmpXCoor.delete(0, tmpXCoor.length());
                    tmpYCoor.delete(0, tmpYCoor.length());

                    System.out.println("coordinatesOfTileCounter SE: " + coordinatesOfTileCounter);
                    break;

                case "NW":

                    tmpXCoor.append((char) (coordinatesOfTileCounter.charAt(0) - 1));
                    tmpYCoor.append((char) (coordinatesOfTileCounter.charAt(1) - 1));

                    coordinatesOfTileCounter.delete(0, coordinatesOfTileCounter.length());
                    coordinatesOfTileCounter.append(tmpXCoor);
                    coordinatesOfTileCounter.append(tmpYCoor);

                    tmpXCoor.delete(0, tmpXCoor.length());
                    tmpYCoor.delete(0, tmpYCoor.length());

                    System.out.println("coordinatesOfTileCounter NW: " + coordinatesOfTileCounter);
                    break;

                case "SW":

                    tmpXCoor.append((char) (coordinatesOfTileCounter.charAt(0) - 1));
                    tmpYCoor.append((char) (coordinatesOfTileCounter.charAt(1) + 1));

                    coordinatesOfTileCounter.delete(0, coordinatesOfTileCounter.length());
                    coordinatesOfTileCounter.append(tmpXCoor);
                    coordinatesOfTileCounter.append(tmpYCoor);

                    tmpXCoor.delete(0, tmpXCoor.length());
                    tmpYCoor.delete(0, tmpYCoor.length());

                    System.out.println("coordinatesOfTileCounter SW: " + coordinatesOfTileCounter);
                    break;

                default:
                    System.out.println("Something went wrong in the switch statement of the Bishop");

            }

            System.out.println("destinationTile.getCoordinates() after the switch: " + destinationTile.getCoordinates());
            System.out.println("coordinatesOfTileCounter after the switch: " + coordinatesOfTileCounter);
            if (!board.getTile(coordinatesOfTileCounter.toString()).isEmpty()) {

                return false;
            }

        }

        //checks that the destination it self does not contain a piece from the same army
        if ((!board.getTile(coordinatesOfTileCounter.toString()).isEmpty()) && (color == board.getTile(coordinatesOfTileCounter.toString()).getPiece().getColor())) {

            return false;
        }

        return true;

    }

    public String getName() {

        return "B";
    }

}


import java.awt.*;


public abstract class Piece {

    protected Color color;
    protected Tile tile;
    protected Board board;

    public Color getColor() {
        return color;
    }

    public Tile getTile() {
        return tile;
    }

    public Board getBoard() {
        return board;
    }

    public abstract String getName();

    public void setColor(Color color) {
        this.color = color;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getDirection(Tile destinationTile) {

        String origin = this.getTile().getCoordinates();
        String destination = destinationTile.getCoordinates();
        String direction = null;

        int changeInX = destination.charAt(0) - origin.charAt(0);
        int changeInY = -(destination.charAt(1) - origin.charAt(1));
        //setting the value for direction
        if (changeInX > 0 && changeInY > 0) {

            direction = "NE";
        } else if (changeInX > 0 && changeInY < 0) {

            direction = "SE";
        } else if (changeInX < 0 && changeInY > 0) {

            direction = "NW";
        } else if (changeInX < 0 && changeInY < 0) {

            direction = "SW";
        } else if (changeInX == 0 && changeInY != 0) {

            if (changeInY > 0) {

                direction = "NO";
            } else {

                direction = "SO";
            }
        } else if (changeInX != 0 && changeInY == 0) {

            if (changeInX > 0) {

                direction = "EA";
            } else {

                direction = "WE";
            }
        }

        System.out.println("direction from Direction in Piece: " + direction);
        return direction;
    }

    // i think this and the following should be implemented inside of Board
    public boolean isPathEmpty(Tile destinationTile) {

        String origin = this.getTile().getCoordinates();
        String direction = this.getDirection(destinationTile);

        //this is a counter used in the following while in order not to change an actual value
        StringBuilder coordinatesOfTileCounter = new StringBuilder(origin);
        StringBuilder tmpXCoor = new StringBuilder();
        StringBuilder tmpYCoor = new StringBuilder();
        //if we did not reach the destination tile, move one tile and get the the piece on it, to obviously check if the path is empty
        while (!coordinatesOfTileCounter.toString().equals(destinationTile.getCoordinates())) {
            switch (direction) {

                case "NE":

                    tmpXCoor.append((char) (coordinatesOfTileCounter.charAt(0) + 1));
                    tmpYCoor.append((char) (coordinatesOfTileCounter.charAt(1) - 1));

                    coordinatesOfTileCounter.delete(0, coordinatesOfTileCounter.length());
                    coordinatesOfTileCounter.append(tmpXCoor);
                    coordinatesOfTileCounter.append(tmpYCoor);

                    tmpXCoor.delete(0, tmpXCoor.length());
                    tmpYCoor.delete(0, tmpYCoor.length());

                    //System.out.println("coordinatesOfTileCounter NE: " + coordinatesOfTileCounter);
                    break;

                case "SE":

                    tmpXCoor.append((char) (coordinatesOfTileCounter.charAt(0) + 1));
                    tmpYCoor.append((char) (coordinatesOfTileCounter.charAt(1) + 1));

                    coordinatesOfTileCounter.delete(0, coordinatesOfTileCounter.length());
                    coordinatesOfTileCounter.append(tmpXCoor);
                    coordinatesOfTileCounter.append(tmpYCoor);

                    tmpXCoor.delete(0, tmpXCoor.length());
                    tmpYCoor.delete(0, tmpYCoor.length());

                    //System.out.println("coordinatesOfTileCounter SE: " + coordinatesOfTileCounter);
                    break;

                case "NW":

                    tmpXCoor.append((char) (coordinatesOfTileCounter.charAt(0) - 1));
                    tmpYCoor.append((char) (coordinatesOfTileCounter.charAt(1) - 1));

                    coordinatesOfTileCounter.delete(0, coordinatesOfTileCounter.length());
                    coordinatesOfTileCounter.append(tmpXCoor);
                    coordinatesOfTileCounter.append(tmpYCoor);

                    tmpXCoor.delete(0, tmpXCoor.length());
                    tmpYCoor.delete(0, tmpYCoor.length());

                    //System.out.println("coordinatesOfTileCounter NW: " + coordinatesOfTileCounter);
                    break;

                case "SW":

                    tmpXCoor.append((char) (coordinatesOfTileCounter.charAt(0) - 1));
                    tmpYCoor.append((char) (coordinatesOfTileCounter.charAt(1) + 1));

                    coordinatesOfTileCounter.delete(0, coordinatesOfTileCounter.length());
                    coordinatesOfTileCounter.append(tmpXCoor);
                    coordinatesOfTileCounter.append(tmpYCoor);

                    tmpXCoor.delete(0, tmpXCoor.length());
                    tmpYCoor.delete(0, tmpYCoor.length());

                    //System.out.println("coordinatesOfTileCounter SW: " + coordinatesOfTileCounter);
                    break;

                case "NO":

                    tmpXCoor.append(coordinatesOfTileCounter.charAt(0));
                    tmpYCoor.append((char) (coordinatesOfTileCounter.charAt(1) - 1));

                    coordinatesOfTileCounter.delete(0, coordinatesOfTileCounter.length());
                    coordinatesOfTileCounter.append(tmpXCoor);
                    coordinatesOfTileCounter.append(tmpYCoor);

                    tmpXCoor.delete(0, tmpXCoor.length());
                    tmpYCoor.delete(0, tmpYCoor.length());

                    //System.out.println("coordinatesOfTileCounter NE: " + coordinatesOfTileCounter);
                    break;

                case "SO":

                    tmpXCoor.append(coordinatesOfTileCounter.charAt(0));
                    tmpYCoor.append((char) (coordinatesOfTileCounter.charAt(1) + 1));

                    coordinatesOfTileCounter.delete(0, coordinatesOfTileCounter.length());
                    coordinatesOfTileCounter.append(tmpXCoor);
                    coordinatesOfTileCounter.append(tmpYCoor);

                    tmpXCoor.delete(0, tmpXCoor.length());
                    tmpYCoor.delete(0, tmpYCoor.length());

                    //System.out.println("coordinatesOfTileCounter NE: " + coordinatesOfTileCounter);
                    break;

                case "EA":

                    tmpXCoor.append((char) (coordinatesOfTileCounter.charAt(0) + 1));
                    tmpYCoor.append(coordinatesOfTileCounter.charAt(1));

                    coordinatesOfTileCounter.delete(0, coordinatesOfTileCounter.length());
                    coordinatesOfTileCounter.append(tmpXCoor);
                    coordinatesOfTileCounter.append(tmpYCoor);

                    tmpXCoor.delete(0, tmpXCoor.length());
                    tmpYCoor.delete(0, tmpYCoor.length());

                    //System.out.println("coordinatesOfTileCounter NE: " + coordinatesOfTileCounter);
                    break;

                case "WE":

                    tmpXCoor.append((char) (coordinatesOfTileCounter.charAt(0) - 1));
                    tmpYCoor.append(coordinatesOfTileCounter.charAt(1));

                    coordinatesOfTileCounter.delete(0, coordinatesOfTileCounter.length());
                    coordinatesOfTileCounter.append(tmpXCoor);
                    coordinatesOfTileCounter.append(tmpYCoor);

                    tmpXCoor.delete(0, tmpXCoor.length());
                    tmpYCoor.delete(0, tmpYCoor.length());

                    //System.out.println("coordinatesOfTileCounter NE: " + coordinatesOfTileCounter);
                    break;

                default:
                    System.out.println("Something went wrong in the switch statement of the Bishop");

            }

            boolean isTileFull = !board.getTile(coordinatesOfTileCounter.toString()).isEmpty();
            if (isTileFull) {

                return false;
            }

        }

        return true;

    }

    public boolean destinationContainsAlly(Tile destinationTile) {
        return !destinationTile.isEmpty() &&
                destinationTile.getPiece().getColor() == getColor();
    }

    public boolean pathIsEmptyAndDestinationIsFree(Tile destinationTile) {

        boolean destinationContainsAlly = destinationContainsAlly(destinationTile);
        boolean isPathEmpty = isPathEmpty(destinationTile);

        if (isPathEmpty && !destinationContainsAlly) {

            return true;
        }

        System.out.println("The Destination Path Contains a Piece");
        return false;

    }

    public abstract boolean canMove(Tile destinationTile);

    public void move(Tile destinationTile) {

        if (canMove(destinationTile)) {
            this.tile.setPiece(null);
            destinationTile.setPiece(this);
            board.setWhiteTurn(!board.isWhiteTurn());

            switch (this.getName()) {

                case "P":

                    System.out.println("Pawn Moved");
                    break;
                case "B":

                    System.out.println("Bishop Moved");
                    break;
                case "Kn":

                    System.out.println("Knight Moved");
                    break;
                case "R":

                    System.out.println("Rook Moved");
                    break;
                case "K":

                    System.out.println("King Moved");
                    break;
                case "Q":

                    System.out.println("Queen Moved");
                    break;
                default:

            }
        } else {

            switch (this.getName()) {

                case "P":

                    System.out.println("This is not a valid Pawn Move");
                    break;
                case "B":

                    System.out.println("This is not a valid Bishop Move");
                    break;
                case "Kn":

                    System.out.println("This is not a valid Knight Move");
                    break;
                case "R":

                    System.out.println("This is not a valid Rook Move");
                    break;
                case "K":

                    System.out.println("This is not a valid King move");
                    break;
                case "Q":

                    System.out.println("This is not a valid Queen Move");
                    break;
                default:

                    System.out.println("Something wrong happened in the else statement of the if condition of printing who moved in piece");
            }

        }

    }

}

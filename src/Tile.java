import javafx.scene.control.Button;

public class Tile extends Button {

    private Coordinate coordinate;
    private Piece piece;
    private String color;
    private boolean isEmpty;
    private Board board;

    Tile(Coordinate coordinate, String color, Board board) {
        this.color = color;
        this.coordinate = coordinate;
        this.isEmpty = true;
        this.board = board;
    }

    boolean isEmpty() {
        return isEmpty;
    }

    Piece getPiece() {
        return piece;
    }

    Board getBoard() {
        return board;
    }

    int xDiffFrom(Tile destinationTile) {
        return coordinate.getXDifference(destinationTile.getCoordinates());
    }

    int yDiffFrom(Tile destinationTile) {
        return coordinate.getYDifference(destinationTile.getCoordinates());
    }

    Tile getNeighbourTile(int xDiff, int yDiff) {
        Coordinate tileCoordinate = coordinate.shift(xDiff, yDiff);
        if (tileCoordinate.isValidCoordinate()) {
            return board.getTile(tileCoordinate);
        } else {
            return null;
        }
    }

    //setPiece sets the piece and also makes the piece points to the new tile that holds the it (the piece) in it
    void setPiece(Piece piece) {
        this.piece = piece;
        if (piece != null) {
            this.isEmpty = false;
            piece.setTile(this);
        } else {
            this.isEmpty = true;
        }
    }

    Coordinate getCoordinates() {
        return coordinate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}


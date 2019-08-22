import java.awt.*;

public class Tile {

    private String coordinates;
    private Piece piece;
    private Color color;
    private boolean isEmpty;

    public Tile(String coordinates, Color color) {
        this.color = color;
        this.coordinates = coordinates;
        this.isEmpty = true;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (piece != null) {
            this.isEmpty = false;
            piece.setTile(this);
        } else {
            this.isEmpty = true;
        }
    }

    public int xDistanceTo(Tile destinationTile) {
        return destinationTile.getCoordinates().charAt(0) - coordinates.charAt(0);
    }

    public int yDistanceTo(Tile destinationTile) {
        return destinationTile.getCoordinates().charAt(1) - coordinates.charAt(1);
    }

    public String getCoordinates() {
        return coordinates;
    }
}


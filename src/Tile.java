import java.awt.*;

public class Tile {

    private String coordinates;
    private Piece piece;
    private Color color;
    private boolean isEmpty;
    private Board board;

    public Tile(String coordinates, Color color, Board board) {
        this.color = color;
        this.coordinates = coordinates;
        this.isEmpty = true;
        this.board = board;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public Piece getPiece() {
        return piece;
    }

    public Board getBoard() {
        return board;
    }

    //setPiece sets the piece and also makes the piece points to the new tile that holds the it (the piece) in it
    public void setPiece(Piece piece) {
        this.piece = piece;
        if (piece != null) {
            this.isEmpty = false;
            piece.setTile(this);
            piece.setBoard(this.getBoard());
        } else {
            this.isEmpty = true;
        }
    }

    public String getCoordinates() {
        return coordinates;
    }
}


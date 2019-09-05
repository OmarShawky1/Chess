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

    public void setColor(Color color) {
        this.color = color;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * The abstract template for move(). For now, this does nothing but
     * relocate the piece from the old tile to the new one. it is
     * therefore your responsibility to adopt the appropriate checks
     * before calling this method.
     * TODO: certain checks might be added to this later.
     */
    public void move(Tile destinationTile) {
        this.tile.setPiece(null);
        destinationTile.setPiece(this);
    }

    public abstract String getName();

}

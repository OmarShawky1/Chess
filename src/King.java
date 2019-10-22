import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class King extends Piece {

    King(Color color) {
        super(color);

        if (color == Color.WHITE) {
            this.image = new Image("White_King.png");
        } else {
            this.image = new Image("Black_King.png");
        }
    }

    boolean isBeingChecked() {
        Color enemyColor = color == Color.WHITE ? Color.BLACK : Color.WHITE;
        LinkedList<Piece> enemyPieces = tile.getBoard().getAllPiecesWithColor(enemyColor);
        for (Piece piece : enemyPieces) {
            if (piece.canMove(tile)) {
                return true;
            }
        }
        return false;
    }

    boolean isAlive() {

        //to know if the king is still alive, you should
        // 1- check if he can move to another place or not
        // 2- check if there is a piece that can kill the enemy piece that threats the king
        // 3- check for a place where any piece from the army can block the threat

        System.out.println("<==>pieceCanKillAKing(): <==>" + pieceCanKillAKing());
        System.out.println("tile.getCoordinates(): " + tile.getCoordinates());
        System.out.println("kingCanMoveAround(): " + kingCanMoveAround());
        System.out.println("armyCanBlockThreat(): " + armyCanKillThreat());
        System.out.println("armyCanBlockThreat(): " + armyCanBlockThreat());

//        LinkedList<Piece> enemyPieces = color == Color.WHITE? getPiecesOfColor(Color.BLACK): getPiecesOfColor(Color.WHITE);
//        LinkedList<Piece> enemyThreateningKing = getThreateningPieces(enemyPieces);
        if (pieceCanKillAKing()) {
            if (kingCanMoveAround() || armyCanKillThreat() || armyCanBlockThreat()) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean canMove(Tile destinationTile) {
        int xDist = Math.abs(tile.xDiffFrom(destinationTile));
        int yDist = Math.abs(tile.yDiffFrom(destinationTile));

        return xDist <= 1 && yDist <= 1 && super.canMove(destinationTile);
    }

    private boolean kingCanMoveAround() {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Tile neighbourTile = tile.getNeighbourTile(i, j);
                if (neighbourTile != null && canMove(neighbourTile)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean armyCanKillThreat() {
        LinkedList<Piece> army = getPiecesOfColor(color);
        LinkedList<Piece> enemyPieces = color == Color.WHITE ? getPiecesOfColor(Color.BLACK) : getPiecesOfColor(Color.WHITE);
        LinkedList<Piece> enemyPiecesThatThreats = getThreateningPieces(enemyPieces);
        //checking if we can kill this threatening piece
        for (Piece armyPiece : army) {
            for (Piece enemyPiece : enemyPiecesThatThreats) {
                if (armyPiece.canMove(enemyPiece.tile)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean armyCanBlockThreat() {
        LinkedList<Piece> army = getPiecesOfColor(color);
        LinkedList<Piece> enemyPieces = color == Color.WHITE ? getPiecesOfColor(Color.BLACK) : getPiecesOfColor(Color.WHITE);
        LinkedList<Piece> enemyPiecesThatThreats = getThreateningPieces(enemyPieces);

        //make each armyPiece go each place in the board
        for (Piece armyPiece : army) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Coordinate coordinate = new Coordinate(row, col);
                    //checking if the coordinate is a valid movement for the piece
                    if (armyPiece.canMove(tile.getBoard().getTile(coordinate))) {
                        //if this armPiece moved to the new coordinate, check if there is a leftover enemyThreat
                        return pieceCanKillAKing();
                    }
                }
            }
        }
        return false;
    }

    private boolean pieceCanKillAKing() {

        LinkedList<Piece> enemyPieces = color == Color.WHITE ? getPiecesOfColor(Color.BLACK) : getPiecesOfColor(Color.WHITE);
        LinkedList<Piece> enemyPiecesThatThreats = getThreateningPieces(enemyPieces);
//        for (Piece enemyPieceThatThreats : enemyPiecesThatThreats) {
//            System.out.println("enemyPiecesThatThreats: " + enemyPiecesThatThreats.getClass().getName());
//            if (enemyPieceThatThreats.canMove(tile)) {
//                return true;
//            }
        for (int i=0; i<enemyPiecesThatThreats.size(); i++){
            if (enemyPiecesThatThreats.get(i).canMove(tile)){
                return true;
            }
        }
        return false;
    }

    private LinkedList<Piece> getPiecesOfColor(Color color) {
        return tile.getBoard().getAllPiecesWithColor(color);
    }

    private LinkedList<Piece> getThreateningPieces(LinkedList<Piece> enemyPieces) {
        //creating enemyPiecesThatThreats LinkedList
        LinkedList<Piece> enemyPiecesThatThreats = new LinkedList<>();
        for (Piece enemyPiece : enemyPieces) {
            if (enemyPiece.canMove(tile)) {
                enemyPiecesThatThreats.add(enemyPiece);
            }
        }
        return enemyPiecesThatThreats;
    }
}

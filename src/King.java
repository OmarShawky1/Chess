import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class King extends Piece {

    King(String color) {
        super(color);

        if (color.equalsIgnoreCase("white")) {
            this.image = new Image("White_King.png");
        } else {
            this.image = new Image("Black_King.png");
        }
    }

    boolean isBeingChecked() {
        return pieceCanKillAKing();
    }

    boolean isAlive() {

        //to know if the king is still alive, you should
        // 1- check if he can move to another place or not
        // 2- check if there is a piece that can kill the enemy piece that threats the king
        // 3- check for a place where any piece from the army can block the threat
        System.out.println(this.color.toString() + " King");
        System.out.println("pieceCanKillAKing(): " + pieceCanKillAKing());
        if (pieceCanKillAKing()) {
            System.out.println("kingCanMoveAround(): "+ kingCanMoveAround());
            System.out.println("armyCanKillThreat():" + armyCanKillThreat());
            System.out.println("armyCanBlockThreat(): " + armyCanBlockThreat());
            return kingCanMoveAround() || armyCanKillThreat() || armyCanBlockThreat();
        }
        return true;
    }

    public boolean canMove(Tile destinationTile) {
        int xDist = Math.abs(tile.xDiffFrom(destinationTile));
        int yDist = Math.abs(tile.yDiffFrom(destinationTile));

        if (xDist <= 1 && yDist <= 1){

            return super.canMove(destinationTile);
        }
        return false;
    }

    private boolean kingCanMoveAround() {
        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                Tile neighbourTile = tile.getNeighbourTile(row, col);
                if (neighbourTile != null && canMove(neighbourTile)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean armyCanKillThreat() {
        LinkedList<Piece> army = getPiecesOfColor(color);
        LinkedList<Piece> enemyPieces = color.equalsIgnoreCase("white") ? getPiecesOfColor("black") : getPiecesOfColor("white");
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

        //make each armyPiece go each place in the board
        for (Piece armyPiece : army) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Coordinate coordinate = new Coordinate(row, col);
                    //checking if the coordinate is a valid movement for the piece
                    if (armyPiece.canMove(tile.getBoard().getTile(coordinate))) {
                        //if this armyPiece moved to the new coordinate, check if there is a leftover enemyThreat
                        System.out.println("armyPiece.tile.getCoordinates(): " + armyPiece.tile.getCoordinates());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean pieceCanKillAKing() {

        String enemyColor = color.equalsIgnoreCase("white")? "black" : "white";
        LinkedList<Piece> enemyPieces = getPiecesOfColor(enemyColor);
        LinkedList<Piece> enemyPiecesThatThreats = getThreateningPieces(enemyPieces);
        return enemyPiecesThatThreats.size() > 0;
    }

    private LinkedList<Piece> getPiecesOfColor(String color) {
        return tile.getBoard().getAllPiecesWithColor(color);
    }

    private LinkedList<Piece> getThreateningPieces(LinkedList<Piece> enemyPieces) {
        LinkedList<Piece> enemyPiecesThatThreats = new LinkedList<>();
        for (Piece enemyPiece : enemyPieces) {
            if (enemyPiece.canMove(tile)) {
                enemyPiecesThatThreats.add(enemyPiece);
            }
        }
        return enemyPiecesThatThreats;
    }
}

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.util.Optional;

public class Pawn extends Piece {

    private boolean firstTwoStepMovement;
    private boolean canEnPassantMe;


    Pawn(String color) {
        super(color);

        if (color.equalsIgnoreCase("white")) {
            this.image = new Image("White_Pawn.png");
        } else {
            this.image = new Image("Black_Pawn.png");
        }
        firstTwoStepMovement = false;
    }

    private void setFirstTwoStepMovement(Tile destinationTile){
        int y = Math.abs(destinationTile.getCoordinates().getY() - tile.getCoordinates().getY());
        if (y ==2){
            firstTwoStepMovement = true;
        }
    }

    private boolean isCanEnPassantMe() {
        return canEnPassantMe;
    }

    void setCanEnPassantMe() {
        this.canEnPassantMe = false;
    }

    public boolean canMove(Tile destinationTile) {
        if (isForwardMove(destinationTile)) {
            return super.canMove(destinationTile);
        } else return isValidCornerMove(destinationTile) && super.canMove(destinationTile);

    }

    private boolean isForwardMove(Tile destinationTile) {

        int yDiff = tile.yDiffFrom(destinationTile);
        int xDiff = tile.xDiffFrom(destinationTile);
        int maxYSteps = firstTwoStepMovement ? 1 : 2;

        boolean isCorrectVerticalMove = (color.equalsIgnoreCase("black") && yDiff <= maxYSteps && yDiff > 0) ||
                (color.equalsIgnoreCase("white") && yDiff >= -maxYSteps && yDiff < 0);
        boolean isCorrectHorizontalMove = Math.abs(xDiff) == 0;

        return isCorrectVerticalMove && isCorrectHorizontalMove && isPathClearTowards(destinationTile);
    }

    private boolean isValidCornerMove(Tile destinationTile) {
        //if i can move to corner because there is a piece in the corner
        if (isCornerMove(destinationTile)) {
            // if the corner tile is empty, check if i can en passant
            if (!destinationTile.isEmpty()) {
                return true;
            } else return iCanEnPassant();
        }
        return false;
    }

    private boolean isCornerMove(Tile destinationTile) {
        int yDiff = tile.yDiffFrom(destinationTile);
        int xDiff = tile.xDiffFrom(destinationTile);

        boolean isCorrectVerticalMove = (color.equalsIgnoreCase("black") && yDiff == 1) || (color.equalsIgnoreCase("white") && yDiff == -1);
        boolean isCorrectHorizontalMove = Math.abs(xDiff) == 1;
        return isCorrectVerticalMove && isCorrectHorizontalMove;
    }

    private boolean iCanEnPassant() {
        Pawn rightPawn = sidePawn(1);
        Pawn leftPawn = sidePawn(-1);

        return (rightPawn != null && rightPawn.isCanEnPassantMe()) || (leftPawn != null && leftPawn.isCanEnPassantMe());
    }

    private Pawn sidePawn(int direction) {
        int side = tile.getCoordinates().getX() + direction;
        Coordinate sideCoor = new Coordinate(side, tile.getCoordinates().getY());
        Pawn sidePawn = null;

        if (sideCoor.isValidCoordinate()) {
            Tile sideTile = tile.getBoard().getTile(sideCoor);
            Piece sidePiece = sideTile.isEmpty() ? null : sideTile.getPiece();
            sidePawn = sidePiece instanceof Pawn ? (Pawn) sidePiece : null;
        }
        return sidePawn;
    }

    private void setNewPiece(Piece piece, Tile destinationTile) {
        System.out.println("Reached setNewPiece");
        System.out.println("destinationTile.getCoordinates(): " + destinationTile.getCoordinates());
        System.out.println("piece.getClass().getName(): " + piece.getClass().getName());
        destinationTile.setPiece(piece);
        piece.setTile(destinationTile);
    }

    private void promotePawn(Tile destinationTile) {
        int y = destinationTile.getCoordinates().getY();
        boolean reachedLastTileInBoard = (y == 7) || (y == 0);
        if (reachedLastTileInBoard) {

            ButtonType queenButton = new ButtonType("Queen");
            ButtonType rookButton = new ButtonType("Rook");
            ButtonType bishopButton = new ButtonType("Bishop");
            ButtonType knightButton = new ButtonType("Knight");

            Alert choosePiece = new Alert(Alert.AlertType.CONFIRMATION);
            choosePiece.setTitle("Promote a piece");
            choosePiece.setHeaderText("You can promote your pawn into another piece");
            choosePiece.setContentText("Choose one of the following piece");
            choosePiece.getButtonTypes().addAll(queenButton, rookButton, bishopButton, knightButton);

            choosePiece.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            choosePiece.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
            choosePiece.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
            choosePiece.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);

            Optional<ButtonType> result = choosePiece.showAndWait();
            if (result.get() == queenButton){
                setNewPiece(new Queen(color), destinationTile);

            }else if(result.get() == rookButton){
                setNewPiece(new Rook(color), destinationTile);

            }else if(result.get() == bishopButton){
                setNewPiece(new Bishop(color), destinationTile);

            }else if(result.get() == knightButton){
                setNewPiece(new Knight(color), destinationTile);

            }
        }
    }

    public void move(Tile destinationTile) {

        setFirstTwoStepMovement(destinationTile);

        Pawn rightPawn = sidePawn(1);
        Pawn leftPawn = sidePawn(-1);
        int x = Math.abs(destinationTile.getCoordinates().getY() - tile.getCoordinates().getY());
        if (x == 2) {
            if (rightPawn != null || leftPawn != null) {
                canEnPassantMe = true;
            }
        }
        //eat the enPassant Pawn
        if (iCanEnPassant()) {

            if (rightPawn != null && rightPawn.isCanEnPassantMe()) {
                rightPawn.tile.setPiece(null);
            }
            if (leftPawn != null && leftPawn.isCanEnPassantMe()) {
                leftPawn.tile.setPiece(null);
            }
        }

        super.move(destinationTile);
        promotePawn(destinationTile);
        //the previous order was made for a reason, because when the super.move works, it will overlap with promotion
    }
}

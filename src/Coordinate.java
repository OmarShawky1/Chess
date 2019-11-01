public class Coordinate {
    private int x, y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(String coordinate) {
        x = coordinate.charAt(0) - 'a';
        y = coordinate.charAt(1) - '1';
    }

    boolean isValidCoordinate() { /* valid chess board coordinate */
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    int getXDifference(Coordinate coordinate) {
        return coordinate.getX() - x;
    }

    int getYDifference(Coordinate coordinate) {
        return coordinate.getY() - y;
    }

    @Override
    public String toString() {
        byte[] bytes = {(byte)(x + 'a'), (byte)(y + '1')};
        return new String(bytes);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    Coordinate shift(int xDist, int yDist) {
        return new Coordinate(x + xDist, y + yDist);
    }
}

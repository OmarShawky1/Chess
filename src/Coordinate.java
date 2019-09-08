public class Coordinate {
    private int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(String coordinate) {
        x = coordinate.charAt(0) - 'a';
        y = coordinate.charAt(1) - '1';
    }

    public boolean isValidCoordinate() { /* valid chess board coordinate */
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    public int getXDifference(Coordinate coordinate) {
        return coordinate.getX() - x;
    }

    public int getYDifference(Coordinate coordinate) {
        return coordinate.getY() - y;
    }

    @Override
    public String toString() {
        byte[] bytes = {(byte)(x + 'a'), (byte)(y + '1')};
        return new String(bytes);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate shift(int xDist, int yDist) {
        return new Coordinate(x + xDist, y + yDist);
    }
}

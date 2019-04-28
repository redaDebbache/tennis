package model;

public enum Point {
    ZERO(0, "FIFTEEN"),
    FIFTEEN(15, "THIRTY"),
    THIRTY(30, "FOURTY"),
    FOURTY(40, "WIN"),
    WIN(0, "ZERO");
    private int score;
    private String nextPoint;

    Point(int score, String nextPoint) {
        this.score = score;
        this.nextPoint = nextPoint;
    }

    public int getScore() {
        return score;
    }

    public Point getNextPoint() {
        return valueOf(nextPoint);
    }
}

package model;

public enum Point {
    ZERO("0", "FIFTEEN", 0),
    FIFTEEN("15", "THIRTY", 1),
    THIRTY("30", "FOURTY", 2),
    FOURTY("40", "ADV", 3),
    ADV("ADV", "POINT", 4),
    POINT("ADV", "ZERO", 4),
    DEUCE("DEUCE", "ZERO", 4);
    private String value;
    private int score;
    private String nextPoint;

    Point(String value, String nextPoint, int score) {
        this.value = value;
        this.score = score;
        this.nextPoint = nextPoint;
    }

    public int getScore() {
        return score;
    }

    public Point getNextPoint() {
        return valueOf(nextPoint);
    }

    public String getValue() {
        return value;
    }
}

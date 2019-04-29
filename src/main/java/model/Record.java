package model;

public class Record {
    private static final String EMPTY = "";
    private final Point first;
    private final Point second;
    private final Integer firstScore;
    private final Integer secondScore;
    private final String title;
    private String message;

    public Record(Point first, Point second, Integer firstScore, Integer secondScore, String title) {
        this.first = first;
        this.second = second;
        this.firstScore = firstScore;
        this.secondScore = secondScore;
        this.title = title;
        this.message = EMPTY;
    }
    public Record(Point first, Point second, Integer firstScore, Integer secondScore, String title, String message) {
        this.first = first;
        this.second = second;
        this.firstScore = firstScore;
        this.secondScore = secondScore;
        this.title = title;
        this.message = message;
    }

    public Point getFirst() {
        return first;
    }

    public Point getSecond() {
        return second;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Integer getFirstScore() {
        return firstScore;
    }

    public Integer getSecondScore() {
        return secondScore;
    }
}

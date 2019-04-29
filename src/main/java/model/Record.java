package model;

public class Record {
    private static final String EMPTY = "";
    private final Point first;
    private final Point second;
    private final String title;
    private String message;

    public Record(Point first, Point second, String title) {
        this.first = first;
        this.second = second;
        this.title = title;
        this.message = EMPTY;
    }
    public Record(Point first, Point second, String title, String message) {
        this.first = first;
        this.second = second;
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
}

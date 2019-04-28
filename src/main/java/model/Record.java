package model;

public class Record {
    private final Point first;
    private final Point second;
    private final String title;

    public Record(Point first, Point second, String title) {
        this.first = first;
        this.second = second;
        this.title = title;
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
}

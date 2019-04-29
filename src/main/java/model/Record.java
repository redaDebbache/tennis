package model;

public class Record {
    private static final String EMPTY = "";
    private final Point first;
    private final Point second;
    private final Integer firstSet;
    private final Integer secondSet;
    private final Integer firstTieBreak;
    private final Integer secondTieBreak;
    private final String title;
    private String message;

    public Record(Point first, Point second, Integer firstSet, Integer secondSet, String title) {
       this(first, second, firstSet, secondSet, 0, 0, title, EMPTY);
    }


    public Record(Point first, Point second, Integer firstSet, Integer secondSet, String title, String message) {
        this(first, second, firstSet, secondSet, title);
        this.message = message;
    }

    public Record(Point first, Point second, Integer firstSet, Integer secondSet, Integer firstTieBreak, Integer secondTieBreak, String title, String message) {
        this.first = first;
        this.second = second;
        this.firstSet = firstSet;
        this.secondSet = secondSet;
        this.title = title;
        this.message = message;
        this.firstTieBreak = firstTieBreak;
        this.secondTieBreak = secondTieBreak;
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
  
    public Integer getFirstSet() {
        return firstSet;
    }

    public Integer getSecondSet() {
        return secondSet;
    }

    public Integer getFirstTieBreak() {
        return firstTieBreak;
    }

    public Integer getSecondTieBreak() {
        return secondTieBreak;
    }
}

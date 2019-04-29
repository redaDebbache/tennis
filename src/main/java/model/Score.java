package model;


import static model.Point.ZERO;

public class Score {
    private Player player;
    private Point currentPoint;
    private int winnedSet;
    private int tieBreak;

    public Score(Player player) {
        this.player = player;
        this.currentPoint = ZERO;

    }

    public void winAPoint() {
        this.currentPoint = this.currentPoint.getNextPoint();
    }

    public void winASet() {
        this.winnedSet += 1;
    }

    public void winATieBreak(){
        this.tieBreak += 1;
    }

    public void loseAdv() {
        if (currentPoint == Point.ADV) {
            this.currentPoint = Point.FOURTY;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Point point() {
        return currentPoint;
    }

    public Integer getWinnedGame() {
        return winnedSet;
    }

    public void init() {
        this.currentPoint = ZERO;
    }

    public Integer getTieBreak() {
        return tieBreak;
    }
}

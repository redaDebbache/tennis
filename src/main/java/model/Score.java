package model;

public class Score {
    private Player player;
    private Point currentPoint;

    public Score(Player player) {
        this.player = player;
        this.currentPoint = Point.ZERO;
    }

    public void winAPoint(){
        this.currentPoint = this.currentPoint.getNextPoint();
    }


    public void loseAdv(){
        if(currentPoint == Point.ADV) {
            this.currentPoint = Point.FOURTY;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }
}

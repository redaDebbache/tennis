package model;

import java.util.ArrayList;
import java.util.List;

import static model.Point.ZERO;

public class GameResume {
    private static final String EMPTY = "";
    private static final String START_OF_GAME_LABEL = "Start of game";


    private Player firstPlayer;
    private Player secondPlayer;
    private List<Record> records;
    private String resume;

    public GameResume(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.records = new ArrayList<>();
        this.resume = EMPTY;
    }

    public GameResume startGame(){
        addRecord(new Record(ZERO, ZERO, START_OF_GAME_LABEL));
        return this;
    }

    public void addRecord(Record record){
        this.records.add(record);
    }

    public List<Record> getRecords() {
        return records;
    }

    public Record getLastRecord(){
        return this.records.get(this.records.size() -1);
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public String getResume() {
        return getLastRecord().getMessage();
    }

}

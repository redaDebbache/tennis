package model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static model.Point.*;
import static org.assertj.core.api.Assertions.*;

public class GameTest {
    private Player first;
    private Player second;
    private Game game;

    @Before
    public void init(){
        this.first = new Player("Player 1");
        this.second = new Player("Player 2");
        this.game = new Game(first, second);
    }

    @Test
    public void should_start_game_with_start_score() {
        //When
        GameResume gameResume = game.getGameResume();
        //Then
        assertThat(gameResume).isNotNull();
        List<Record> records = gameResume.getRecords();
        assertThat(records).isNotEmpty();
        assertThat(records.size()).isEqualTo(1);
        Record record = records.get(0);
        assertThat(record.getFirst()).isEqualTo(ZERO);
        assertThat(record.getSecond()).isEqualTo(ZERO);
        assertThat(record.getTitle()).isEqualTo("Start of game");


    }

    @Test
    public void should_win_point_increase_plyer_score() {
        //When
        GameResume gameResume = game.winPoint(first);
        //Then
        List<Record> records = gameResume.getRecords();
        assertThat(records.size()).isEqualTo(2);
        Record record = records.get(1);
        assertThat(record.getTitle()).isEqualTo("Player 1 wins 1 point");
        assertThat(record.getFirst()).isEqualTo(FIFTEEN);
        assertThat(record.getSecond()).isEqualTo(ZERO);

    }

    @Test
    public void should_have_a_winner_and_stops_match() {
        assertWInPoint(first);
        assertWInPoint(first);
        assertWInPoint(second);
        assertWInPoint(first);
        assertWInPoint(second);
        assertWInPoint(second);
        assertWInPoint(second);
        assertWinsMAtch(second);
    }

    private void assertWInPoint(Player player) {
        GameResume gameResume = game.winPoint(player);
        assertThat(gameResume.getResume()).isEmpty();
        assertThat(gameResume.getLastRecord().getTitle()).isEqualTo(String.format("%s wins 1 point", player.getName()));
    }

    private void assertWinsMAtch(Player player) {
        GameResume gameResume = game.winPoint(player);
        assertThat(gameResume.getResume()).isNotEmpty();
        assertThat(gameResume.getResume()).isEqualTo("Player 2 wins the game");
        Record lastRecord = gameResume.getLastRecord();
        assertThat(lastRecord.getFirst()).isEqualTo(ZERO);
        assertThat(lastRecord.getSecond()).isEqualTo(ZERO);
    }

    @Test
    public void should_activate_deuce_when_both_players_reach_40() {
        //Given
        playUntilDeuce();
        assertWInPoint(first);
        assertWInPoint(second);
        GameResume gameResume = game.getGameResume();
        //When
        Record lastRecord = gameResume.getLastRecord();
        //Then
        assertThat(lastRecord.getFirst()).isEqualTo(DEUCE);
        assertThat(lastRecord.getSecond()).isEqualTo(DEUCE);
    }

    @Test
    public void should_show_ADV_when_both_players_reach_40() {
        //Given
        playUntilDeuce();
        assertWInPoint(first);
        GameResume gameResume = game.getGameResume();
        //When
        Record lastRecord = gameResume.getLastRecord();
        //Then
        assertThat(lastRecord.getFirst()).isEqualTo(ADV);
        assertThat(lastRecord.getSecond()).isEqualTo(FOURTY);
    }

    private void playUntilDeuce() {
        assertWInPoint(first);
        assertWInPoint(first);
        assertWInPoint(second);
        assertWInPoint(first);
        assertWInPoint(second);
        assertWInPoint(second);
    }
}
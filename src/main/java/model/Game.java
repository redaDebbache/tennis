package model;

import java.util.Objects;

import static rules.GameRuleEngine.RuleType;

public class Game {
    private static final String PLAYER_MUST_NOT_BE_NULL_ERROR_MESSAGE = "Player must not be null";

    private Score first;
    private Score second;
    private GameResume gameResume;

    public Game(Player firstPlayer, Player secondPlayer) {
        this.first = initScore(firstPlayer);
        this.second = initScore(secondPlayer);
        this.gameResume = new GameResume(firstPlayer, secondPlayer).startGame();
    }

    private Score initScore(Player player) {
        return new Score(Objects.requireNonNull(player, PLAYER_MUST_NOT_BE_NULL_ERROR_MESSAGE));
    }

    public GameResume winPoint(Player player) {
        Score winnerScore = getPointWinner(player);
        winnerScore.winAPoint();

        Record ruleRecord = RuleType.matchRule(first.getCurrentPoint(), second.getCurrentPoint())
                .getRule().rule(first, second, player);
        this.gameResume.addRecord(ruleRecord);
        return this.gameResume;
    }

    private Score getPointWinner(Player player) {
        return first.getPlayer().equals(player) ? first : second;
    }

    public GameResume getGameResume() {
        return gameResume;
    }

}

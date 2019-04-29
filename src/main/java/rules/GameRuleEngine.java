package rules;

import model.Player;
import model.Point;
import model.Record;
import model.Score;

import java.util.stream.Stream;

import static java.lang.String.format;
import static model.Point.*;

public class GameRuleEngine {
    private static final String WINS_1_POINT_LABEL = "%s wins 1 point";
    private static final String WINS_THE_GAME_LABEL = "%s wins the game";
    private static final String WINS_THE_SET_LABEL = "%s wins the set";

    private static boolean winWithAdv(Point first, Point second) {
        return (first == POINT || second == POINT) && Math.abs(first.getScore() - second.getScore()) == 1;
    }

    private static boolean winWithPoints(Point first, Point second) {
        return (first == ADV || second == ADV) && Math.abs(first.getScore() - second.getScore()) == 1;
    }

    private static Record buildDeuceRecord(Score first, Score second, Player player) {
        return new Record(Point.DEUCE, Point.DEUCE, first.getWinnedGame(), second.getWinnedGame(), format(WINS_1_POINT_LABEL, player.getName()));
    }

    private static Record buildGameWinnerRecord(Score first, Score second, Player player) {
        Stream.of(first, second).filter(score -> score.getPlayer().equals(player)).findFirst().ifPresent(Score::winASet);
        return winASet(first, second) ? buildSetWinnerRecord(first, second, player) : buildGameWinRecord(first, second, player);
    }

    private static Record buildSetWinnerRecord(Score first, Score second, Player player) {
        first.init();
        second.init();
        return new Record(first.point(), second.point(), first.getWinnedGame(), second.getWinnedGame(), format(WINS_THE_GAME_LABEL, player.getName()), format(WINS_THE_SET_LABEL, player.getName()));
    }

    private static Record buildGameWinRecord(Score first, Score second, Player player) {
        first.init();
        second.init();
        return new Record(ZERO, ZERO, first.getWinnedGame(), second.getWinnedGame(), format(WINS_1_POINT_LABEL, player.getName()), format(WINS_THE_GAME_LABEL, player.getName()));
    }

    private static Record buildPointWinner(Score first, Score second, Player player) {
        return new Record(first.point(), second.point(), first.getWinnedGame(), second.getWinnedGame(), format(WINS_1_POINT_LABEL, player.getName()));
    }

    private static Record buildAdvRecord(Score first, Score second, Player player) {
        Score loserRecord = first.getPlayer().equals(player) ? second : first;
        loserRecord.loseAdv();
        return new Record(first.point(), second.point(), first.getWinnedGame(), second.getWinnedGame(), format(WINS_1_POINT_LABEL, player.getName()));
    }

    private static boolean winASet(Score first, Score second) {
        return (first.getWinnedGame() >= 6 || second.getWinnedGame() >= 6) && Math.abs(first.getWinnedGame() - second.getWinnedGame()) >= 2;
    }

    public enum RuleType {
        DEUCE_RULE((first, second) -> first.point() == ADV && second.point() == ADV, GameRuleEngine::buildDeuceRecord),

        ADV_RULE((first, second) -> (first.point() == ADV || second.point() == ADV), GameRuleEngine::buildAdvRecord),

        GAME_WINNING_RULE(((first, second) -> winWithAdv(first.point(), second.point()) || winWithPoints(first.point(), second.point())), GameRuleEngine::buildGameWinnerRecord),

        SIMPLE_POINT_WINNING_RULE((first, second) -> true, GameRuleEngine::buildPointWinner);

        private RuleCondition condition;

        private GameRule rule;

        RuleType(RuleCondition condition, GameRule rule) {
            this.condition = condition;
            this.rule = rule;
        }

        public GameRule getRule() {
            return rule;
        }

        public static RuleType matchRule(Score first, Score second) {
            return Stream.of(values()).filter(r -> r.condition.match(first, second)).findFirst().orElse(SIMPLE_POINT_WINNING_RULE);
        }

    }

    public interface GameRule {
        Record rule(Score first, Score second, Player winner);
    }

    interface RuleCondition {
        boolean match(Score first, Score second);
    }
}

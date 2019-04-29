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

    private static boolean winWithAdv(Point first, Point second) {
       return (first == POINT || second == POINT) && Math.abs(first.getScore() - second.getScore()) == 1;
    }

    private static boolean winWithPoints(Point first, Point second) {
       return (first == ADV || second == ADV) && Math.abs(first.getScore() - second.getScore()) == 1;
    }

    private static Record buildDeuceRecord(Player player){
        return new Record(Point.DEUCE, Point.DEUCE, format(WINS_1_POINT_LABEL, player.getName()));
    }

    private static Record buildGameWinnerRecord(Player player) {
        return new Record(ZERO, ZERO, format(WINS_1_POINT_LABEL, player.getName()), format(WINS_THE_GAME_LABEL, player.getName()));
    }

    private static Record buildPointWinner(Score first, Score second, Player player) {
        return new Record(first.getCurrentPoint(), second.getCurrentPoint(), format(WINS_1_POINT_LABEL, player.getName()));
    }

    private static Record buildAdvRecord(Score first, Score second, Player player) {
        Score loserRecord = first.getPlayer().equals(player) ? second : first;
        loserRecord.loseAdv();
        return new Record(first.getCurrentPoint(), second.getCurrentPoint(), format(WINS_1_POINT_LABEL, player.getName()));
    }

    public enum RuleType {
        DEUCE_RULE((first, second) -> first == ADV && second == ADV, (first, second, player) -> buildDeuceRecord(player)),

        ADV_RULE((first, second) -> (first == ADV || second == ADV), GameRuleEngine::buildAdvRecord),

        GAME_WINNING_RULE(((first, second) -> winWithAdv(first, second) || winWithPoints(first, second)), (first, second, player) -> buildGameWinnerRecord(player)),

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
        public static RuleType matchRule(Point first, Point second){
            return Stream.of(values()).filter(r -> r.condition.match(first, second)).findFirst().orElse(SIMPLE_POINT_WINNING_RULE);
        }

    }

    public interface GameRule {
        Record rule(Score first, Score second, Player winner);
    }

    interface RuleCondition {
        boolean match(Point first, Point second);
    }
}

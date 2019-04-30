package rules;

import model.Player;
import model.Point;
import model.Record;
import model.Score;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static model.Point.*;

public class GameRuleEngine {
    private static final String WINS_1_POINT_LABEL = "%s wins 1 point";
    private static final String WINS_THE_GAME_LABEL = "%s wins the game";
    private static final String WINS_THE_SET_LABEL = "%s wins the set";
    public static final int MIN_SET_WIN = 6;
    public static final int MIN_WINNING_DIFFERENCE = 2;
    public static final int MIN_POINTS_DIFFERENCE = 1;

    private static Record buildDeuceRecord(Score first, Score second, Player player) {
        return new Record(Point.DEUCE, Point.DEUCE, first.getWinnedGame(), second.getWinnedGame(), format(WINS_1_POINT_LABEL, player.getName()));
    }

    private static Record buildGameWinnerRecord(Score first, Score second, Player player) {
        Stream.of(first, second).filter(score -> score.getPlayer().equals(player)).findFirst().ifPresent(Score::winASet);
        return winATieBreak(first, second) ? buildTieBreakRecord(first, second, player) : winASet(first, second) ? buildSetWinnerRecord(first, second, player) : buildGameWinRecord(first, second, player);
    }

    private static Record buildTieBreakRecord(Score first, Score second, Player player) {
        initscoresPoints(first, second);
        Score winnerScore = first.getPlayer().equals(player) ? first : second;
        winnerScore.winATieBreak();
        return new Record(first.point(), second.point(), first.getWinnedGame(), second.getWinnedGame(), first.getTieBreak(), second.getTieBreak(), format(WINS_THE_GAME_LABEL, player.getName()), format(WINS_THE_SET_LABEL, player.getName()));
    }

    private static Record buildSetWinnerRecord(Score first, Score second, Player player) {
        initscoresPoints(first, second);
        return new Record(first.point(), second.point(), first.getWinnedGame(), second.getWinnedGame(), format(WINS_THE_GAME_LABEL, player.getName()), format(WINS_THE_SET_LABEL, player.getName()));
    }

    private static Record buildGameWinRecord(Score first, Score second, Player player) {
        initscoresPoints(first, second);
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

    private static boolean winWithAdv(Point first, Point second) {
        return (first == POINT || second == POINT) && Math.abs(first.getScore() - second.getScore()) == MIN_POINTS_DIFFERENCE;
    }

    private static boolean winWithPoints(Point first, Point second) {
        return (first == ADV || second == ADV) && Math.abs(first.getScore() - second.getScore()) >= MIN_POINTS_DIFFERENCE;
    }

    private static boolean winATieBreak(Score first, Score second) {
        return first.getWinnedGame() >= MIN_SET_WIN && second.getWinnedGame() >= MIN_SET_WIN && Math.abs(first.getWinnedGame() - second.getWinnedGame()) >= MIN_WINNING_DIFFERENCE;
    }

    private static boolean winASet(Score first, Score second) {
        return (first.getWinnedGame() >= MIN_SET_WIN || second.getWinnedGame() >= MIN_SET_WIN) && Math.abs(first.getWinnedGame() - second.getWinnedGame()) >= MIN_WINNING_DIFFERENCE;
    }

    private static boolean advRule(Score first, Score second) {
        List<Point> points = Stream.of(first, second).map(Score::point).collect(Collectors.toList());
        return points.contains(ADV) && (points.contains(FOURTY) || points.contains(DEUCE));
    }

    private static void initscoresPoints(Score first, Score second) {
        first.init();
        second.init();
    }

    public enum RuleType {
        DEUCE_RULE((first, second) -> first.point() == ADV && second.point() == ADV, GameRuleEngine::buildDeuceRecord),

        ADV_RULE(GameRuleEngine::advRule, GameRuleEngine::buildAdvRecord),

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

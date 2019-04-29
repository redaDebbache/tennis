package rules;

import model.Point;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static rules.GameRuleEngine.RuleType.*;

public class GameRuleEngineTest {

    @Test
    public void validate_deuce_rule() {
        //Given
        Point first = Point.ADV;
        Point second = Point.ADV;
        //When
        GameRuleEngine.RuleType ruleType = GameRuleEngine.RuleType.matchRule(first, second);
        //Then
        assertThat(ruleType).isEqualTo(DEUCE_RULE);
    }

    @Test
    public void validate_adv_rule() {
        //Given
        Point first = Point.ADV;
        Point second = Point.FOURTY;
        //When
        GameRuleEngine.RuleType ruleType = GameRuleEngine.RuleType.matchRule(first, second);
        //Then
        assertThat(ruleType).isEqualTo(ADV_RULE);
    }

    @Test
    public void validate_game_winning_rule() {
        //Given
        Point first = Point.POINT;
        Point second = Point.FOURTY;
        //When
        GameRuleEngine.RuleType ruleType = GameRuleEngine.RuleType.matchRule(first, second);
        //Then
        assertThat(ruleType).isEqualTo(GAME_WINNING_RULE);
    }

    @Test
    public void validate_point_winning_rule() {
        //Given
        Point first = Point.THIRTY;
        Point second = Point.FIFTEEN;
        //When
        GameRuleEngine.RuleType ruleType = GameRuleEngine.RuleType.matchRule(first, second);
        //Then
        assertThat(ruleType).isEqualTo(SIMPLE_POINT_WINNING_RULE);
    }

}
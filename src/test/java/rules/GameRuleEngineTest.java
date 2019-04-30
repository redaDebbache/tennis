package rules;

import model.Score;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static model.Point.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static rules.GameRuleEngine.RuleType.*;

@RunWith(MockitoJUnitRunner.class)
public class GameRuleEngineTest {

    @Mock
    private Score first;
    @Mock
    private Score second;

    @Test
    public void validate_deuce_rule() {
        //Given
        when(first.point()).thenReturn(ADV);
        when(second.point()).thenReturn(ADV);
        //When
        GameRuleEngine.RuleType ruleType = GameRuleEngine.RuleType.matchRule(first, second);
        //Then
        assertThat(ruleType).isEqualTo(DEUCE_RULE);
    }

    @Test
    public void validate_adv_rule() {
        //Given

        when(first.point()).thenReturn(ADV);
        when(second.point()).thenReturn(FOURTY);
        //When
        GameRuleEngine.RuleType ruleType = GameRuleEngine.RuleType.matchRule(first, second);
        //Then
        assertThat(ruleType).isEqualTo(ADV_RULE);
    }

    @Test
    public void validate_game_winning_rule() {
        //Given
        when(first.point()).thenReturn(POINT);
        when(second.point()).thenReturn(FOURTY);
        //When
        GameRuleEngine.RuleType ruleType = GameRuleEngine.RuleType.matchRule(first, second);
        //Then
        assertThat(ruleType).isEqualTo(GAME_WINNING_RULE);
    }

    @Test
    public void validate_point_winning_rule() {
        //Given
        when(first.point()).thenReturn(THIRTY);
        when(second.point()).thenReturn(FIFTEEN);
        //When
        GameRuleEngine.RuleType ruleType = GameRuleEngine.RuleType.matchRule(first, second);
        //Then
        assertThat(ruleType).isEqualTo(SIMPLE_POINT_WINNING_RULE);
    }
}
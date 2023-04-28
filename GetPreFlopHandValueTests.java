import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GetPreFlopHandValueTests {
    // getPreFlopHandValue()
    @Test
    public void expectValueOfZeroFor75o() {
        RulesBasedAgent agent = new RulesBasedAgent(1);
        // card 3 = 5c, card 18 = 7d
        int handValue = agent.getPreFlopHandValue(3, 18);
        assertEquals(0, handValue);
    }
    @Test
    public void expectValueOfOneFor55() {
        RulesBasedAgent agent = new RulesBasedAgent(1);
        // card 3 = 5c, card 16 = 7d
        int handValue = agent.getPreFlopHandValue(3, 16);
        assertEquals(1, handValue);
    }
    @Test
    public void expectValueOfTwoForATs() {
        RulesBasedAgent agent = new RulesBasedAgent(1);
        // card 12 = Ac, card 8 = Tc
        int handValue = agent.getPreFlopHandValue(12, 8);
        assertEquals(2, handValue);
    }
    @Test 
    public void expectValueOfThreeForQQ() {
        RulesBasedAgent agent = new RulesBasedAgent(1);
        // card 10 = Qc, card 23 = Qd
        int handValue = agent.getPreFlopHandValue(10, 23);
        assertEquals(3, handValue);
    }
}

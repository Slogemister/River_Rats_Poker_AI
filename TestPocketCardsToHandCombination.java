
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestPocketCardsToHandCombination {
    
    // pocketCardsToHandCombination tests
    // Pairs
    @Test
    public void pairOfPocketTwos() {
        RulesBasedAgent agent = new RulesBasedAgent(1);
        String pocketCardCombo = agent.pocketCardsToHandCombination(0, 13);
        assertEquals("22", pocketCardCombo);
    }
    @Test
    public void pairOfPocketKings() {
        RulesBasedAgent agent = new RulesBasedAgent(1);
        String pocketCardCombo = agent.pocketCardsToHandCombination(24, 11);
        assertEquals("KK", pocketCardCombo);
    }

    // Suited cards
    @Test
    public void threeOfHeartsAndSevenOfHearts() {
        RulesBasedAgent agent = new RulesBasedAgent(1);
        // 3h = card 27, 7h = card 31
        String pocketCardCombo = agent.pocketCardsToHandCombination(27, 31);
        assertEquals("73s", pocketCardCombo);
    }
    @Test 
    public void aceOfClubsandJackOfClubs() {
        RulesBasedAgent agent = new RulesBasedAgent(1);
        // Ac = card 12, Jc = card 9
        String pocketCardCombo = agent.pocketCardsToHandCombination(12, 9);
        assertEquals("AJs", pocketCardCombo);
    }

    // Non-suited cards
    @Test
    public void twoOfSpadesAnd4ofHearts() {
        RulesBasedAgent agent = new RulesBasedAgent(1);
        // 2s = card 39, and 4h = card 28
        String pocketCardCombo = agent.pocketCardsToHandCombination(39, 28);
        assertEquals("42o", pocketCardCombo);
    }
    @Test 
    public void kingOfDiamondsAndQueenOfClubs() {
        RulesBasedAgent agent = new RulesBasedAgent(1);
        // Kd = card 24, and Qc = card 10
        String pocketCardCombo = agent.pocketCardsToHandCombination(24, 10);
        assertEquals("KQo", pocketCardCombo);
    }
}

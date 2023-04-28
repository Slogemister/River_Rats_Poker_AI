public class RulesBasedAgent extends Player {

    private final int num;

    private final String[][] CARDCOMBOARRAY = 
        {
            {"AA", "AKs", "AQs", "AJs", "ATs", "A9s", "A8s", "A7s", "A6s", "A5s", "A4s", "A3s", "A2s"},
            {"AKo", "KK", "KQs", "KJs", "KTs", "K9s", "K8s", "K7s", "K6s", "K5s", "K4s", "K3s", "K2s"},
            {"AQo", "KQo", "QQ", "QJs", "QTs", "Q9s", "Q8s", "Q7s", "Q6s", "Q5s", "Q4s", "Q3s", "Q2s"},
            {"AJo", "KJo", "QJo", "JJ", "JTs", "J9s", "J8s", "J7s", "J6s", "J5s", "J4s", "J3s", "J2s"},
            {"ATo", "KTo", "QTo", "JTo", "TT", "T9s", "T8s", "T7s", "T6s", "T5s", "T4s", "T3s", "T2s"},
            {"A9o", "K9o", "Q9o", "J9o", "T9o", "99", "98s", "97s", "96s", "95s", "94s", "93s", "92s"},
            {"A8o", "K8o", "Q8o", "J8o", "T8o", "98o", "88", "87s", "86s", "85s", "84s", "83s", "82s"},
            {"A7o", "K7o", "Q7o", "J7o", "T7o", "97o", "87o", "77", "76s", "75s", "74s", "73s", "72s"},
            {"A6o", "K6o", "Q6o", "J6o", "T6o", "96o", "86o", "76o", "66", "65s", "64s", "63s", "62s"},
            {"A5o", "K5o", "Q5o", "J5o", "T5o", "95o", "85o", "75o", "65o", "55", "54s", "53s", "52s"},
            {"A4o", "K4o", "Q4o", "J4o", "T4o", "94o", "84o", "74o", "64o", "54o", "44", "43s", "42s"},
            {"A3o", "K3o", "Q3o", "J3o", "T3o", "93o", "83o", "73o", "63o", "53o", "43o", "33", "32s"},
            {"A2o", "K2o", "Q2o", "J2o", "T2o", "92o", "82o", "72o", "62o", "52o", "42o", "32o", "22"}
        };
    
    /**
     * agentpreFlopRange is the what the rules based agent will
     * use in deciding when to fold, check/call, or bet/raise
     * during the pre-flop betting round.
     * Each index corresponds to the card combination range
     * it gets from getHandValue() method.
     * 
     * Meaning of the numbers:
     * 0 = folding the hand, not in range
     * 1 = (low range) call in most cases and sometimes raise, will fold to most bets
     * 2 = (mid range) raise in most cases and sometimes call, will fold sometimes to big bets
     * 3 = (high range) raises all the time, won't fold off
     */
    private int[][] agentPreFlopRange = 
    {
        {3, 3, 3, 3, 2, 2, 1, 1, 1, 1, 1, 1, 1},
        {3, 3, 3, 2, 2, 1, 1, 1, 1, 1, 0, 0, 0},
        {2, 2, 3, 2, 1, 1, 1, 0, 0, 0, 0, 0, 0},
        {2, 1, 1, 3, 1, 1, 1, 0, 0, 0, 0, 0, 0},
        {1, 1, 1, 1, 3, 1, 0, 0, 0, 0, 0, 0, 0},
        {1, 1, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0},
        {1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };


    public RulesBasedAgent(int num) {
        this.num = num;
    }

    @Override
    public String getScreenName() {
        return "Rules Based Agent: " + this.num;
    }

    @Override
    public String getAction(TableData data) {
        // PRE-FLOP: (IF current round is PRE-FLOP):
            // FIND card combination in the hand range
            String cardCombo = pocketCardsToHandCombination(
                data.getPocket()[0], data.getPocket()[1]
            );
            // FIND hand value in terms of agent's own range
            int preFlopHandValue = getPreFlopHandValue(
                data.getPocket()[0], data.getPocket()[1]
            );
            
            // IF current pocket cards aren't in the acceptable range
            if (preFlopHandValue == 0) {
                return "fold";
            }
                // FOLD
            // ELSE:
            // WHILE the flop has not been seen:
                // Choose to either limp in (CALL/CHECK) or raise
                // IF (someone has bet before or after your turn):
                    // See if cards are in the top portion of your range
                        // If Cards are in top 50%, prefer to call bet
                        // If Cards are in top 25%, prefer to raise (if able)
                        // FOLD off it bet is big (lower 50% of range)
        // FLOP through RIVER:
            // Determine the BestHand you can make with your cards
            // WHILE the current round has not ended:
                // IF current hand is the Nuts-range (straight-flush through trips):
                    // BET  or call (betting limit reached)
                    // Do this through the entire round (even if someone else raises after you)
                // ELSE IF current hand is the medium-range (high/medium two pairs, over pair, top pair with high kicker):
                    // Choose to Call 
                    // If facing a bet:
                        // Choose to call (bluff-catch) or fold
                // ELSE (current hand is in the lower-range: low/made two pairs, top pair with low kicker, lone pairs, nothing):
                    // Choose to Call or Raise/Bet
                    // If facing a bet:
                        // Prefer to FOLD, but occasionally CALL and rarely Re-Raise

        return "fold";
    }

    /**
     * Takes a string representation of pocket cards, in the form of <rank><suit><rank><suit>
     * and returns the combination value of the cards. 
     * 
     * @param card1 (int)
     * @param card2 (int)
     * @return string
     */
    public String pocketCardsToHandCombination(int card1, int card2) {
        int rankOfCard1 = card1 % 13;
        int suitOfCard1 = card1 / 13;

        int rankOfCard2 = card2 % 13;
        int suitOfCard2 = card2 / 13;

        // Suited cards
        int higherRankingCard = Math.max(rankOfCard1, rankOfCard2);
        int lowerRankingCard = Math.min(rankOfCard1, rankOfCard2);
        if (suitOfCard1 == suitOfCard2) {
            return CARDCOMBOARRAY[12 - higherRankingCard][12 - lowerRankingCard];
        }
        // Unsuited cards
        else {
            return CARDCOMBOARRAY[12 - lowerRankingCard][12 - higherRankingCard];
        }
    }

    /**
     * Takes the agents's 2 pocket cards as indices to retrieve the value from agentPreFlopRange
     * int[][] array.
     * 
     * @param card1 (int)
     * @param card2 (int)
     * @return (int)
     */
    public int getPreFlopHandValue(int card1, int card2) {
        int rankOfCard1 = card1 % 13;
        int suitOfCard1 = card1 / 13;
        int rankOfCard2 = card2 % 13;
        int suitOfCard2 = card2 / 13;

        if (suitOfCard1 == suitOfCard2) {
            return agentPreFlopRange[12 - Math.max(rankOfCard1, rankOfCard2)][12-Math.min(rankOfCard1, rankOfCard2)];

        } else {
            return agentPreFlopRange[12 - Math.min(rankOfCard1, rankOfCard2)][12-Math.max(rankOfCard1, rankOfCard2)];
        }
    }
}

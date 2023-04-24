public class RulesBasedAgent extends Player {

    private final int num;

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
            // IF current pocket cards aren't in the acceptable range
                // FOLD
            // ELSE:
            // WHILE the flop has not been seen:
                // Choose to either limp in (CALL/CHECK) or raise
                // IF (someone has bet before or after your turn):
                    // See if cards are in the top portion of your range
                        // If Cards are in top 50%, prefer to call bet
                        // If Cards are in top 25%, prefer to raise (if able)
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
}

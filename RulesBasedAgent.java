import java.util.ArrayList;

public class RulesBasedAgent extends Player {

    private int num = 0;

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

    private int lastRound;
    private int nextIndex;

    private String[] topBestHandRange = {"straight flush", "four of a kind", "full house", "flush", "straight"};
    private String[] mediumBestHandRange = {"three of a kind", "two of a kind"};
    private String[] lowerBestHandRange = {"one pair", "no pair"};

    public RulesBasedAgent(int num) {
        this.num = num;
        this.lastRound = 1;
        this.nextIndex = 0;
    }

    @Override
    public void newHand(int hand, int[] bank) {
        System.out.println("Players start this round with bank totals of:");
        for (int x = 0; x < bank.length; x++) {
            System.out.println((x + 1) + " has " + bank[x]);
        }
        lastRound = 1;
        nextIndex = 0;
    }

    @Override
    public String getScreenName() {
        return "Rules Based Agent: " + this.num;
    }

    @Override
    public String getAction(TableData data) {
        int currentRound = data.getBettingRound();

        // Match starting implementation of AgentHumanCommandLine:
        if (lastRound < currentRound) {
            ArrayList<String> lastRoundActions = data.getHandActions(lastRound);
            System.out.println();

            for (int x = nextIndex; x < lastRoundActions.size(); x++) {
                System.out.println(lastRoundActions.get(x));
            }
            System.out.println();
            System.out.println("Starting next round of betting.");
            nextIndex = 0;
            lastRound = currentRound;
        }

        ArrayList<String> thisRoundActions = data.getHandActions(currentRound);
        
        System.out.println();
        int len = thisRoundActions.size();

        for (int x = nextIndex; x < thisRoundActions.size(); x++) {
            System.out.println(thisRoundActions.get(x));
        }

        nextIndex = thisRoundActions.size() + 1;

        System.out.println();

        // PRE-FLOP: (IF current round is PRE-FLOP):
        if (currentRound == 1) {
            // FIND card combination in the hand range
            System.out.println("Agent's pocket cards are: ");
            System.out.print(EstherTools.intCardToStringCard(data.getPocket()[0]) + " ");
            System.out.println(EstherTools.intCardToStringCard(data.getPocket()[1]) + " ");
            System.out.println();

            String cardCombo = pocketCardsToHandCombination(
                data.getPocket()[0], data.getPocket()[1]
            );
            System.out.println("Card combination from pocket cards: " + cardCombo);
            System.out.println();

                // FIND hand value in terms of agent's own range
            int preFlopHandValue = getPreFlopHandValue(
                data.getPocket()[0], data.getPocket()[1]
            );
            System.out.println("Hand strength value: " + preFlopHandValue);
            System.out.println();
            
            // IF current pocket cards aren't in the acceptable range
            if (preFlopHandValue == 0) {
                // FOLD
                System.out.println("Agent folds " + "\n");
                return "fold";
            }
            // If Cards are in top of range (3), raise all the time, else call.
            else if (preFlopHandValue == 3) {
                // If betting limit isn't reached, raise
                if (data.getValidActions().contains("bet")) {
                    System.out.println("Agent bets " + '\n');
                    return "bet";
                }
                else if (data.getValidActions().contains("raise")) {
                    System.out.println("Agent raises " + "\n");
                    return "raise";
                }
                else {
                    System.out.println("Agent calls" + "\n");
                    return "call";
                }
            }
            // If Cards are in middle of range:
            else if (preFlopHandValue == 2) {
                // If no one has raised, bet
                if (data.getValidActions().contains("bet")) {
                    System.out.println("Agent raises" + "\n");
                    return "bet";
                }
                // Else someone has raised before the agent
                else {
                    if (data.getValidActions().contains("raise")) {
                        // Randomly decide to raise bet or call
                        // Starting weights will be a 50% bet, 50% call
                        double raiseOrCall = Math.random();
                        if (raiseOrCall > 0.5) {
                            System.out.println("Agent raises" + "\n");
                            return "raise";
                        } else {
                            System.out.println("Agent calls" + "\n");
                            return "call";
                        }
                    }
                    // Betting limit reached, call bet
                    System.out.println("Agent checks" + "\n");
                    return "check";
                }
            }
            // Lower portion of range, prefer to fold, else call
            else {
                // If player raised before agent acts, decide to call or fold
                if (data.getValidActions().contains("raise")) {
                    double callOrFold = Math.random();
                    // Default weighting is 50% call, 50% fold
                    if (callOrFold > 0.5) {
                        System.out.println("Agent calls" + "\n");
                        return "call";
                    } else {
                        System.out.println("Agent folds" + "\n");
                        return "fold";
                    }
                } else {
                    // If first to act, initially bet
                    System.out.println("Agent raises" + "\n");
                    return "raise";
                }
            }
        }
        // FLOP through RIVER:
        else {
            // Determine the BestHand you can make with your cards
            BestHand currentBestHand = EstherTools.getBestHand(data.getPocket(), data.getBoard());

            boolean isInTopRange = false;
            for (int i = 0; i < topBestHandRange.length; i++) {
                if (currentBestHand.getComboString() == topBestHandRange[i]) {
                    isInTopRange = true;
                }
            }
            // IF current hand is the Nuts-range (straight-flush through straight):
            if (isInTopRange) {
                // Reraise if betting limit isn't reached
                if (data.getValidActions().contains("raise")) {
                    System.out.println("Agent raises" + "\n");
                    return "raise";
                } else {
                    // if first to act, bet first
                    if (data.getValidActions().contains("bet")) {
                        System.out.println("Agent bets" + "\n");
                        return "bet";
                    } else {
                        // betting limit reached, call
                        System.out.println("Agent calls" + "\n");
                        return "call";
                    }
                }
                // Do this through the entire round (even if someone else raises after you)
            }

            boolean isInMidRange = false;
            for (int i = 0; i < mediumBestHandRange.length; i++) {
                if (currentBestHand.getComboString() == mediumBestHandRange[i]) {
                    isInMidRange = true;
                }
            }
            if (isInMidRange) {
                 // ELSE IF current hand is the medium-range (three-of-a-kind and two pairs):
                 if (data.getValidActions().contains("check")) {
                    // Choose to Call 
                    System.out.println("Agent checks " + "\n");
                    return "check";
                } else {
                    // If facing a bet:
                    // Choose to call (bluff-catch) or fold
                    // Default values for mid-range, call 60%, fold 40%
                    double callOrFold = Math.random();
                    if (callOrFold > 0.4) {
                        System.out.println("Agent calls" + "\n");
                        return "call";
                    } else {
                        System.out.println("Agent folds" + "\n");
                        return "fold";
                    }
                }
            }

            boolean isInLowRange = false;
            for (int i = 0; i < lowerBestHandRange.length; i++) {
                if (currentBestHand.getComboString() == lowerBestHandRange[i]) {
                    isInLowRange = true;
                }
            }
            if (isInLowRange) {
                // ELSE (current hand is in the lower-range: low/made two pairs, top pair with low kicker, lone pairs, nothing):
                if (data.getValidActions().contains("check")) {
                    // If no one has bet, prefer to call but sometimes raise
                    // Default values will be call 90%, bet 10%
                    double callOrBet = Math.random();
                    if (callOrBet > 0.1) {
                        System.out.println("Agent checks \n");
                        return "check";
                    } else {
                        System.out.println("Agent bets \n");
                        return "bet";
                    }
                } else {
                    // If facing a bet, prefer to fold, 
                    // but sometimes call and rarely re-raise (if able)
                    double foldCallOrRaise = Math.random();
                    if (data.getValidActions().contains("raise")) {
                        // If raising is possible, default values are
                        // 60% fold, 30% call, 10% raise
                        if (foldCallOrRaise > 0.4) {
                            System.out.println("Agent folds \n");
                            return "fold";
                        } else if (foldCallOrRaise <= 0.4 && foldCallOrRaise > 0.1) {
                            System.out.println("Agent calls \n");
                            return "call";
                        } else {
                            System.out.println("Agent raises \n");
                            return "raise";
                        }
                    } else {
                        // Betting limit reached, default values are
                        // 60% fold, 40% call
                        if (foldCallOrRaise > 0.4) {
                            System.out.println("Agent folds \n");
                            return "fold";
                        } else {
                            System.out.println("Agent calls \n");
                            return "call";
                        }
                    }
                }
            }
        }
        System.out.println("Error in execution, fold by default \n");
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

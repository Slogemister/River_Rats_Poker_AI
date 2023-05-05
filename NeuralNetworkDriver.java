import java.util.List;






public class NeuralNetworkDriver {
    public static void main(String[] args){
        /* 
        We want our input to be 
        - Opponent actions or change in pot size? (simpiler as well as still being able) - 3 (call(match rasie), raise(increases extra), fold(0))
        - Card in hand - 2 nodes (1 for each card number in hand)
        - card on table - 5 nodes (1 for each card that could be on the table)
        
        https://www.infoworld.com/article/3685569/how-to-build-a-neural-network-in-java.html

        wont need these below
        p1  p2  p3  p4
        {0} {0} {0} {0} "call/check"
        {0} {1} {1} {1} "fold"
        {1} {0} {0} {0} "raise"
        */

        // Need to look into unsuprevised training. There is no specified output for a 
        // given input, instead tell the agent (specific NN) that its doing good (don't change weights much). 
        // Tell the losing NN that its doing bad (make a good amount of changes to weights)


        // Make an agent that will take an input, play the game based how it thinks, and be rewarded for playing
        // as well as gaining points. If it wins it will be rewarded, and won't change much weights.
        // if it loses by a lot, then it will change weights by a lot. allow the bot to play in multiple games
        // and see the progress.

        double [][] X = {
            // All possible input nodes
            // Call? raise? or fold? is a collective pot decision. (not based on player as of rn)
            //{call?, raise?, fold?, card1, card2, table1, table2, table3, table4, table5},
            {0, 0},
            {1, 0},
            {0, 1},
            {1, 1}
        };

        /*
         * We want our output to be an action
         * "call/check"
         * "fold"
         * "raise"
         */
        double [][] Y = {
            // {fold, raise, check/call}
            {0},
            {1},
            {1},
            {0}
        };

        NeuralNetwork nn = new NeuralNetwork(2, 10, 1);

        List<Double> output;

        nn.fit(X, Y, 50000);

        double [][] input = {
                            {0, 0}, 
                            {0, 1}, 
                            {1, 0},
                            {0, 0}};
        double [][] input2 = {
                            {1, 0},
                            {0, 1},
                            {0, 0},
                            {1, 1}                            
        };

        for (double d[] : X){
            output = nn.predict(d);
            System.out.println(output.toString());
        }

        System.out.println();

        for (double d[] : input2){
            output = nn.predict(d);
            System.out.println(output.toString());
        }
    }
}

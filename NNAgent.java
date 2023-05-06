import java.util.Collections;

public class NNAgent extends Player{
    
    private final int num;

    private NeuralNetwork nn;
    
    public NNAgent(int num, boolean train){
        this.num = num;
        
        if (train){
            // Trains the neural network agent if train boolean is true
            // if train boolean is false then the neural network agent
            // will play the game based on random weights.
            nn = new NeuralNetwork(8, 10, 5); 
            trainNN();
        }
    }

    public NNAgent(int num, NeuralNetwork preTrainedNN){
        // initializer used for training the agent
        this.num = num;
        nn = preTrainedNN;
        
    }
    
    public String getScreenName(){
        // returns the nameof the agent
        return "NeuralNetAgent " + this.num;
    }

    @Override
    public String getAction(TableData data){
        // returns the action that the NNAgent decides

        // Initializes table cards for the input array
        // no table cards showing
        int t1 = -1;
        int t2 = -1;
        int t3 = -1;
        int t4 = -1;
        int t5 = -1;

        String actions = data.getValidActions();

        if (data.getBoard().length > 4){
            // all table cards are shown
            t1 = data.getBoard()[0];
            t2 = data.getBoard()[1];
            t3 = data.getBoard()[2];
            t4 = data.getBoard()[3];
            t5 = data.getBoard()[4];
        }
        else if (data.getBoard().length > 3){
            // 4 table cards are shown
            t1 = data.getBoard()[0];
            t2 = data.getBoard()[1];
            t3 = data.getBoard()[2];
            t4 = data.getBoard()[3];
        }
        else if (data.getBoard().length > 0){
            // 3 table cards are shown
            t1 = data.getBoard()[0];
            t2 = data.getBoard()[1];
            t3 = data.getBoard()[2];
        }

        // input array that gets pushed into the neural network
        double [] input = {
                            data.getTablePot(),    // total table pot
                            data.getPocket()[0],   // 1st pocket card
                            data.getPocket()[1],   // 2nd pocket card
                            t1,                    // table card 1
                            t2,                    // table card 2
                            t3,                    // table card 3
                            t4,                    // table card 4
                            t5                     // table card 5
                            };

        // all possible actions in the game of texas hold'em
        String[] options = {"fold", "check", "call", "bet", "raise"};                            
        
        // all possible actions for the specified hand
        String[] choices = actions.split(",");
        
        // Neural networks choice based on the input
        // used to determine what the agent decision
        // is if its not an option
        int max = nn.predict(input).indexOf(Collections.max(nn.predict(input)));

        if (actions.contains("check")){
            if (max == 4){
                max = 3;
            }
            else if (max == 2){
                max = 1;
            }
        }

        if (actions.contains("call")){
            if(choices.length == 3){
                if (max == 1){
                    max = 2;
                }
                else if (max == 3){
                    max = 4;
                }
            }
            else {
                if (max == 1 | max > 2){
                    max = 2;
                }
            }
        }

        String choice = options[max];

        return choice;
    }

    private void trainNN(){
        // training function that trains the Neural network agent

        NNAgent agent = new NNAgent(1, nn);
        
        int epochs = 10000;

        for (int i = 0; i < epochs; i++){
            Player[] players = new Player[6];

            players[0] = new AgentAlwaysFold(1);
            players[1] = new AgentAlwaysCall(1);
            players[2] = new AgentAlwaysRaise(1);
            players[3] = new AgentAlwaysRaise(2);
            players[4] = new AgentAlwaysCall(2);
            players[5] = agent;

            Dealer dealer = new Dealer(players.length, 123456789);
            GameManager g = new GameManager(players, dealer, false);
            int[] end = g.playGame();

            nn.trainReinforcement(end[5]);

            System.out.println("Training Game " + i + " agent score: " + end[5]);
            System.out.println();
            }

        nn.setBestWeights();
    }
}

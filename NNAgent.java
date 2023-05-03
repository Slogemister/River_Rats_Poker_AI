import java.util.Collections;

public class NNAgent extends Player{
    
    private final int num;
    private Matrix weights;
    private int t1 = -1;
    private int t2 = -1;
    private int t3 = -1;
    private int t4 = -1;
    private int t5 = -1;
    private NeuralNetwork nn;
    

    public NNAgent(int num/*, Matrix weights*/){
        this.num = num;
        this.weights = weights;
        nn = new NeuralNetwork(10, 20, 5); // assign weights some how for already trained NN
        
        // train the NN here
        //nn.trainReinforcement()
    }

    public NNAgent(int num, NeuralNetwork preTrainedNN){
        this.num = num;
        nn = preTrainedNN;
        
    }


    
    public String getScreenName(){
        return "NeuralNetAgent " + this.num;
    }

    @Override
    public String getAction(TableData data){
        // List of possible actions to be taken in the given state of the game.

        String actions = data.getValidActions();

        if (data.getBoard().length > 4){
            t1 = data.getBoard()[0];
            t2 = data.getBoard()[1];
            t3 = data.getBoard()[2];
            t4 = data.getBoard()[3];
            t5 = data.getBoard()[4];
        }
        else if (data.getBoard().length > 3){
            t1 = data.getBoard()[0];
            t2 = data.getBoard()[1];
            t3 = data.getBoard()[2];
            t4 = data.getBoard()[3];
        }
        else if (data.getBoard().length > 0){
            t1 = data.getBoard()[0];
            t2 = data.getBoard()[1];
            t3 = data.getBoard()[2];
        }

        double [] input = {
                            0, // Players actions (Use how much each player has put in the pot)
                            0, // players actions
                            0, // players actions
                            data.getPocket()[0], // 1st pocket card
                            data.getPocket()[1], // 2nd pocket card
                            t1,
                            t2,
                            t3,
                            t4,
                            t5
                            };

        String[] options = {"fold", "check", "call", "bet", "raise"};

        System.out.println("Actions " + actions);
        for (int i = 0; i < input.length; i++){
            System.out.println("Input Matrix " + i + " " + input[i]);
        }

        String[] choices = actions.split(",");

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
        
        System.out.println(nn.predict(input).indexOf(Collections.max(nn.predict(input))));
        System.out.println();
        System.out.println("AI Choice is " + choice);

        return choice;
    }

    @Override
    public void newHand(int handNumber, int[] cashBalances){
        // Decide what weights need to be changed and then change the weights
        // for the next round.
        // possibly implement mutations??
    }

    @Override
    public void handResults(Results results){
        // results of the previous game.
        System.out.println(results);
    }
}

import java.util.ArrayList;

public class NeuralNetworkTestTraining {
    public static void main(String[] args){

        NeuralNetwork nn = new NeuralNetwork(10, 20, 5);
        NNAgent agent = new NNAgent(1, nn);
        
        for (int i = 0; i < 100000; i++){
            Player[] players = new Player[6];
            //Adjust the right side of these assignments to select new agents
            //players[0] = new AgentRandomPlayer(1);
            players[0] = new AgentAlwaysRaise(2);
            players[1] = new AgentAlwaysCall(1);
            //players[2] = new AgentRandomPlayer(2);
            players[2] = new AgentAlwaysCall(2);
            players[3] = new AgentAlwaysRaise(1);
            //players[4] = new AgentRandomPlayer(3);
            players[4] = new AgentAlwaysFold(1);
            players[5] = agent;

            Dealer dealer = new Dealer(players.length, 123456789);
            GameManager g = new GameManager(players, dealer, false);
            int[] end = g.playGame();
            //System.out.println("Final Totals");
            //for (int x = 0; x < end.length; x++) {
            //System.out.println((x + 1) + " "
            //            + players[x].getScreenName() + " had " + end[x]);

            ArrayList<Matrix> bestNeuralNet = nn.trainReinforcement(end[5]);

            System.out.println("Training Game " + i + " agent score: " + end[5]);
            System.out.println();
            }
        }
    }

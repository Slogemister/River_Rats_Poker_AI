import java.util.List;
import java.util.Random;

public class NeuralNetwork {
    // Weights_ih = the weights matrix for the input and hidden layer
    // weights_ho = the weights matrix for hidden and output layer
    // bias_h = bias matrix for the hidden layer
    // bias_o = bias matrix for the output matrix
    // l_rate = the learning rate

    Matrix weights_ih, weights_ho, bias_h, bias_o;
    double l_rate=0.01;
    int i, h, o;

    public NeuralNetwork(int i, int h, int o){
        this.h = h;
        this.i = i;
        this.o = o;

        weights_ih = new Matrix(h, i);
        weights_ho = new Matrix(o, h);

        bias_h = new Matrix(h, 1);
        bias_o = new Matrix(o, 1);
    }

    public List<Double> predict(double [] X){
        // pridection function which does forward 
        // propegation over the neural network
        // finds the answer given input on the already trained network
        
        Matrix input = Matrix.fromArray(X);
        Matrix hidden = Matrix.multiply(weights_ih, input);
        hidden.add(bias_h);
        hidden.sigmoid();

        Matrix output = Matrix.multiply(weights_ho, hidden);
        output.add(bias_o);
        output.sigmoid();
        

        //return output.toArray();
        System.out.println("Input->Hidden: " + weights_ih.toArray());
        System.out.println("Hidden->Output: " + weights_ho.toArray());
        System.out.println("Hidden Bias: " + bias_h.toArray());
        System.out.println();
        System.out.println("OutputArray: " + output.toArray());

        return output.toArray();

    }

    public void train(double [] X, double [] Y){
        // needs to be updated to enable Reinforcement learning. To do this 
        // base how well the agent based on how well his score is at the end 
        // of each hand played. 
        // Possibly add in mutation to this as well?

        Matrix input = Matrix.fromArray(X);
        Matrix hidden = Matrix.multiply(weights_ih, input);
        hidden.add(bias_h);
        hidden.sigmoid();

        Matrix output = Matrix.multiply(weights_ho, hidden);
        output.add(bias_o);
        output.sigmoid();

        Matrix target = Matrix.fromArray(Y);
        Matrix error = Matrix.subtract(target, output);
        Matrix gradient = output.dsigmoid();
        gradient.multiply(error);
        gradient.multiply(l_rate);

        Matrix hidden_T = Matrix.transpose(hidden);
        Matrix who_delta = Matrix.multiply(gradient, hidden_T);

        weights_ho.add(who_delta);
        bias_o.add(gradient);

        Matrix who_T = Matrix.transpose(weights_ho);
        Matrix hidden_errors = Matrix.multiply(who_T, error);

        Matrix h_gradient = hidden.dsigmoid();
        h_gradient.multiply(hidden_errors);
        h_gradient.multiply(l_rate);

        Matrix i_T = Matrix.transpose(input);
        Matrix wih_delta = Matrix.multiply(h_gradient, i_T);

        weights_ih.add(wih_delta);
        bias_h.add(h_gradient);
        

    }

    public void trainReinforcement(double [] input, int score){

        // info needed: Score from last round,
        //              Old weights (weights_ih, weights_ho)
        //              Old Bias (bias_h, bias_o)
        //              Input that was used
        Random random = new Random();
    
        Matrix[] weights = {weights_ih, weights_ho, bias_h, bias_o};
        // possible weights: {weights_ih, weights_ho, bias_h, bias_o}
        // determine what weight is needed to be changed.

        int propertyToChange = (int)(random.nextInt(0, 4));

        Matrix propertyChanged = weights[propertyToChange];
        
        System.out.println("Changing type: " + propertyToChange);

        int row = 0;
        int column = 0;

        if (propertyToChange == 0){
            row = random.nextInt(0, h);
            column = random.nextInt(0, i);
        }
        else if (propertyToChange == 1){
            row = random.nextInt(0, o);
            column = random.nextInt(0, h);
        }
        else if (propertyToChange == 2){
            row = random.nextInt(0, h);
            column = 1;
        }
        else if (propertyToChange == 3){
            row = random.nextInt(0, o);
            column = 1;
        }

        // set the old weight = oldWeight
        int oldRow = row;
        int oldColumn = column;
        double oldWeight = propertyChanged.getWeight(row, column);

        // create a new random weight for the new weight
        double newWeight = random.nextDouble(-1, 1);
        propertyChanged.changeWeight(oldRow, oldColumn, newWeight);

        //Play game to see how the change did. If the score got worse
        // revert the changes and change a differnt weight
        // If score was good keep weight and change another weight.

        //Play Game
    

        //If score > oldScore Then keep changes


        //Else discaerd changes


        //go to next







    }

    public void fit(double [][] X , double [][] Y , int epochs){
        for (int i = 0; i < epochs; i++){
            int sampleN = (int)(Math.random() * X.length);
            this.train(X[sampleN], Y[sampleN]);
        }
    }
}

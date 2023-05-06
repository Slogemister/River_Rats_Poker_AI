import java.util.ArrayList;
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

    int row = 0;
    int column = 0;
    int oldRow = 0;
    int oldColumn = 0;
    int bestScore = -100000;

    double oldWeight = 0;

    Matrix oldProperty;

    Matrix bestWeights_ih, bestWeights_ho, bestBias_h, bestBias_o;


    public NeuralNetwork(int i, int h, int o){
        // creates a neural network with specified neurons 
        // in the input, hidden, and output layers.
        this.h = h;
        this.i = i;
        this.o = o;

        weights_ih = new Matrix(h, i);
        weights_ho = new Matrix(o, h);
        bias_h = new Matrix(h, 1);
        bias_o = new Matrix(o, 1);

        bestWeights_ho = weights_ho;
        bestWeights_ih = weights_ih;
        bestBias_h = bias_h;
        bestBias_o = bias_o;
    }
    public NeuralNetwork(Matrix weights_Ih, Matrix weights_Ho, Matrix bias_H, Matrix bias_O){
        // creates a neural network bsed on given matrices
        weights_ih = weights_Ih;
        weights_ho = weights_Ho;
        bias_h = bias_H;
        bias_o = bias_O;

        bestWeights_ho = weights_ho;
        bestWeights_ih = weights_ih;
        bestBias_h = bias_h;
        bestBias_o = bias_o;
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

        return output.toArray();

    }

    public ArrayList<Matrix> trainReinforcement(int score){

        Random random = new Random();
    
        Matrix[] weights = {weights_ih, weights_ho, bias_h, bias_o};
        // possible weights: {weights_ih, weights_ho, bias_h, bias_o}
        // determine what weight is needed to be changed.

        int propertyToChange = (int)(random.nextInt(0, 4));

        Matrix propertyChanged = weights[propertyToChange];
        
        System.out.println("Changing type: " + propertyToChange);

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
            column = 0;
        }
        else if (propertyToChange == 3){
            row = random.nextInt(0, o);
            column = 0;
        }

        // create a new random weight for the new weight
        double newWeight = random.nextDouble(-1, 1);

        if (score < bestScore){
            // Revert to old weight
            System.out.println("Bad Weight ========================================================= " + bestScore + " vs " + score);
            oldProperty.changeWeight(oldRow, oldColumn, oldWeight);
            
        }
        else{
            bestScore = score;
            bestBias_h = bias_h;
            bestBias_o = bias_o;
            bestWeights_ho = weights_ho;
            bestWeights_ih = weights_ih;
        }
        System.out.println("row " + row);
        System.out.println("Column " + column);
        System.out.println(propertyChanged.getWeight(row, column));
        propertyChanged.changeWeight(row, column, newWeight);

        // set the old weight = oldWeight
        //old weight before any changes happend
        oldWeight = propertyChanged.getWeight(row, column);

        oldProperty = propertyChanged;
        oldRow = row;
        oldColumn = column;
        propertyChanged.changeWeight(oldRow, oldColumn, newWeight);

        ArrayList<Matrix> returnList = new ArrayList<Matrix>();
        returnList.add(bestWeights_ih);
        returnList.add(bestWeights_ho);
        returnList.add(bestBias_h);
        returnList.add(bestBias_o);

        return returnList;

    }

    public void setBestWeights(){
        weights_ho = bestWeights_ho;
        weights_ih = bestWeights_ih;
        bias_h = bestBias_h;
        bias_o = bestBias_o;
    }

    public void getBestWeights(){
        System.out.println("======================================= Best Weights =================================");
        System.out.println("Weights_ho = " + weights_ho.toArray());
        System.out.println("Weights_ih = " + weights_ih.toArray());
        System.out.println("Bias_o = " + bias_o.toArray());
        System.out.println("Bias_h = " + bias_h.toArray());
        System.out.println("======================================================================================");
    }
}

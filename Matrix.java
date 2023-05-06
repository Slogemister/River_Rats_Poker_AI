
import java.util.ArrayList;
import java.util.List;

public class Matrix {

    double [][] data;
    int rows, cols;

    public Matrix(int rows, int cols){
        // Creates a new matrix and initializes it with random values between 
        // 1 and -1
        data = new double [rows][cols];
        this.rows = rows;
        this.cols = cols;

        for (int i = 0; i < rows; i++){

            for (int j = 0; j < cols; j++){
                data[i][j] = Math.random() * 2 - 1;
            }
        }
    }

    public void add(double scalar){
        // allows to add a scalar to the whole matrix
        for (int i = 0; i < rows; i++){

            for (int j = 0; j < cols; j++){
                this.data[i][j] += scalar;
            }
        }
    }

    public void add(Matrix m){
        // allows to add a matrix with a matrix
        if (cols != m.cols || rows != m.rows){
            System.out.println("Shape Mismatch");
            return;

        }
        for (int i =0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                this.data[i][j] += m.data[i][j];
            }
        }
    }

    public static Matrix subtract(Matrix a, Matrix b){
        // Allows subtraction between two matrices
        Matrix temp = new Matrix(a.rows, a.cols);
        for (int i = 0; i < a.rows; i++){
            for (int j = 0; j < a.cols; j++){
                temp.data[i][j] = a.data[i][j] - b.data[i][j];
            }
        }
        return temp;
    }

    public static Matrix transpose(Matrix a){
        // Creates a matrix that is the transpose of the given matrix
        Matrix temp = new Matrix(a.cols, a.rows);
        for (int i = 0; i < a.rows; i++){
            for(int j = 0; j < a.cols; j++){
                temp.data[j][i] = a.data[i][j];
            }
        }
        return temp;
    }

    public static Matrix multiply(Matrix a, Matrix b){
        // takes the dot product of both matrices
        Matrix temp = new Matrix(a.rows, b.cols);

        for (int i = 0; i < temp.rows; i++){
            for (int j = 0; j < temp.cols; j++){
                double sum = 0;
                for (int k = 0; k < a.cols; k++){
                    sum += a.data[i][k] * b.data[k][j];
                }
                temp.data[i][j] = sum;
            }
        }
        return temp;
    }

    public void multiply(Matrix a){
        // element wise multiplication of the matrices
        for (int i = 0; i < a.rows; i++){
            for (int j = 0; j < a.cols; j++){
                this.data[i][j] *= a.data[i][j];
            }
        }
    }

    public void multiply(double a){
        // Scaler multiplication over the matrix
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                this.data[i][j] *= a;
            }
        }
    }

    public void sigmoid(){
        // Applies the sigmoid function on a matrix
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                this.data[i][j] = 1 / (1 + Math.exp(- this.data[i][j]));
            }
        }
    }

    public Matrix dsigmoid(){
        // applies the derivative of sigmoid on a matrix
        // (useful for back propagation)
        Matrix temp = new Matrix(rows, cols);
        for(int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                temp.data[i][j] = this.data[i][j] * (1 - this.data[i][j]);
            }
        }
        return temp;
    }

    public static Matrix fromArray(double [] x){
        // helper function to make an array a matrix
        Matrix temp = new Matrix(x.length, 1);
        for (int i = 0; i < x.length; i++){
            temp.data[i][0] = x[i];
        }
        return temp;
    }

    public List<Double> toArray(){
        // creats an array based on a matrix
        List<Double> temp = new ArrayList<Double>();

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                temp.add(data[i][j]);
            }
        }
        return temp;
    }

    public void changeWeight(int row, int column, double newWeight){
        // Changes the weight of a matrix given row and column data
        this.data[row][column] = newWeight;
    }

    public double getWeight(int row, int column){
        // Returns the weight of a matrix given row and column data
        return this.data[row][column];
    }

}

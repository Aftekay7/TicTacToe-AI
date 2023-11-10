package NeuralNetwork;
import Matrix.Matrix;

public class Layer {
    private Matrix weight;
    private Matrix bias; 
    private int layerPos;
    static int epoch = 1;
    static int layerCount = 0;
    

    public Layer () {
        layerCount++;
        layerPos = layerCount;
        this.weight = new Matrix(true);
        this.bias = new Matrix();


    }

    public Matrix compute(Matrix input) {
        Matrix output = new Matrix();
        // relu(input * weight + bias)
        output = input.multiply(weight);
        output = output.addMatrix(bias);
        output.relu();
        return output;
    }

    public Matrix[] trainLayer(Matrix[] inputs, Matrix[] targets) {
        
        Matrix[] outputs = new Matrix[inputs.length];
        

        for (int i = 0; i < inputs.length; i++) {
            gradientDescend2(inputs[i], targets[i]);

            double test = targets[i].calcMSE(compute(inputs[i]));
            if ( Double.isNaN(test) ) {
                System.out.println("son of a bitch");
            }
            outputs[i] = compute(inputs[i]);
        }
        //System.out.println(" \n");
        return outputs;
    }


    public void gradientDescend (Matrix input, Matrix target) {
        double stepSize = 0.01;
        double convergence = 0.001;

        boolean init;
        double mseWeights;
        double newWeight = 1;
        double WEIGHT_1;
        double WEIGHT_2;
        double WEIGHT_3;
        double INPUT_1;
        double INPUT_2;
        double INPUT_3;
        double TARGET;
        double BIAS;
        double INPUT_I;
        double deriveOf;
        double deriveTo;
        double reluBody;

        //adjusts every w_i of the weights matrix
        for (int index = 0; index < 9; index++) {
            mseWeights = 1;

            int[] indices = getCorrectIndices(input, target, index);
            INPUT_1 = input.getValue(indices[3]);
            INPUT_2 = input.getValue(indices[4]);
            INPUT_3 = input.getValue(indices[5]);
            TARGET = target.getValue(indices[6]);
            BIAS = this.bias.getValue(index);
            init = true;
            
            int iterations = 0;
            //adjusts the weights until minimum is found.
            while (mseWeights > convergence && iterations < 20) {
                
                //adjusts the 3 used weights needed for computing a specific target(m,n) individualy
                for (int i = 0; i < 3; i++) {
                    WEIGHT_1 = this.weight.getValue(indices[0]);
                    WEIGHT_2 = this.weight.getValue(indices[1]);
                    WEIGHT_3 = this.weight.getValue(indices[2]);
                    INPUT_I = input.getValue(indices[3 + i]);
                    deriveTo = this.weight.getValue(indices[0 + i]);
            
                    reluBody = INPUT_1*WEIGHT_1 + INPUT_2 * WEIGHT_2 + INPUT_3 * WEIGHT_3 + BIAS;


                    
                    //jumps the weight so the lossfunction computes a result > 0, 
                    //so we can use gradient descend (otherwise the gradient is always 0 as the relu function and its derivative is 0 for x < 0)
                    //may only be called in the first iteration.
                    
                    
                    //bias-shift
                    if (reluBody < 0 && init && epoch == 1) {
        
                        double newBias = INPUT_1*WEIGHT_1 + INPUT_2 * WEIGHT_2 + INPUT_3 * WEIGHT_3;
                        this.bias.setValue((-1)* newBias + stepSize, index);
                        BIAS = this.bias.getValue(index);
                        reluBody = INPUT_1*WEIGHT_1 + INPUT_2 * WEIGHT_2 + INPUT_3 * WEIGHT_3 + BIAS;
                        }
                    init = false;
                    
                    
                    mseWeights = 0;
                    if (reluBody < 0) { reluBody = 0;}
                    //computes the value of derivTo(w_i) ( loss(w_i) )
                    deriveOf = (-2) * INPUT_I * reluBody * (TARGET - reluBody);

                    //adjusts the weight (the one which is derived to) so loss(w_i) is nearer to the minimum
                    newWeight = (deriveTo + ((-1) * stepSize * deriveOf) );
                
                    this.weight.setValue(newWeight, indices[i]);
                    mseWeights += Math.pow(deriveTo - newWeight,2);
                    init = false;
                    }
                
                mseWeights = mseWeights/3;
                iterations++;
            }

        }
    }







    public int[] getCorrectIndices (Matrix input, Matrix target, int index) {
        int[] indices = new int[7]; //{weight1, weigh2, weigh3, input1, input2, input3, target1 }
        for (int i = 0; i < 3; i++) {
            indices[i] = (index/3) * 3 + i;
            indices[i+3] = i*3 + index%3;
        }
        indices[6] = index;
        return indices;
    }


    public void test_setLayer () {
        double[] inp = {0.699904, 0.042710, 0.605750, 0.85, 0.45, 0.74, 0.38, 0.58, 0.04};
        Matrix weight = new Matrix(inp);
        this.weight = weight;
        double[] inp2 = {0.47, 0.27, 0.85, 0.05, 0.78, 0.32, 0.74, 0.18, 0.9};
        Matrix bias = new Matrix(inp2);
        this.bias = bias;
    }


    //updates the weights individually
    public void gradientDescend2 (Matrix input, Matrix target) {
        double stepSize = 0.01;
        double convergence = 0.0001;

        boolean init;
        double error;
        double newWeight = 1;
        double WEIGHT_1;
        double WEIGHT_2;
        double WEIGHT_3;
        double INPUT_1;
        double INPUT_2;
        double INPUT_3;
        double TARGET;
        double BIAS;
        double INPUT_I;
        double deriveOf;
        double WEIGHT_I;
        double reluBody;

        //adjusts every w_i of the weights matrix
        for (int index = 0; index < 9; index++) {
            error = 1;

            int[] indices = getCorrectIndices(input, target, index);
            
            INPUT_1 = input.getValue(indices[3]);
            INPUT_2 = input.getValue(indices[4]);
            INPUT_3 = input.getValue(indices[5]);
            TARGET = target.getValue(indices[6]);
            INPUT_I = input.getValue(index);
            
            BIAS = this.bias.getValue(index);
            init = true;
            
            int iterations = 0;
            //adjusts the weights until minimum is found.
            while (error > convergence && iterations < 100) {
                WEIGHT_1 = this.weight.getValue(indices[0]);
                WEIGHT_2 = this.weight.getValue(indices[1]);
                WEIGHT_3 = this.weight.getValue(indices[2]);
                WEIGHT_I = this.weight.getValue(index);
                
                reluBody = INPUT_1*WEIGHT_1 + INPUT_2 * WEIGHT_2 + INPUT_3 * WEIGHT_3 + BIAS;
                    

                    
                    //jumps the weight so the lossfunction computes a result > 0, 
                    //so we can use gradient descend (otherwise the gradient is always 0 as the relu function and its derivative is 0 for x < 0)
                    //may only be called in the first iteration.
                    
                    //bias-shift
                    if (reluBody <= 0 && init && epoch == 1 && layerPos == 0 && BIAS < 7) {
        
                        double newBias = INPUT_1*WEIGHT_1 + INPUT_2 * WEIGHT_2 + INPUT_3 * WEIGHT_3;
                        this.bias.setValue((-1)* newBias + stepSize, index);
                        BIAS = this.bias.getValue(index);
                        reluBody = INPUT_1*WEIGHT_1 + INPUT_2 * WEIGHT_2 + INPUT_3 * WEIGHT_3 + BIAS;
                        }
                    init = false;
                    
                    
                    
                    error = 0;
                    if (reluBody < 0) { reluBody = 0;}

                    //computes the value of derivTo(w_i) ( loss(w_i) )
                    deriveOf = (-2) * INPUT_I * reluBody * (TARGET - reluBody);

                    //adjusts the weight (the one which is derived to) so loss(w_i) is nearer to the minimum
                    newWeight = WEIGHT_I + ((-1) * stepSize * deriveOf);
                
                    this.weight.setValue(newWeight, index);
                    error = Math.pow(WEIGHT_I - newWeight,2);

                    //init = false;
                    iterations++;
            }   
        }
    }


}

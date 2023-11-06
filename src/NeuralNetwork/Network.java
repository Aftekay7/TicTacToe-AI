package NeuralNetwork;
import Matrix.Matrix;

public class Network {
    public Layer[] layers;

    public Network(int layerCount) {
        this.layers = new Layer[layerCount];
        for (int i =0; i < layerCount; i++) {
            layers[i] = new Layer();
        }
    }

    public Matrix predict(Matrix input){
        Matrix input_conv = input.copy();//layer0 (input);
        for(Layer layer : layers) {
            input_conv = layer.compute(input_conv);
        }
        return input_conv;
    }

    public double[] train(Matrix[] inputs, Matrix[] targets, int epoches) {
        Matrix[] output = new Matrix[inputs.length];
        double[] msePerEpoch = new double[epoches];
        for (int epoch = 0; epoch < epoches; epoch++) {

            //apply layer0 to all inputs
            for (int i = 0; i < inputs.length; i++) {
                output[i] = layer0(inputs[i]);
            }

            //either layer0 or this line
            //output = inputs.clone();

            for (Layer layer : layers) {
                output = layer.trainLayer(output, targets);

            }
            msePerEpoch[epoch] = loss(output, targets);
            System.out.println("MSE of Epoch " + epoch + ": " + msePerEpoch[epoch]);
            Layer.epoch++;
        }
        return msePerEpoch;
    }

    public double loss (Matrix[] output, Matrix[] target ) {
        double mse = 0;
        for (int i = 0; i < output.length; i++) {
            mse += target[i].calcMSE(output[i]);
        }
        return mse/output.length;
    }


    public double test (Matrix[] inputs, Matrix[] targets) {
        Matrix[] outputs = new Matrix[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            outputs[i] = predict(inputs[i]);
        }

        return loss(outputs,targets);
        

        
    }

    //converts input to a better suitable 
    public Matrix layer0 (Matrix input) {
        Matrix input_conv = new Matrix();
        int index;
        double comp;

        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                index = 3*m + n;
                comp = input.getValue(index);
                if (comp == -1) {
                    input_conv.setValue(1, index);
                } else if (comp == 1) {
                    input_conv.setValue(2, index);
                } else {
                    input_conv.setValue(3, index);
                }
            }
        }

        return input_conv;
    }

    
}

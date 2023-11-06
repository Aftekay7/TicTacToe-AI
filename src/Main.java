

import java.util.Scanner;

import Data.DataGenerator;
import Data.Datapackage;
import Game.TicTacToe;
import NeuralNetwork.Network;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    private static int trainingGames = 5000; //Number of Played Games;
    private static int layerCount = 4; //depth of the network/number of layers.
    private static int epochCount = 100; //number of epoches the whole network is trained.

    public static void main(String[] args) {
        
        String input = "";

        //generate and prepare the data
        DataGenerator generator = new DataGenerator();
        Datapackage datapackage = generator.farmData(trainingGames);
        //datapackage.testNAN();
        datapackage.prepareData();
        datapackage.verifyData();
        
        /*
        System.out.println("Data prepared, enter the layercount!");
        input = scanner.nextLine();
        */
        //initilize the network
        Network network = new Network(layerCount/*Integer.parseInt(input)*/);
        //network.layers[0].test_setLayer();

        /*
        System.out.println("press enter to train for one epoch");
        input = scanner.nextLine();

        */

        System.out.println("training...");
        network.train(datapackage.trainingset_inputs, datapackage.trainingset_targets, epochCount);

        while (!input.equalsIgnoreCase("skip")) {
            System.out.println("enter train to train for one more epoch, enter test if you want to test the network '\n' or play to play a game against the AI");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("train")) {
                System.out.println("training...");
                network.train(datapackage.trainingset_inputs, datapackage.trainingset_targets, epochCount);
            } else if (input.equalsIgnoreCase("test")) {
                System.out.println("testing...");
                double mse = network.test(datapackage.testset_inputs, datapackage.testset_targets);

                System.out.println("The MSE of the testset is: " + mse);
            } else if (input.equalsIgnoreCase("game")) {
                TicTacToe game = new TicTacToe(network);
                game.startGame(scanner);
            }

            if (input.equalsIgnoreCase("skip")) {
                System.out.println("you really want to quit? reenter 'skip' to quit, press enter to continue");
                input = scanner.nextLine();
            }
        }
       
        scanner.close();
    }
}
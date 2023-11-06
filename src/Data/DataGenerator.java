package Data;

import java.util.concurrent.ThreadLocalRandom;

import Matrix.Matrix;
import Util.GameTree;
import Util.RandomGenerator;

import java.util.LinkedList;
import java.util.Scanner;

public class DataGenerator {

    public Datapackage farmData(int iterations) {
        Datapackage datapackage = new Datapackage();
        //GameTree gameTreePosFirst = new GameTree();
        //GameTree gameTreePosSecond = new GameTree();
        //Scanner scanner = new Scanner(System.in);


        for (int i = 0; i < iterations; i++) {
            Matrix[] moveList = generateRandomGame(i);
            
            int eval = evaluate(moveList);
            if (eval > 0) {
                //gameTreePosSecond.sortIn(moveList);
                datapackage.addMoveList(moveList);
            } else if (eval == 0) {
                //gameTreePosSecond.sortIn(moveList);
                datapackage.addMoveList(moveList);

                Matrix.convertToPosFirst(moveList);

                //gameTreePosFirst.sortIn(moveList);
                datapackage.addMoveList(moveList);
            } else {
                Matrix.convertToPosFirst(moveList);
                //gameTreePosFirst.sortIn(moveList);
                datapackage.addMoveList(moveList);
            }
            
            //System.out.println("continue? // press Enter");
            //scanner.nextLine();


            
        }
        return datapackage;

    }


    public Matrix[] generateRandomGame (int seed) {
        //ThreadLocalRandom random = ThreadLocalRandom.current();
        int nr = 0;

        Matrix[] pastGame = new Matrix[9];
        Matrix game = new Matrix();
        int spacesLeft = 9;
        for (int i = 0; i < 9; i++) {
            int[] indexes = game.findEmptyIndexes();
            //nr = random.nextInt(0,spacesLeft); //truly (lol) random
            nr = RandomGenerator.random(seed, spacesLeft); //with specific seed for debugging
            
            //-1's turn
            if (spacesLeft%2 == 1) {
                if (spacesLeft < 7) {
                    int win = game.possibleEnd(1); //which turn is already defined
                    int block = game.possibleEnd(2);
                    if (win != 9) {
                        game.SetIndexNeg(win);
                    } else if (block != 9){
                        game.SetIndexNeg(block);
                    } else {game.SetIndexNeg(indexes[nr]);} 

                } else { game.SetIndexNeg(indexes[nr]); }
                
                pastGame[i] = game.copy();
                //game.print();

            //1's turn
            } else {
                if (spacesLeft < 7) {
                    int win = game.possibleEnd(2); //which turn is already defined
                    int block = game.possibleEnd(1);
                if (win != 9) {
                    game.SetIndexPos(win);
                } else if (block != 9){
                    game.SetIndexPos(block);
                } else {game.SetIndexPos(indexes[nr]);}

                } else { game.SetIndexPos(indexes[nr]); }

                pastGame[i] = game.copy();
                //game.print();
            }
            spacesLeft--;



            if (i > 4 && i < 9 && game.isGameWon()) {
                return pastGame;
            }
        }
        return pastGame;
    }


    public int evaluate(Matrix[] moveList) {
        int i = moveList.length - 1;
        while (i >= 0 && moveList[i] == null) {
            i--;
        }
        
        Matrix lastMove = moveList[i];

        if (lastMove.isGameWon()) {
            if (i%2 == 1) {return 1;}
            if (i%2 == 0) {return -1;}
        }
        return 0;
    }



    
}

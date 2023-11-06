package Data;
import Matrix.Matrix;
import Util.GameTree;
import Util.RandomGenerator;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;


public class Datapackage {
    LinkedList <Matrix> inputs = new LinkedList<Matrix>();
    LinkedList <Matrix> targets = new LinkedList<Matrix>();

    public Matrix[] trainingset_inputs;
    public Matrix[] trainingset_targets;

    public Matrix[] testset_inputs;
    public Matrix[] testset_targets;



    public void addMoveList (Matrix[] moveList) {
        int i = 0;
        if(moveList[i].PosStarts()) {
            inputs.addLast(new Matrix());
            targets.addLast(moveList[i+1]);
            i = 2;

            while (i < moveList.length && moveList[i] != null) {
                inputs.addLast(moveList[i - 1]);
                moveList[i].movePlayed(moveList[i-1]);
                targets.addLast(moveList[i]);

                i+= 2;
                }
        } else {
            i = 1;
            while ( i < 8 && moveList[i] != null) {
            inputs.addLast(moveList[i-1]);
            moveList[i].movePlayed(moveList[i-1]);
            targets.addLast(moveList[i]);
            i+= 2;
            }
        }
    }

    public void prepareData () {
        double y = inputs.size() * 0.8;

        int setLength = (int) Math.floor(y);
        trainingset_inputs = new Matrix[setLength];
        trainingset_targets = new Matrix[setLength];
        testset_inputs = new Matrix[inputs.size() - setLength];
        testset_targets = new Matrix[inputs.size() - setLength];

        ThreadLocalRandom random = ThreadLocalRandom.current();
        int index;
        for (int i = 0; i < setLength; i++) {
            index = random.nextInt(0,inputs.size());
            //index = RandomGenerator.random(0, inputs.size()); //debugging, 
            trainingset_inputs[i] = inputs.remove(index);
            trainingset_targets[i] = targets.remove(index);
        }

        for (int i = 0; i < testset_inputs.length; i++) {
            testset_inputs[i] = inputs.pop();
            testset_targets[i] = targets.pop();
        }


    }

    public void verifyData() {
        boolean test;
        for (int i = 0; i < testset_inputs.length; i++) {
            test = testset_inputs[i].checkForNaN();
            if(test) {
                System.out.println("in testset inputs is a NaN");
            }
        }

        for (int i = 0; i < testset_targets.length; i++) {
            test = testset_inputs[i].checkForNaN();
            if(test) {
                System.out.println("in testset targets is a NaN");
            }
        }

        System.out.println("no NAN found");

    }

    public void testNAN () {
        Matrix matrix = new Matrix();
        System.out.println("is is 0er matrix nan ?"  + matrix.checkForNaN());
        matrix.testSetNAN();
        System.out.println("matrix with NAN has NAN?" + matrix.checkForNaN());
        Matrix[] testMoveList = {matrix, matrix.copy(), matrix.copy(), matrix.copy(), matrix.copy(), 
                                null,null,null,null};
        addMoveList(testMoveList);
    }



}

package Game;

import java.util.Scanner;

import Matrix.Matrix;
import NeuralNetwork.Network;

public class TicTacToe {
    private boolean AIisFirst = false;
    private Matrix gameField = new Matrix();
    private boolean AIisOnTurn = false;
    private Network network;
    private boolean gameOver = false;

    public TicTacToe (Network AI) {
        this.network = AI;
    }


    public void startGame (Scanner scanner) {
        String input = "";
        this.gameField = new Matrix();
        AIisFirst = false;
        AIisOnTurn = false;
        if (AIisFirst) {
            AIisOnTurn = true;
            System.out.println("AI starts the game");
        } else {
            System.out.println("You start the game");
            AIisOnTurn = false;
        }
        int counter = 0;
        while (counter < 9 && !gameOver) {
            if (AIisOnTurn) {
                Matrix AIMove = network.predict(gameField);
                int[] AIMoveOrder = AIMove.extractMoveOrder();

                for (int move : AIMoveOrder) {
                    if (gameField.isFree(move)) {
                        if (AIisFirst) {
                        gameField.SetIndexNeg(move);
                        } else {
                            gameField.SetIndexPos(move);
                        }
                        break;
                    }
                }
                
                AIisOnTurn = !AIisOnTurn;

            } else {
                gameField.print();
                System.out.println("enter your move");
                input = scanner.nextLine();
                if (AIisFirst) {
                    gameField.SetIndexPos(Integer.parseInt(input));
                } else {
                    gameField.SetIndexNeg(Integer.parseInt(input));
                }
                gameField.print();
                AIisOnTurn = !AIisOnTurn;
                System.out.println("AI is on turn");
                
            }
            gameOver = gameField.isGameWon();
            counter++;
        }
        if (counter == 9 && !gameOver) {
            System.out.println("Its a draw!");
        } else {
            if (!AIisOnTurn) {
                gameField.print();
                System.out.println("The AI won!");

            } else {
                System.out.println("You won!");
            }
        }
    }
}

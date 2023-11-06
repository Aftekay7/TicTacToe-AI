package Matrix;
import java.util.Random;
import Util.RandomGenerator;



public class Matrix {
    private double[][] matrix;
    private static Random random = new Random();

    public Matrix (double [] input) {
        this.matrix = new double[3][3];
        int i = 0;
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                matrix[m][n] = input[i];
                i++;
            }
        }
    }

    public Matrix (int index) {
        this.matrix = new double[3][3];
        SetIndexPos(index);
    }

    public Matrix () {
        
        this.matrix = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.matrix[i][j] = 0;
                }
            }
        }

    public Matrix (boolean filledRandomPositiv) {
        this.matrix = new double[3][3];
        
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                this.matrix[m][n] = random.nextDouble() + 0.01;
                if (!filledRandomPositiv) {
                    matrix[m][n] = matrix[m][n]*(-1);
                    }
                }
            }
        }

    public static Matrix constructWeight () {
        Matrix matrix = new Matrix();
        int signed = 0;
        double newParam = 0;
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                newParam = random.nextDouble();
                signed = (int) newParam*100;
                if (signed % 2 == 1) {
                    signed = -1;
                } else {
                    signed = 1;
                }
                matrix.matrix[m][n] = newParam * signed;
                
            }
        }
        return matrix;
    }

    public Matrix multiply(Matrix input) {
        Matrix newMatrix = new Matrix();
        double param = 0;
        
        for (int m = 0; m < input.matrix.length; m++) {
            for (int n = 0; n < input.matrix[0].length; n++) {

                param = 0;
                for (int i = 0; i < input.matrix.length; i++) {
                    param += this.matrix[m][i] * input.matrix[i][n];
                }

                newMatrix.matrix[m][n] = param;
            }
            
        }
        return newMatrix;
    }

    public Matrix addMatrix (Matrix input) {
        Matrix output = new Matrix();
        
        for (int m = 0; m < input.matrix.length; m++) {
            for (int n = 0; n < input.matrix[0].length; n++) {

                output.matrix[m][n] = this.matrix[m][n] + input.matrix[m][n];
            }   
        }
        return output;
    }

    

    public void relu() {
        for (int m = 0; m < this.matrix.length; m++) {
            for (int n = 0; n < this.matrix[0].length; n++) {

                if (this.matrix[m][n] < 0) {
                    this.matrix[m][n] = 0;
                } 
            }   
        } 
    }

    public Matrix shiftClockWise(){
        Matrix output = new Matrix();
        
        for (int m = 0; m < this.matrix.length; m++) {
            int i = 2;
            for (int n = 0; n < this.matrix[0].length; n++) {
                output.matrix[m][n] = this.matrix[i][m];
                i--;
            }   
        }
        return output;
    }

    public void SetIndexPos(int i) {
        int m = i/3;
        int n = i%3;
        this.matrix[m][n] = 1;
    }
    public void SetIndexNeg(int i) {
        int m = i/3;
        int n = i%3;
        this.matrix[m][n] = -1;
    }

    public boolean isFree(int i) {
        if (i > 8) {return false;}
        int m = i/3;
        int n = i%3;
        return matrix[m][n] == 0;
    }

    public int[] findEmptyIndexes() {
        int[] indexes = new int[9];
        int counter = 0;

        for (int i = 0;i<3; i++ ) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == 0) {
                    indexes[counter] = (i*3)+ j ;
                    counter++;
                }
            }

        }
        
        if (counter > 0) {
            int[] cropped = new int[counter];
            for (int i = 0; i < counter; i++) {
            cropped[i] = indexes[i];
                }
                return cropped;
            }
        return null;

    }

    public void print() {
        String output = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                output += this.matrix[i][j] + "\t";

            }
            output += '\n';
        }
        System.out.println(output);
    }
    
    public Matrix copy() {
        Matrix copy = new Matrix();
        copy = this.addMatrix(copy);
        return copy;
    }

    public String export() {
        String converted = "{";
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                converted += this.matrix[i][j] + ",";

            }
        }
        converted += "}";

        return converted;
    }

    public int possibleEnd (int spacesLeft) {
        int counterOnTurn = 0;
        int counterOFFTurn = 0;
        int index = 9;
        boolean turn = spacesLeft%2 == 1; //true == -1 plays (9 spaces left -> 9%2 = 1 -> -1's turn.)
        //goes through rows
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(matrix[i][j] > 0) {
                    counterOnTurn++;
                } else if (matrix[i][j] == 0) {
                    index = i*3 + j;
                } else {
                counterOFFTurn++;
                }
            }
            if (isFree(index)){
                if (counterOnTurn == 2 && !turn ) {
                    return index;
                } else if (counterOFFTurn == 2 && turn ) {
                    return index;
                }
            }   
            counterOFFTurn = 0;
            counterOnTurn = 0;
            index = 9;
        }

        

        //Goes through columns
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(matrix[j][i] > 0) {
                    counterOnTurn++;
                } else if (matrix[j][i] == 0 /*|| isOccupiedBy(i*3+j, turn)*/) {
                    index = j*3 + i;
                } else {
                counterOFFTurn++;
                }
            }
            if (isFree(index)){
                if (counterOnTurn == 2 && !turn ) {
                    return index;
                } else if (counterOFFTurn == 2 && turn ) {
                    return index;
                }
            }
            counterOFFTurn = 0;
            counterOnTurn = 0;
            index = 9;
        }

        

        //checks the crosses
        for (int i = 0; i < 3; i++) {
                if(matrix[i][i] > 0) {
                    counterOnTurn++;
                } else if (matrix[i][i] == 0) {
                    index = i*3 + i;
                }else {
                counterOFFTurn++;
                }
        }
        if (isFree(index)){
            if (counterOnTurn == 2 && !turn ) {
                return index;
            } else if (counterOFFTurn == 2 && turn ) {
                return index;
            }
        }
        counterOFFTurn = 0;
        counterOnTurn = 0;
        index = 9;

        

        for (int i = 0; i < 3; i++) {
                if(matrix[i][2-i] > 0) {
                    counterOnTurn++;
                } else if (matrix[i][2-i] == 0) {
                    index = i*3 + (2-i);
                } else { counterOFFTurn++;}
        
            }
        if (isFree(index)){
            if (counterOnTurn == 2 && !turn ) {
                return index;
            } else if (counterOFFTurn == 2 && turn ) {
                return index;
            }
        }
        return 9;
    }

    public boolean isOccupiedBy(int index, boolean turn) {
        int m = index/3;
        int n = index%3;
        if (turn && matrix[m][n] == -1) {
            return true;
        } else if (!turn && matrix[m][n] == 1) {
            return true;
        }
        return false;
    }
    

    public boolean isEqual(Matrix comp) {
        if (comp == null) {
            return false;
        }
        for (int i = 0;i<3; i++ ) {
            for (int j = 0; j < 3; j++) {
                if (this.matrix[i][j] != comp.matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void movePlayed(Matrix comp) {
        for (int i = 0;i<3; i++ ) {
            for (int j = 0; j < 3; j++) {
                if (this.matrix[i][j] != comp.matrix[i][j]) {
                    continue;
                }
                this.matrix[i][j] = 0;
            }
        }
    }

    public boolean PosStarts(){
        for (int i = 0;i<3; i++ ) {
            for (int j = 0; j < 3; j++) {
                if (this.matrix[i][j] == 1) {return true;}
                }
            }
        return false;
    }

    public boolean isGameWon () {
        int player1wins= 0;
        int player2wins = 0;
        //goes through rows
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(matrix[i][j] > 0) {
                    player1wins++;
                } else if (matrix[i][j] == 0) {
                    break;
                } else {
                player2wins++;
                }
            }
            if (player1wins== 3 || player1wins == 3) {return true;}
            player1wins = 0;
            player2wins = 0;
        }

        

        //Goes through columns
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(matrix[j][i] > 0) {
                    player1wins++;
                } else if (matrix[j][i] == 0) {
                    break;
                } else {
                player2wins++;
                }
            }
            if (player1wins == 3 || player2wins ==3) {
                return true;}
            player1wins = 0;
            player2wins = 0;
        }

        

        //checks the crosses
        for (int i = 0; i < 3; i++) {
            if(matrix[i][i] > 0) {
                player1wins++;
            } else if (matrix[i][i] == 0) {
                break;
            }else { player2wins++; }
        
        }
        if (player1wins == 3 || player2wins == 3) {return true;}
        player1wins = 0;
        player2wins = 0;

        

        for (int i = 0; i < 3; i++) {
            if(matrix[i][2-i] > 0) {
                player1wins++;
            } else if (matrix[i][2-i] == 0) {
                break;
            } else { player2wins++; }
        }
        if (player1wins == 3 || player2wins == 3) {return true;}
        
        
        return false;
    }

    public static void convertToPosFirst(Matrix[] moveList) {
        int i = 0;
        while (i < moveList.length && moveList[i] != null) {
            for (int m = 0; m < 3; m++) {
                for (int n = 0; n < 3; n++) {
                    if (moveList[i].matrix[m][n] == 0) {
                        moveList[i].matrix[m][n] = moveList[i].matrix[m][n] * (-1);
                    }
                }
            }
            i++;
            
        }
    }

    public double calcMSE(Matrix input) {
        double mse = 0;
        
        for (int m = 0; m < input.matrix.length; m++) {
            for (int n = 0; n < input.matrix[0].length; n++) {

                mse += Math.pow(this.matrix[m][n] - input.matrix[m][n],2);
            }   
        }
        return mse/9;
    }

    public double getValue(int index) {
        if (index < 0 || index >9) {
            throw new IllegalArgumentException("not a valid index");
        }
        return matrix[index/3][index%3];
    }

    public void setValue (double value, int index) {
        if (index < 0 || index >9) {
            throw new IllegalArgumentException("not a valid index");
        }
        matrix[index/3][index%3] = value;
    }

    public boolean checkForNaN() {
        
        for (int m = 0; m < this.matrix.length; m++) {
            for (int n = 0; n < this.matrix[0].length; n++) {
                if (Double.isNaN(this.matrix[m][n])) {
                    return true;
                }
            }   
        }
        return false;
    }


    public void testSetNAN () {
        this.matrix[0][0] = Double.NaN;
    }

    public int[] extractMoveOrder () {
        int[] moveOrder = new int[9];
        int move;

        for (int i = 0; i < 9; i++) {
            move = 0; 
            for (int m = 0; m < 3; m++) {
                for (int n = 0; n < 3; n++) {
                    if (matrix[m][n] > move) {
                        move = m*3+n;
                        matrix[m][n] = 0;
                    }
                }
            }
        moveOrder[i] = move;
        }
        return moveOrder;
    }
}

package Util;
import java.util.Random;

public class RandomGenerator {
    
    public static int random(int seed, int spacesLeft) {
     
        Random random = new Random(seed);

        int min = 0; // Minimum value of the range
        int max = spacesLeft - 1;

        // Generate a random integer between min and max (inclusive)
        int randomInt = random.nextInt(max - min + 1) + min;

        return randomInt;
    }
}

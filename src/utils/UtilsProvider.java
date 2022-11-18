package utils;

public class UtilsProvider {
    public static int getPowerOfTwoBiggerThan(int value) {
        int ind = 0;
        double powerOfTwo = 0;

        while(powerOfTwo < value) powerOfTwo = Math.pow(2, ind++);
        return (int) powerOfTwo;
    }

    public static int getFirstPrimeLowerThan(int value) {
        int parsedValue = Math.abs(value);
        int findedPrime = 0;

        for(int ind=parsedValue ; ind > 0 ; ind--) {
            if(isPrime(ind)) {
                findedPrime = ind;
                break;
            }
        }

        return findedPrime;
    }

    private static boolean isPrime(int value) {
        int parsedValue = Math.abs(value);

        if(parsedValue <= 2) return true;
        if(value % 2 == 0) return false;

        boolean isPrime = true;
        for(int ind=2 ; ind<parsedValue ; ind++) {
            if(parsedValue % ind == 0) {
                isPrime = false;
                break;
            }
        }

        return isPrime;
    }
}

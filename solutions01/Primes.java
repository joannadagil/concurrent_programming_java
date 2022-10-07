package solutions01;

public class Primes {

    private static final int END = 10000;
    private static final int NONE = 0;

    private static volatile int foundDivisor;

    public static boolean isPrime(int n) {
        int sqrt = (int) Math.sqrt(n);
        for (int p : new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 }) {
            if (p > sqrt) {
                return true;
            }
            if (n % p == 0) {
                return false;
            }
        }
        int[] starts = { 31, 37, 41, 43, 47, 49, 53, 59 };
        // FIXME: implement here and remove the line below

        foundDivisor = 0;

        Thread[] threads = new Thread[starts.length];

        for(int i = 0; i < starts.length; i++) {
            threads[i] = new Thread(new Helper(starts[i]));
            threads[i].start();
        }

        // żeby wątki się  skończyły
        for(int i = 0; i < starts.length; i++) {
            while(threads[i].isAlive()) {
                // empty
            }
        }

        if(foundDivisor > 0) { return false; }
        else { return true; }
    }

    private static class Helper implements Runnable {

        int value;

        public Helper(int v) {
            this.value = v;
        }

        @Override
        public void run() {

        }
    }

    // FIXME: adding a class for helper threads may be convenient
    
    public static void main(String[] args) {
        int primesCount = 0;
        for (int i = 2; i <= END; ++i) {
            if (isPrime(i)) {
                ++primesCount;
            }
        }
        System.out.println(primesCount);
        assert(primesCount == 1229);
    }

}

package solutions01;

public class Primes {

    private static final int END = 10000;
    private static final int NONE = 0;

    private static final int DIFF = 30;

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
            threads[i] = new Thread(new Helper(n, starts[i]));
            threads[i].start();
        }

        // żeby wątki się  skończyły
        for(int i = 0; i < starts.length; i++) {
            while(threads[i].isAlive()) {
                // empty
            }
        }

        return foundDivisor == 0;
    }

    private static class Helper implements Runnable {

        int n, c;

        public Helper(int n, int c) {
            this.n = n;
            this.c = c;
        }

        @Override
        public void run() {
            if (foundDivisor == 0) {
                if (n % c == 0) {
                    foundDivisor = 1;
                } else {
                    c = c + DIFF;
                    if (c < n)
                        this.run();
                }
            }
        }
    }

    // FIXME: adding a class for helper threads may be convenient
    
    public static void main(String[] args) {

        System.out.println("aaaaa");

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

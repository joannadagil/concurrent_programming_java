package przyklady02;


import java.util.Random;

public class Vector {

    int[] arr;


    public Vector(int[] arr) {
        this.arr = arr;
    }

    public Vector sum (Vector other) {
        int length = this.arr.length;
        int no_threads = (length + 9) / 10;
        //int results[] = new int[no_threads];
        Thread[] threads = new Thread[no_threads];
        Vector result = new Vector(new int[length]);
        for (int i = 0; i < no_threads - 1; i++) {
            threads[i] = new Thread(new SumHelper(10 * i, 10 * (i + 1), result, this, other));
            // 0-10   10-20 (liczymy 10-19)
            threads[i].start();
        }
        threads[no_threads - 1] = new Thread(new SumHelper(10 * (no_threads - 1), length, result, this, other));
        threads[no_threads - 1].start(); // 20-21 (jeden - 20-ty)

        try {
            for (Thread t : threads)
                t.join();
        } catch (InterruptedException e) {
            System.out.println("interruption");
        }

        return other;
    }

    public int dot (Vector other) {
        return 0;
    }

    private static class DotHelper implements Runnable {

        int begin;
        int end;

        public DotHelper(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public void run() {

        }

    }

    private static class SumHelper implements Runnable {
        int begin;
        int end;
        Vector result;
        Vector a;
        Vector b;

        public SumHelper(int begin, int end, Vector result, Vector a, Vector b) {
            this.begin = begin;
            this.end = end;
            this.result = result;
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            for (int i = begin; i < end; i++) {
                result.arr[i] = a.arr[i] + b.arr[i];
            }
        }

    }

    public static void main(String[] args) {
        Random rn = new Random();

        int[] arr = new int[8];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rn.nextInt(10);
        }
        Vector a = new Vector(arr);
        for(int e : a.arr)
            System.out.print(e + " ");
        System.out.println();

        Vector res = a.sum(a);

        //System.out.println(res.arr);

        for(int e : res.arr)
            System.out.print(e + " ");
        System.out.println();

    }
}

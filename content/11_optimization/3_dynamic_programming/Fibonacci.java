public class Fibonacci {

    long withDynamicProgramming(long n) {
        long[] cache = new long[(int) (n+1)];
        cache[0] = 0;
        cache[1] = 1;
        for(int i=2 ; i<=n; i++) {
            cache[i] = cache[i-1] + cache[i-2];
        }
        return cache[(int) n];
    }



    long withMemoization(long n, long[] cache) {
        var n1 = cache[(int) n-1];
        if (n1 == -1L) {
            n1 = withMemoization(n-1, cache);
            cache[(int) n-1] = n1;
        }
        var n2 = cache[(int) n-2];
        if (n2 == -1L) {
            n2 = withMemoization(n-2, cache);
            cache[(int) n-2] = n2;
        }
        return n1 + n2;
    }

    long recursive(long n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return recursive(n-1) + recursive(n-2);
    }


    public static void main(String args[]) {
        var n = 50L;

        var fibonacci = new Fibonacci();
        long start, end;
        long result;

        start = System.nanoTime();
        result = fibonacci.recursive(n);
        end = System.nanoTime();
        System.out.println("With recursion F("+n+") = " + result);
        System.out.println("Time = " + (end - start) / 1000000 + " ms");

        var cache = new long[(int) (n+1)];
        cache[0] = 0L;
        cache[1] = 1L;
        for (int i=2 ; i<=n ; i++) cache[i] = -1L;
        start = System.nanoTime();
        result = fibonacci.withMemoization(n, cache);
        end = System.nanoTime();
        System.out.println("With memorization F("+n+") = " + result);
        System.out.println("Time = " + (end - start) + " ns");

        start = System.nanoTime();
        result = fibonacci.withDynamicProgramming(n);
        end = System.nanoTime();
        System.out.println("With dynamic programming F("+n+") = " + result);
        System.out.println("Time = " + (end - start) / 1000000 + " ns");
    }

}

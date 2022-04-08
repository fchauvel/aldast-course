public class Recursion {


    public static int iterativeFactorial(int value) {
        int product = 1;
        for (int each = 1 ; each <= value ; each++) {
            product = product *  each;
        }
        return product;
    }

    public static int recursiveFactorial(int value) {
        if (value == 1) {
            return 1;
        } else {
            return value * recursiveFactorial(value-1);
        }
    }

    public static int tailCallFactorial(int value) {
        return doRecursion(value, 1);
    }

    public static int doRecursion(int value, int accumulator) {
        if (value == 1) {
            return accumulator * 1;
        }
        return doRecursion(value-1, accumulator*value);
    }

    // return value^exponent
    public static long iterativePower(int value, int exponent) {
        if (exponent == 0) return 1;
        long result = value;
        for (int index=1 ; index < exponent ; index++) {
            result = result * value;
        }
        return result;
    }

    public static long recursivePower(int value, int exponent) {
        if (exponent == 0) return 1;
        return value * recursivePower(value, exponent-1);
    }

    //
    public static int iterativeSum(int[] array) {
        int total = 0;
        for (int index = 0 ; index < array.length ; index++) {
            total += array[index];
        }
        return total;
    }

    public static int recursiveSum(int[] array) {
        return doRecursiveSum(array, 0);
    }


    public static int doRecursiveSum(int[] array, int start) {
        if (start == array.length) {
            return 0;
        } else {
            return array[start] + doRecursiveSum(array, start+1);
        }
    }


    public static int iterativeMaximum(int[] array) {
        int maximum = array[0];
        for (int index=1 ; index<array.length ; index++) {
            if (array[index] > maximum) {
                maximum = array[index];
            }
        }
        return maximum;
    }


    public static int recursiveMaximum(int[] array) {
        return doMaximum(array, 0);
    }

    public static int doMaximum(int[] array, int start) {
        if (array.length-start <= 1) {
            return array[start];
        }
        int maxOfTail = doMaximum(array, start+1);
        if (array[start] < maxOfTail) {
            return maxOfTail;
        }
        return array[start];
    }


    public static void main(String args[]) {
        System.out.println("1! = " + tailCallFactorial(1));
        System.out.println("2! = " + tailCallFactorial(2));
        System.out.println("5! = " + tailCallFactorial(5));

    }

}

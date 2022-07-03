
import java.lang.Math;


public class Main {

    static void show(int[] array) {
        System.out.print("[");
        for (int i=0 ; i<array.length ; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println("]");
    }

    static int[] countingSort(int[] array, int position) {
        int[] frequencies = new int[10];
        int[] sorted = new int[array.length];

        for (int i=0 ; i<array.length ; i++) {
            int digitAtPosition = digitAt(array[i], position);
            frequencies[digitAtPosition] += 1;
        }

        for (int i=1 ; i<10 ; i++) {
            frequencies[i] += frequencies[i-1];
        }

        for  (int i=array.length-1 ; i>=0 ; i--) {
            int digit = digitAt(array[i], position);
            sorted[frequencies[digit]-1] = array[i];
            frequencies[digit] -= 1;
        }

        return sorted;
    }

    static int findMaximum(int[] array) {
        if (array.length == 1) return array[0];
        int maximum = array[0];
        for(int any_index=0 ; any_index<array.length ; any_index++) {
            if (array[any_index] > maximum) {
                maximum = array[any_index];
            }
        }
        return maximum;
    }

    static int countDigits(int value) {
        int exponent = 1;
        int threshold = 10;
        while (value >= threshold) {
            threshold *= 10;
            exponent += 1;
        }
        return exponent;
    }

    static int digitAt(int number, int position) {
      int divisor = (int) Math.pow(10, position);
      return (number / divisor) % 10;
    }

    static int[] radixSort(int[] array) {
        int[] sorted = array;;
        int maximum = findMaximum(array);
        int digitCount = countDigits(maximum);
        for(int digit=0 ; digit<digitCount ; digit++) {
            sorted = countingSort(sorted, digit);
        }
        return sorted;
    }

    public static void main(String args[]) {
      int[] array = {135, 246, 19, 74, 753, 82, 975, 258};
      int[] sorted = radixSort(array);
      show(sorted);
    }

}

public class InsertionSort2 {

    public static void sort(int[] array) {
        int current = 1;
        while (current < array.length) {
            int selectedPosition = findInsertionPosition(array, current);
            insert(array, selectedPosition, current);
            current = current + 1;
        }
    }

    private static int findInsertionPosition(int[] array, int limit) {
        int position = 0;
        while (position < limit){
            if (array[position] > array[limit]) {
                return position;
            }
            position = position + 1;
        }
        return limit;
    }

    private static void insert(int[] array, int selectedPosition, int limit) {
        int itemToInsert = array[limit];
        int index = limit;
        while (index > selectedPosition) {
            array[index] = array[index-1];
            index = index - 1;
        }
        array[selectedPosition] = itemToInsert;
    }

    private static void show(int[] array) {
        for (int index=0 ; index<array.length ; index++) {
            System.out.print(array[index] + " ");
        }
        System.out.println("");
    }

    public static void main(String args[]) {
        int[] array = {3, 4, 6, 6, 7, 2, 1, 9};
        show(array);
        sort(array);
        show(array);
    }

}

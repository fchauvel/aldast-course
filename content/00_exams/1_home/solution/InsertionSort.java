wpublic class InsertionSort {


    public static void sort(int[] array) {
        for (int current=1 ; current<array.length ; current++) {
            int itemToInsert = array[current];

            // We select where to insert the current item
            int selectedPosition = current;
            for (int position=0 ; position < current ; position++){
                if (array[position] > itemToInsert) {
                    selectedPosition = position;
                    break;
                }
            }

            // We insert the current element at the selected position
            for (int index=current; index>selectedPosition; index--) {
                array[index] = array[index-1];
            }
            array[selectedPosition] = itemToInsert;
        }
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

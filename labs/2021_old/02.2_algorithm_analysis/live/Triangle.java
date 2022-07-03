public class Triangle {

    public static void printRow(int row)
    {                               // Cost    Freq    Total cost
        int column = 0;             //    1       1        1
        while (column <= row) {     //    1   row+2        row+2
            System.out.print("* "); //    1   row+1        row+1
            column = column + 1;    //    2   row+1        2row + 2
        }
        System.out.println();       //    1       1        1
    }                               //                  + ------------
                                    //                     4row + 7

    public static void printTriangle(int height)
    {                                   // Cost   Freq       Total
        int row = 0;                    // 1      1             1
        while (row < height) {          // 1      height+1    h+1
            printRow(row);              // 4r+7   height      4*0 +
            row = row + 1;              // 2      height      2h
        }
    }                                   // 1 + h+1 + sum + 2h

    public static void main(String[] args) {
        printTriangle(6);
    }
}

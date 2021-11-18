public class Triangle {
    public static void printTriangle(int height) {
        for(int row=0 ; row<height ; row++){  
            for(int column=0 ; column<=row ; column++){  
                System.out.print("* ");  
            }  
            System.out.println();  
        } 
    }

    public static void main(String[] args) {  
        printTriangle(6);
    }  
}  
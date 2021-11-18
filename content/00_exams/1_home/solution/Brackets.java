public class Brackets {


    static int checkParentheses(char[] expression) {
        int bracketToClose = 0;
        int groupStart = -1;
        for(int index=0 ; index<expression.length ; index++) {
            if (expression[index] == '(') {
                bracketToClose += 1;
                if (bracketToClose == 1) {
                    groupStart = index;
                }
            } else if (expression[index] == ')') {
                bracketToClose -= 1;

            } else {}
            if (bracketToClose < 0) { return index; }
        }
        if (bracketToClose > 0) {
            return groupStart;
        }
        return -1;
    }


    public static void main(String args []) {
        char[] case1 = {'(', 'a', '+', '1', ')', '/', '(', '2', '+', 'c', ')'};
        int result1 = checkParentheses(case1);
        System.out.println("Case 1: " + result1);

        // (a+(2-c)*3
        char[] case2 = {'(', 'a', '+', '(', '2', '-', 'c', ')', '*', '3'};
        int result2 = checkParentheses(case2);
        System.out.println("Case 2: " + result2);

        // Case 3: (a+b)*3)+c
        char[] case3 = {'(', 'a', '+', 'b', ')', '*', '3', ')', '+', 'c'};
        int result3 = checkParentheses(case3);
        System.out.println("Case 3: " + result3);

        // Case 4: (a+b)*3)+c
        char[] case4 = {'a', '+', 'b', '/', 'c'};
        int result4 = checkParentheses(case4);
        System.out.println("Case 4: " + result4);
    }

}

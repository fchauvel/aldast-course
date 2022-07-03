public class Palindrome {

    public static boolean iterative(String word) {
        for (int i=0 ; i<word.length()/2 ; i++) {
            if (word.charAt(i) != word.charAt(word.length()-1-i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean recursive(String word) {
        if (word.length() <= 1)
            return true;
        return word.charAt(0) == word.charAt(word.length()-1)
            && recursive(word.substring(1, word.length()-1));
    }


    public static boolean tailRecursive(String word) {
        if (word.length() <= 1)
            return true;
        if (word.charAt(0) != word.charAt(word.length()-1))
            return false;
        return tailRecursive(word.substring(1, word.length()-1));
    }

    public static void main(String[] args) {
        String[] words = new String[]{
            "kayak",
            "madam",
            "level",
            "civic",
            "john",
            "abba",
            "abcba",
            "abcza",
            "abcbz"};
        for (var eachWord: words) {
            System.out.println(eachWord+ ": "
                               + iterative(eachWord) + ", "
                               + recursive(eachWord) + ", "
                               + tailRecursive(eachWord));
        }
    }

}

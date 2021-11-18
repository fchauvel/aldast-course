public class Matching {


    static int bruteForce(char[] text, char[] pattern) {
        for (var textSpot = 0; textSpot < text.length ; textSpot++) {
            var offset = 0;
            while (offset < pattern.length) {
                    if (text[textSpot + offset] == pattern[offset]) {
                        offset++;
                    }
                }
            if (offset == pattern.length) {
                return textSpot;
            }
        }
        return -1;
    }

    static int brokenForce(char[] text, char[] pattern) {
        for (var textSpot = 0; textSpot < text.length ; textSpot++) {
            var offset = 0;
            while (offset < pattern.length) {
                if (text[textSpot + offset] == pattern[offset]) {
                    offset++;

                } else {
                    textSpot += offset - 2;
                    break;
                }
            }
            if (offset == pattern.length) {
                return textSpot;
            }
        }
        return -1;
    }


    static int kmp(char text[], char[] pattern) {
        //var prefixes = new int[]{0, 0, 1, 2, 0};
        var prefixes = createPrefixTable3(pattern);
        showTable(prefixes);

        var textSpot = 0;
        var offset = 0;

        while (textSpot < text.length) {
            if (text[textSpot + offset] == pattern[offset]) {
                System.out.println("Match " + (textSpot+offset) + " <-> " + offset);
                offset++;
                if (offset == pattern.length) {
                    return textSpot;
                }

            } else {
                System.out.println("Error " + (textSpot+offset) + " <-> " + offset);
                if (offset > 0) {
                    textSpot += prefixes[offset-1];
                    offset = prefixes[offset-1];
                    System.out.println("Shift to " + (textSpot+offset) + " <-> " + offset);

                } else {
                    textSpot += 1;
                    offset = 0;
                    System.out.println("Shift to " + (textSpot+offset) + " <-> " + offset);
                }
            }
        }
        return -1;
    }



    static int[] createPrefixTable(char pattern[]) {
        int[] prefixLengths = new int[pattern.length];
        var end = 1;
        var length = 1;
        while (end < pattern.length) {
            var newPrefixChar = length-1;
            var newSuffixChar = end-length;
            System.out.println("Position: " + end + "\n"
                               + " - Length: " + length + "\n"
                               + " - Compare: p[" + newPrefixChar + "] ?= p[" + (end-1) + "]\n"
                               + "            p[" + 0 + "] ?= p[" + newSuffixChar +  "]");
            if (pattern[newPrefixChar] == pattern[end-1]
                && pattern[0] == pattern[newSuffixChar]) { // Match
                System.out.println("Match");
                prefixLengths[end] = length;
                length++;

            } else if (length >= end-1) {
                System.out.println("Max length: Next Position!");
                length = 1;
                end++;

            } else {
                System.out.println("Next Position!");
                length = 1;
                end++;

            }

        }
        return prefixLengths;
    }


    static int[] createPrefixTable2(char pattern[]) {
        int[] prefixLengths = new int[pattern.length];
        showTable(prefixLengths);
        var end = 1;
        var length = 1;
        while (end < pattern.length) {
            System.out.println("Comparing " +  (end-length) + " with " + end);
            if (pattern[end-length] == pattern[length]) { // Match
                System.out.println(" - Match" +  (end-length) + " with " + end);
                if (length < end) {
                    length++;
                    System.out.println("  - continue ... ");
                }
                else { // Last match -> reset
                    System.out.println("  - done. Updating table[" + end + "]=" + (length-1));

                    length = 1;
                    end++;
                }

            } else {
                System.out.println(" - Mismatch!");
                if (length > 0) {
                    System.out.println("  - Done. Updating table[" + end + "]=" + (length-1));
                    prefixLengths[end] = length-1;

                }
                System.out.println("  - Next end!");
                length = 1;
                end++;

            }

        }
        return prefixLengths;
    }


    static int[] createPrefixTable3(char pattern) {
        int[] prefixLengths = new int[pattern.length];
        var k = 0;
        for (int state=1 ; state<pattern.length ; state++) {
            while (k > 0 && pattern[k+1] != pattern[state]) {
                k = prefixLengths[k];
            }
            if (pattern[k+1] == pattern[state]) {
                k = k + 1;
            }
            prefixLengths[state] = k;
        }
        return prefixLengths;
    }


    static void showTable(int[] table) {
        System.out.println("Prefix table: ");
        for (int i = 0 ; i<table.length ;i++) {
            System.out.print(table[i] + " ");
        }
        System.out.println();
    }




    public static void main(String args[]) {
        var text = args[0].toCharArray();
        var pattern = args[1].toCharArray();


        System.out.println("Found at: " + kmp(text, pattern));

    }


}

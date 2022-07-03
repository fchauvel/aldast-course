import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

class Permutation {

    char[] choices;
    int choiceCount;

    public Permutation(int size) {
        choices = new char[size];
        choiceCount = 0;
    }

    boolean isComplete() {
        //return choiceCount == choices.length;
        return choiceCount >= 2;
    }

    void show() {
        System.out.print("[");
        for (int i=0 ; i<choiceCount ; i++) {
            System.out.print(choices[i] +  " ");
        }
        System.out.println("]");
    }

    int nextSlot() {
        return choiceCount;
    }

    boolean isInvalid() {
        for (int eachItem = 0; eachItem<choiceCount ; eachItem++) {
            for (int eachOtherItem = 0 ; eachOtherItem<choiceCount ; eachOtherItem++) {
                if (eachOtherItem != eachItem) {
                    if (choices[eachItem] == choices[eachOtherItem]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}


class Change {

    int index;
    char chosenValue;

    public Change(int slotIndex, char chosenValue) {
        this.index = slotIndex;
        this.chosenValue = chosenValue;
    }

    void applyTo(Permutation permutation) {
        permutation.choices[index] = chosenValue;
        permutation.choiceCount += 1;
    }

    void revertTo(Permutation permutation) {
        permutation.choiceCount -= 1;
    }

}

class Problem {

    char[] candidates;

    public Problem(char[] candidates) {
        this.candidates = candidates;
    }

    public Permutation emptyPermutation() {
        return new Permutation(candidates.length);
    }

    public List<Change> possibleChanges(Permutation permutation) {
        var changes = new ArrayList<Change>();
        if (!permutation.isComplete()) {
            for (var eachCandidate: candidates) {
                changes.add(new Change(permutation.nextSlot(),
                                       eachCandidate));
            }
        }
        return changes;
    }

}



public class Permutations {


    public static int countPermutations(Problem problem) {
        Permutation emptyPermutation = problem.emptyPermutation();
        var options = new HashSet<Character>();
        for (var eachCandidate: problem.candidates) {
            options.add(eachCandidate);
        }
        return recurse(problem, emptyPermutation, options);
    }

    static int recurse(Problem problem, Permutation permutation, Set<Character> options) {
        if (permutation.isInvalid()) return 0;
        if (permutation.isComplete()) {
             permutation.show();
            return 1;

        } else {
            var total = 0;
            var localOptions = new HashSet<Character>(options);
            for (var eachChange: problem.possibleChanges(permutation)) {
                if (localOptions.contains(eachChange.chosenValue)) {
                    eachChange.applyTo(permutation);
                    total += recurse(problem, permutation, localOptions);
                    eachChange.revertTo(permutation);
                    localOptions.remove(eachChange.chosenValue);
                }
            }
            return total;
        }

    }

    public static void main(String args[]) {
        var problem = new Problem(new char[]{'L', 'B', 'M', 'C'});

        var totalCount = countPermutations(problem);
        System.out.println("Count:" + totalCount);
        if (totalCount != 24) {
            System.out.println("Broken count");
        }

    }

}

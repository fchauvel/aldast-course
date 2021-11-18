import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;



class Item {
    private String name;
    private int weight;
    private int cost;

    public Item(String name, int weight, int cost) {
        this.name = name;
        this.weight = weight;
        this.cost = cost;
    }

    public int getWeight() {
        return this.weight;
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return cost;
    }

}

class Problem {

    private int capacity;
    private List<Item> items;

    public Problem(Item[] items, int capacity) {
        this.items = new ArrayList<Item>(Arrays.asList(items));
        this.capacity = capacity;

    }

    public int bound(Configuration configuration) {
        int value = 0;
        for (var anyItem: items) {
            if (!configuration.hasKey(anyItem)
                || configuration.includes(anyItem)) {
                value += anyItem.getCost();
            }
        }
        return value;
    }

    public int gain(Configuration configuration) {
        int value = 0;
        for (var anyItem: items) {
            if (configuration.includes(anyItem)) {
                value += anyItem.getCost();
            }
        }
        return value;
    }

    public boolean isValid(Configuration configuration) {
        int totalWeight = 0;
        for (var eachItem: this.items) {
            if (configuration.includes(eachItem)) {
                totalWeight += eachItem.getWeight();
            }
        }
        return totalWeight <= capacity;
    }

    public boolean isComplete(Configuration configuration) {
        return configuration.size() >= items.size();
    }


    public Configuration zeroConfiguration() {
        return new Configuration();
    }

    public List<Change> nextExtension(Configuration configuration) {
        List<Change> changes = new ArrayList<Change>();
        for (var eachItem: items) {
            if (!configuration.hasKey(eachItem)) {
                changes.add(new Change() {

                        public  void applyTo(Configuration configuration) {
                            configuration.choose(eachItem);
                        }

                        public void revertOn(Configuration configuration) {
                            configuration.revert(eachItem);
                        }
                    });
                changes.add(new Change() {

                        public void applyTo(Configuration configuration) {
                            configuration.discard(eachItem);
                        }

                        public void revertOn(Configuration configuration) {
                        configuration.revert(eachItem);
                        }
                    });
                return changes;
            }
        }
        return new ArrayList<Change>();
    }

    public void show(Configuration configuration) {
        for (var eachItem: items){
            System.out.println((eachItem.getName() + ": " +
                                configuration.includes(eachItem)));
        }

    }

}

interface Change {

    void applyTo(Configuration configration);

    void revertOn(Configuration revert);

}

class Configuration {

    private Map<String, Boolean> selection;

    public Configuration() {
        this.selection = new HashMap<String, Boolean>();
    }

    public int size() {
        return this.selection.size();
    }

    public boolean hasKey(Item givenItem) {
        return selection.containsKey(givenItem.getName());
    }

    public boolean includes(Item givenItem) {
        if (selection.containsKey(givenItem.getName())) {
            return selection.get(givenItem.getName());
        }
        return false;
    }

    public void choose(Item givenItem) {
        this.selection.put(givenItem.getName(), true);
    }

    public void discard(Item givenItem) {
        this.selection.put(givenItem.getName(), false);
    }

    public void revert(Item givenItem) {
        this.selection.remove(givenItem.getName());
    }

    public Configuration clone() {
        var clone = new Configuration();
        clone.selection = new HashMap<String, Boolean>(this.selection);
        return clone;
    }

}



public class Search {

    private static int configurationCount = 0;
    private static int validCount = 0;
    private static int bestGainSoFar = 0;
    private static Configuration best;


    static void solve(Problem problem) {
        reset();
        var emptyConfiguration = problem.zeroConfiguration();
        backtrack(problem, emptyConfiguration);
    }

    static void reset() {
        configurationCount = 0;
        validCount = 0;
        bestGainSoFar = 0;
        best = null;
    }

    static void backtrack(Problem problem, Configuration configuration) {
        if (problem.isValid(configuration)
            && problem.bound(configuration) > bestGainSoFar) {
            if (problem.isComplete(configuration)) {
                found(configuration);
                var gain = problem.gain(configuration);
                if (bestGainSoFar < gain) {
                    bestGainSoFar = gain;
                    best = configuration.clone();
                }

            } else {

                for (var eachChange: problem.nextExtension(configuration)) {
                    eachChange.applyTo(configuration);
                    backtrack(problem, configuration);
                    eachChange.revertOn(configuration);

                }
            }
        }
    }


    static void branchAndBound(Problem problem) {
        var candidates = new LinkedList<Change>();
        candidates.add(problem.zeroConfiguration);
        while (!candidates.isEmpty()) {
            var configuration = candidates.poll()
            if (problem.isValid(configuration)
                && problem.bound(configuration) > bestGainSoFar) {
                if (problem.isComplete(configuration)) {
                    found(configuration);
                    var gain = problem.gain(configuration);
                    if (bestGainSoFar < gain) {
                        bestGainSoFar = gain;
                        best = configuration.clone();
                    }

                } else {
                    for (var eachChange: problem.nextExtension(configuration)) {
                        eachChange.applyTo(configuration);
                        candidates.add(configuration.clone());
                        eachChange.revert(configuration);
                    }

                }
            }

        }

    }

    static void found(Configuration solution) {
        System.out.print(".");
        validCount += 1;

    }



    static void showStats(Problem problem) {
        System.out.println("Valid Conf: " + validCount);
        problem.show(best);
        System.out.println("Worth: " + bestGainSoFar);
    }

    public static void main(String args[]) {
        var knapsack =
            new Problem(
                        new Item[]{
                            new Item("books", 7, 3000),
                            new Item("guitar", 3, 4000),
                            new Item("cooking", 8, 7000),
                            new Item("laptop", 2, 9000)
                        },
                        10);

        solve(knapsack);
        System.out.println("-----");
        showStats(knapsack);

    }

}

import java.util.ArrayList;
import java.util.Random;

class Item {

    String name;
    int weight;
    int value;

    public Item(String name, int weight, int value) {
        this.name = name;
        this.value = value;
        this.weight = weight;
    }

    public String toString() {
        return name + "(" + weight + " kg/" + value+ "$)";
    }
}


public class Knapsack {

    private Item[] items;
    private int count = 0;

    public Knapsack(Item[] items) {
        this.items = items;
    }


    int dynamicProgramming(int capacity) {
        int[][] cache = new int[items.length+1][capacity+1];
        for (int n=0 ; n<=items.length ; n++) {
            for (int c=0 ; c<capacity+1 ; c++) {
                cache[n][c] = 0;
                if (c == 0) cache[n][c] = 0;
                if (n == items.length-1)
                    cache[n][c] = c < items[n].weight ? 0 : items[n].value;
            }
        }

        for (int n=items.length-1 ; n>=0 ;n--) {
            for (int c=capacity ; c>=0 ;c--) {
                var item = items[n];
                var without = cache[n+1][c];

                var with = (item.weight <= c) ? item.value + cache[n+1][Math.max(c-item.weight, 0)] : 0;
                cache[n][c] = Math.max(with, without);
            }
        }

        return cache[0][capacity];
    }

    public int withMemoization(int capacity) {
        int[][] cache = new int[items.length][capacity+1];
        for (int n=0 ; n<=items.length-1 ; n++) {
            for (int c=0 ; c<capacity+1 ; c++) {
                cache[n][c] = -1;
                if (c == 0) cache[n][c] = 0;
                if (n == items.length-1)
                    cache[n][c] = c < items[n].weight ? 0 : items[n].value;
            }
        }
        return memoize(0, capacity, cache);
    }

    private int memoize(int index, int capacity, int[][] cache) {
        count++;

        var item = items[index];
        indent(item + " c=" + capacity, index);
        var without = cache[index+1][capacity];
        indent("Without: ks(" + (index+1) + ", " + capacity +") = " + without, index);
        if (without == -1) {
            without = memoize(index+1, capacity, cache);
            cache[index+1][capacity] = without;
        }

        var next = Math.max(capacity-item.weight, 0);
        var with = cache[index+1][next];
        indent("With: ks(" + (index+1) + ", " + (next) +") = " + with, index);
        if (with == -1) {
            with = item.value + memoize(index+1, next, cache);
            cache[index+1][next] = with;
        }

        indent("1/0: " + with + "/" + without, index);
        return Math.max(with, without);
    }

    public int recursive(int capacity) {
        var result = recurse(0, capacity);
        return result;
    }

    private int recurse(int index, int capacity) {
        count++;
        if (index >= items.length-1) {
            if (items[index].weight >= capacity) {
                return items[index].value;
            } else {
                return 0;
            }
        }
        var item = items[index];
        return  Math.max(recurse(index+1, capacity),
                         item.value + recurse(index+1, capacity - item.weight));

    }

    void indent(String message, int level) {
        for(int i=0 ; i<level ; i++) System.out.print("  ");
        System.out.println(message);
    }


    void show() {
        for (var eachItem: items) {
            System.out.printf("%10s %5d kg %10d $\n", eachItem.name, eachItem.weight, eachItem.value);
        }
    }


    public static void main(String args[]) {
        var knapsack = lectureExample();
        knapsack.show();

        var solution = knapsack.withMemoization(Integer.parseInt(args[0]));
        System.out.println("Maximum: " + solution + " EUR");
        System.out.println("Count: " + knapsack.count);
    }

    static Knapsack lectureExample() {
        return new Knapsack(new Item[]{
                new Item("Laptop", 2, 9000),
                new Item("Books", 7, 3000),
                new Item("Music", 3, 4000),
                new Item("Cooking", 8, 7000)
            });
    }

    static Knapsack biggerExample() {
        return new Knapsack(new Item[]{
                new Item("Laptop", 1, 9000),
                new Item("Books", 1, 3000),
                new Item("Music", 1, 4000),
                new Item("Cooking", 1, 7000),
                new Item("Painting", 1, 10000),
                new Item("Vase", 1, 5000),
                new Item("Food", 1, 500),
                new Item("Clothes", 1, 1000),
                new Item("Toys", 1, 4000),
                new Item("Stationaries", 1, 50)
            });
    }

    static Knapsack randomProblem(int itemCount) {
        var generator = new Random();
        var items = new Item[itemCount];
        for (int i=0 ; i<itemCount ; i++) {
            var name = "Item #" + (i+1);
            var weight = 1 + generator.nextInt(20);
            var value = 1000 + generator.nextInt(9000);
            items[i] = new Item(name, weight, value);
        }
        return new Knapsack(items);
    }

}

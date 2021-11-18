import java.util.List;
import java.util.LinkedList;

class Solution {

    private LinkedList<Integer> coins;

    public Solution () {
        this.coins = new LinkedList<Integer>();
    }

    public void add(int coin) {
        this.coins.add(coin);
    }

    public void remove() {
        this.coins.removeLast();
    }

    public boolean isValid(int desiredPrice) {
        int total = 0;
        for (var anyCoin: this.coins) {
            total += anyCoin;
        }
        return total == desiredPrice;
    }

    public boolean isComplete(int desiredPrice) {
        int total = 0;
        for (var anyCoin: this.coins) {
            total += anyCoin;
        }
        return total >= desiredPrice;
    }

    public String toString() {
        var out = "";
        for (var eachCoin: coins) {
            out += eachCoin + " ";

        }
        return out;
    }

}

public class Coins {



    public static void findSolutions(int[] coins, int desiredPrice) {
        var current = new Solution();
        recurse(current, coins, desiredPrice);
    }


    private static void recurse(Solution current, int[] coins, int desiredPrice) {
        //        System.out.println("Current: " + current);
        if (!current.isComplete(desiredPrice)) {
            //System.out.println("incomplete");
            for(var eachCoin: coins) {
                current.add(eachCoin);
                if (current.isValid(desiredPrice)) {
                    System.out.println("Found" + current);
                } else {
                    recurse(current, coins, desiredPrice);
                }
                current.remove();
            }
        }
    }

    public static void main(String args[]) {

        int[] coins = {50, 100};

        findSolutions(coins, 200);

    }


}

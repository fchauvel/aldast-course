import java.util.ArrayList;

public class Heap<Item extends Comparable<Item>> {

    private ArrayList<Item> items;

    public Heap() {
        this.items = new ArrayList<Item>();
    }

    public Heap(Item[] givenItems) {
        this.items = new ArrayList<Item>();
        for (Item eachItem:  givenItems) {
            items.add(eachItem);
        }
        heapify();
    }

    private void heapify() {
        int currentIndex = (size()-1) / 2;
        while (currentIndex >= 0) {
            bubbleDown(currentIndex);
            currentIndex--;;
        }
    }

    public int size() {
        return items.size();
    }

    private boolean isRoot(int index) {
        return index == 0;
    }

    private Item itemAt(int index) {
        return items.get(index);
    }

    private int parentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }

    private Item parent(int childIndex) {
        return this.items.get(parentIndex(childIndex));
    }

    private int leftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    private int rightChildIndex(int parentIndex) {
        return 2 * parentIndex + 2;
    }

    private boolean hasLeftChild(int parentIndex) {
        return leftChildIndex(parentIndex) < size();
    }

    private boolean hasRightChild(int parentIndex) {
        return rightChildIndex(parentIndex) < size();
    }

    public Heap<Item> insert(Item givenItem) {
        items.add(givenItem);
        bubbleUp(lastIndex());
        return this;
    }

    private int lastIndex() {
        return size() - 1;
    }

    private void bubbleUp(int nodeIndex) {
        while (!isRoot(nodeIndex)) {
            int parentIndex = parentIndex(nodeIndex);
            int difference = itemAt(parentIndex).compareTo(itemAt(nodeIndex));
            if (difference > 0) {
                swap(parentIndex, nodeIndex);
            }
            nodeIndex = parentIndex;
        }
    }

    public Item next() {
        swap(lastIndex(), 0);
        Item nextItem = items.remove(lastIndex());
        bubbleDown(0);
        return nextItem;
    }

    private void bubbleDown(int parentIndex) {
        while (hasAnyChildren(parentIndex)) {
            int leastChildIndex = leastChildIndex(parentIndex);
            int difference = itemAt(parentIndex).compareTo(itemAt(leastChildIndex));
            if (difference <= 0) {
                break;
            }
            swap(parentIndex, leastChildIndex);
            parentIndex = leastChildIndex;
        }
    }

    private boolean hasAnyChildren(int parentIndex) {
        return hasRightChild(parentIndex) || hasLeftChild(parentIndex);
    }

    private int leastChildIndex(int parentIndex) {

        if (hasRightChild(parentIndex)) {
            int leftIndex = leftChildIndex(parentIndex);
            int rightIndex = rightChildIndex(parentIndex);
            int difference = itemAt(leftIndex).compareTo(itemAt(rightIndex));
            return (difference < 0) ? leftIndex : rightIndex;
        }
        if (hasLeftChild(parentIndex)) {
            return leftChildIndex(parentIndex);
        }
        throw new RuntimeException("Error: Node #" + parentIndex + " has no child!");
    }

    private void swap(int first, int second) {
        var temporary = items.get(first);
        items.set(first, items.get(second));
        items.set(second, temporary);
    }

    public String format() {
        if (isEmpty()) return "()";
        return formatNode(0);
    }

    private String formatNode(int index) {
        String left = "", right = "";
        if (hasLeftChild(index)) left = formatNode(leftChildIndex(index)) + ", ";
        if (hasRightChild(index)) right = ", " + formatNode(rightChildIndex(index));
        return "(" + left + itemAt(index) + right + ")";
    }

    public boolean isEmpty() {
        return size() <= 0;
    }

    public static void main(String args[]) {
        Integer [] values = new Integer[]{3, 13, 45, 23, 31, 20, 90, 56};
        var heap = new Heap<Integer>(values);
        System.out.println(heap.format());

        System.out.println("----------");
        while (!heap.isEmpty()) {
            var next = heap.next();
            System.out.println(next);
            System.out.println(heap.format());
        }
        System.out.println();
    }

}

public class AVL<Item extends Comparable<Item>>  {

    private final Item item;
    private AVL<Item> left;
    private AVL<Item> right;


    public AVL(Item givenItem) {
        this.item = givenItem;
        this.left = null;
        this.right = null;
    }

    public Item getItem() {
        return this.item;
    }

    public boolean hasLeftChild() {
        return this.left != null;
    }

    public boolean hasRightChild() {
        return this.right != null;
    }


    public int height() {
        int leftHeight = 0;
        int rightHeight = 0;
        if (this.hasLeftChild()) leftHeight = left.height();
        if (this.hasRightChild()) rightHeight = right.height();
        return 1 + Math.max(leftHeight, rightHeight);
    }

    public int balanceFactor() {
        int leftHeight = 0;
        int rightHeight = 0;
        if (this.hasLeftChild()) leftHeight = left.height();
        if (this.hasRightChild()) rightHeight = right.height();
        int balance = leftHeight - rightHeight;
        System.out.println("BF("+item+")=" + balance);
        return balance;
    }

    private AVL<Item> rotateLeft() {
        if (right == null)
            throw new IllegalStateException("Cannot rotate to the left: No right child!");
        System.out.println("rotateLeft(" + item + ")");
        AVL<Item> newRoot = right;
        AVL<Item> tmp = right.left;
        right.left = this;
        right = tmp;
        return newRoot;
    }

    private AVL<Item> rotateRight() {
        if (left == null)
            throw new IllegalStateException("Cannot rotate to the right: No Left child!");
        System.out.println("rotateRight(" + item + ")");
        AVL<Item> newRoot = left;
        AVL<Item> tmp = left.right;
        left.right = this;
        left = tmp;
        return newRoot;
    }

    public AVL<Item> insert(Item givenItem) {
        int difference = this.item.compareTo(givenItem);
        if (difference > 0)  {
            return insertLeft(givenItem);

        } else if (difference < 0) {
            return insertRight(givenItem);

        } else {
            throw new RuntimeException("Duplicated item " + item);
        }
    }

    private AVL<Item> insertLeft(Item givenItem) {
        if (hasLeftChild()) {
            this.left = this.left.insert(givenItem);
            if (this.balanceFactor() > 1) {
                int side = this.left.item.compareTo(givenItem);
                if (side > 0) {
                    return this.rotateRight();
                } else if (side < 0){
                    this.left = this.left.rotateLeft();
                    return this.rotateRight();
                }
            }

        } else {
            this.left = new AVL(givenItem);

        }
        return this;
    }

    public AVL<Item> insertRight(Item givenItem) {
        if (hasRightChild()) {
            this.right = this.right.insert(givenItem);
            if (this.balanceFactor() < -1) {
                int side = this.right.item.compareTo(givenItem);
                if (side < 0) {
                    return this.rotateLeft();
                } else if (side > 0) {
                    this.right = this.right.rotateRight();
                    return this.rotateLeft();
                }
            }

        } else {
            this.right = new AVL(givenItem);
        }
        return this;
    }

    public Item search(Item givenItem) throws NoSuchElement {
        int difference = this.item.compareTo(givenItem);
        if (difference > 0) {
            if (this.hasLeftChild()) {
                return this.left.search(givenItem);
            }
            throw new NoSuchElement();
        } else if (difference < 0) {
            if (this.hasRightChild()) {

            }
            throw new NoSuchElement();
        } else {
            return this.item;
        }
    }


    public Item minimum() {
        return minimumAVL().item;
    }

    private AVL<Item> minimumAVL() {
        if (this.hasLeftChild()) {
            return this.left.minimumAVL();
        }
        return this;
    }



    public Item maximum() {
        if (this.hasRightChild()) {
            return this.right.maximum();
        }
        return this.item;
    }

    public Item findSuccessor(Item givenItem) throws NoSuchElement, NoSuccessor {
        return findSuccessorAVL(givenItem).item;
    }

    private AVL<Item> findSuccessorAVL(Item givenItem) throws NoSuchElement, NoSuccessor {
        int difference = this.item.compareTo(givenItem);
        if (difference < 0) {
            if (hasRightChild()) {
                return right.findSuccessorAVL(givenItem);
            }
            throw new NoSuchElement();

        } else if (difference > 0) {
            if (hasLeftChild()) {
                try {
                    return left.findSuccessorAVL(givenItem);
                } catch (NoSuccessor error) {
                    return this;
                }
            }
            throw new NoSuchElement();

        } else {
           if (hasRightChild()) {
                return right.minimumAVL();
            }
           throw new NoSuccessor();
        }
    }

    public AVL<Item> delete(Item givenItem) throws NoSuchElement {
        int difference = this.item.compareTo(givenItem);
        if (difference < 0) {
            return this.deleteRight(givenItem);

        } else if (difference > 0) {
            return this.deleteLeft(givenItem);

        } else {
           return deleteThis();
        }
    }

    private AVL<Item> deleteThis() throws NoSuchElement {
        if (hasLeftChild() && hasRightChild()) {
            try {
                AVL<Item> successor = this.findSuccessorAVL(this.item);
                successor.right = this.right.delete(successor.item);
                successor.left = this.left;
                return successor;
            } catch (NoSuccessor error) {
                throw new RuntimeException(error);
            }
        }
        if (hasLeftChild()) {
            return this.left;
        }
        if (hasRightChild()) {
            return this.right;
        }
        return null;
    }

    private AVL<Item> deleteRight(Item givenItem) throws NoSuchElement {
        if (hasRightChild()) {
            this.right = this.right.delete(givenItem);
            if (hasLeftChild() && balanceFactor() > 1) {
                int side = this.left.item.compareTo(givenItem);
                if (side > 0) {
                    return this.rotateRight();
                } else if (side < 0){
                    this.left = this.left.rotateLeft();
                    return this.rotateRight();
                }
            }
            return this;
        }
        throw new NoSuchElement();
    }

    private AVL<Item> deleteLeft(Item givenItem) throws NoSuchElement {
        if (hasLeftChild()) {
            this.left = this.left.delete(givenItem);
            if (hasRightChild() && this.balanceFactor() < -1) {
                int side = this.right.item.compareTo(givenItem);
                if (side < 0) {
                    return this.rotateLeft();
                } else if (side > 0) {
                    this.right = this.right.rotateRight();
                    return this.rotateLeft();
                }
            }
            return this;
        }
        throw new NoSuchElement();
    }

    public String show() {
        String left = "", right = "";
        if (hasLeftChild()) left = this.left.show() + ", ";
        if (hasRightChild()) right = ", " + this.right.show();
        return left + item.toString() + right;
    }

    public String format() {
        String left = "", right = "";
        if (hasLeftChild()) left = this.left.format() + ", ";
        if (hasRightChild()) right = ", " + this.right.format();
        return "(" + left + item.toString() + right + ")";
    }


    public static void main(String[] args) throws Exception{
        int[] values = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70};
        var tree = new AVL<Integer>(1);
        for (int index=0 ; index<values.length ; index++){
            System.out.println();
            System.out.println("insert(" + values[index] + ")");
            tree = tree.insert(values[index]);
            System.out.println(tree.format());
        }

        System.out.println(tree.format());
        System.out.println("BF = " + tree.balanceFactor());

        for (int index=values.length-1 ; index>=0 ; index = index-2){
            System.out.println();
            System.out.println("delete(" + values[index] + ")");
            tree = tree.delete(values[index]);
            System.out.println(tree.format());
        }

        System.out.println(tree.format());
        System.out.println("BF = " + tree.balanceFactor());
    }

}


class NoSuchElement extends Exception {

}

class NoSuccessor extends Exception {
}

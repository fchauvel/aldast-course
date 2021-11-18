public class Tree<Item extends Comparable<Item>>  {

    private final Item item;
    private Tree<Item> left;
    private Tree<Item> right;


    public Tree(Item givenItem) {
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

    public Tree<Item> insert(Item givenItem) {
        int difference = this.item.compareTo(givenItem);
        if (difference > 0)  {
            if (hasLeftChild()) {
                this.left.insert(givenItem);

            } else {
                this.left = new Tree(givenItem);

            }
            return this;

        } else if (difference < 0) {
            if (hasRightChild()) {
                this.right.insert(givenItem);

            } else {
                this.right = new Tree(givenItem);
            }
            return this;

        } else {
            throw new RuntimeException("Duplicated item " + item);
        }
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
        return minimumTree().item;
    }

    private Tree<Item> minimumTree() {
        if (this.hasLeftChild()) {
            return this.left.minimumTree();
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
        return findSuccessorTree(givenItem).item;
    }

    private Tree<Item> findSuccessorTree(Item givenItem) throws NoSuchElement, NoSuccessor {
        int difference = this.item.compareTo(givenItem);
        if (difference < 0) {
            if (hasRightChild()) {
                return right.findSuccessorTree(givenItem);
            }
            throw new NoSuchElement();

        } else if (difference > 0) {
            if (hasLeftChild()) {
                try {
                    return left.findSuccessorTree(givenItem);
                } catch (NoSuccessor error) {
                    return this;
                }
            }
            throw new NoSuchElement();

        } else {
           if (hasRightChild()) {
                return right.minimumTree();
            }
           throw new NoSuccessor();
        }
    }

    public Tree<Item> delete(Item givenItem) throws NoSuchElement {
        int difference = this.item.compareTo(givenItem);
        if (difference < 0) {
            if (hasRightChild()) {
                this.right = this.right.delete(givenItem);
                return this;
            }
            throw new NoSuchElement();

        } else if (difference > 0) {
            if (hasLeftChild()) {
                this.left = this.left.delete(givenItem);
                return this;
            }
            throw new NoSuchElement();

        } else {
            if (hasLeftChild() && hasRightChild()) {
                try {
                    Tree<Item> successor = this.findSuccessorTree(this.item);
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
        var tree = new Tree<Integer>(25)
            .insert(36)
            .insert(12)
            .insert(27)
            .insert(40);

        System.out.println(tree.show());

        System.out.println("Minimum = " + tree.minimum());
        System.out.println("Maximum = " + tree.maximum());

        System.out.println("12 -> " + tree.search(12));

        System.out.println("succ(12) = " + tree.findSuccessor(12));

        System.out.println(tree.format());

        tree = tree.delete(40);
        System.out.println(tree.format());
        tree = tree.insert(49).delete(25);
        System.out.println(tree.format());

    }

}


class NoSuchElement extends Exception {

}

class NoSuccessor extends Exception {
}

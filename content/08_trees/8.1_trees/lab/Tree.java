/*
 * Binary Search Tree
 */


import java.util.List;
import java.util.ArrayList;



class NoSuchValue extends RuntimeException {

    private int value;

    public NoSuchValue(int givenValue) {
        super();
        this.value = givenValue;
    }
}


class SuccessorNotFound extends RuntimeException {}

class PredecessorNotFound extends RuntimeException {}


public class Tree {

    // Properties
    private int value;
    private Tree left;
    private Tree right;


    public Tree(int value) {
        this.value = value;
    }


    public Tree insert(int givenValue) {
        if (givenValue < value) {
            // insert on the left
            if (left != null) {
                return left.insert(givenValue);

            } else {
                left = new Tree(givenValue);
                return left;
            }

        } else if (givenValue > value) {
            if ( right != null) {
                return right.insert(givenValue);

            } else {
                right = new Tree(givenValue);
                return right;
            }

        } else {
            // Raise an error
            throw new RuntimeException("Duplicated item " + givenValue);
        }
    }


    public int size() {
        int leftCount = 0;
        if (hasLeft()) {
            leftCount = left.size();
        }
        int rightCount = 0;
        if (hasRight()) {
            rightCount = right.size();
        }
        return 1 + leftCount + rightCount;
    }


    private boolean hasLeft() {
        return left != null;
    }

    private boolean hasRight() {
        return right != null;
    }


    public int height() {
        if (!hasLeft() && !hasRight()) {
            return 0;
        }

        int leftHeight = 0;
        if (hasLeft()) {
            leftHeight = left.height();
        }

        int rightHeight = 0;
        if (hasRight()) {
            rightHeight = right.height();
        }

        return 1 + Math.max(leftHeight, rightHeight);
    }

    int depth(int givenValue) {
        if (givenValue < value) {
            if (hasLeft()) {
                return 1 + left.depth(givenValue);
            }
            throw new RuntimeException("Not in the tree");
        } else if (givenValue > value) {
            if (hasRight()) {
                return 1 + right.depth(givenValue);
            }
            throw new RuntimeException("Not in the tree");
        } else {
            return 0;

        }
    }

    int minimum() {
        if (hasLeft()) {
            return left.minimum();
        }
        return value;
    }


    int maximum() {
        if (hasRight()) {
            return right.maximum();
        };
        return value;
    }


    boolean contains(int givenValue) {
        if (value < givenValue) {
            if (hasRight()) {
                return right.contains(givenValue);
            }
            return false;

        } else if (value > givenValue) {
            if (hasLeft()) {
                return left.contains(givenValue);
            }
            return false;

        } else {
            return true;

        }
    }


    int successor(int givenValue) {
        if (value < givenValue) {
            if (hasRight()) {
                return right.successor(givenValue);

            }
            throw new NoSuchValue(givenValue);

        } else if (value > givenValue) {
            if (hasLeft()) {
                try {
                    return left.successor(givenValue);

                } catch (SuccessorNotFound error) {
                    return value;

                } catch (NoSuchValue error) {
                    return value;

                }
            }
            throw new NoSuchValue(givenValue);

        } else {
            // If have found the given Value
            if (hasRight()) {
                return right.minimum();

            }
            throw new SuccessorNotFound();
        }
    }

    Tree delete(int givenValue) {
        if (givenValue < value) {
            if (hasLeft()) {
                left = left.delete(givenValue);
                return this;
            }
            throw new NoSuchValue(givenValue);
        } else if (givenValue > value) {
            if (hasRight()) {
                right = right.delete(givenValue);
                return this;
            }
            throw new NoSuchValue(givenValue);
        } else {
            if (hasLeft() && !hasRight()) {
                return left;
            }
            if (!hasLeft() && hasRight()) {
                return right;
            }
            if (hasLeft() && hasRight()) {
                try {
                    value = successor(value);

                } catch (SuccessorNotFound error) {
                    return null;

                }
                right.delete(value);
                return this;
            }
            return null;
        }
    }


    public String format() {
        String leftSide = " ?";
        if (hasLeft()) {
            leftSide = left.format();
        }
        String rightSide = " ?";
        if (hasRight()) {
            rightSide = right.format();
        }
        return "(" + leftSide + ", " + value + ", " + rightSide + ")";
    }


    public static void main(String args[]) {
        Tree tree = new Tree(50);
        tree.insert(25);
        tree.insert(75);
        var subTree = tree.insert(45);
        tree.insert(99);
        tree.insert(36);
        tree.insert(51);

        tree = tree.delete(50);
        if (tree.contains(50)) {
            System.out.println("Deletion error: 50");
        }
        if (!tree.contains(25)) {
            System.out.println("Deletion error: 50 -> 25");
        }
        if (!tree.contains(99)) {
            System.out.println("Deletion error: 50 -> 99");
        }

        System.out.println("OK");

    }

}

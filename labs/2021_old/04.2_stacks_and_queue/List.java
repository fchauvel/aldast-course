class List<T> {

    class Node<T> {
        T item;
        Node next;

        Node(T item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    private Node first;
    private Node last;

    public List() {
        this.first = null;
        this.last = null;
    }

    public void insert(T givenItem) {
        Node newNode = new Node(givenItem, this.first);
        this.first = newNode;
    }

    public void insertAt(T givenItem, int position) {
        Node previous = find(position-1);
        Node newNode = new Node(givenItem, previous.next);
        previous.next = newNode;
    }

    public Node find(int givenPosition) {
        int currentPosition = -1;
        Node currentNode = this.first;
        while (currentNode != null) {
            currentPosition += 1;
            if (currentPosition == givenPosition) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        throw new RuntimeException("Invalid list position: " + givenPosition);
    }

    public int length() {
        int counter = 0;
        Node current = this.first;
        while (current != null) {
            counter += 1;
            current = current.next;
        }
        return counter;
    }


    public static void main(String args[]) {
        System.out.println("Hi");

        List<String> myList = new List();
        myList.insert("Franck");
        myList.insert("John");
        myList.insert("James");

        System.out.println("At 1: " + myList.find(1).item);

        System.out.println("Length: " + myList.length());
    }

}

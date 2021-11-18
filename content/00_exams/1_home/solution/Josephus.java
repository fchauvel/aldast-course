    class List {
        int length;
        Node first;


        public List() {
            this.length = 0;
            this.first = null;
        }

        public void append(int playerId) {
            Node newNode = new Node();
            newNode.playerId = playerId;

            if (this.length == 0) {
                newNode.previous = newNode;
                newNode.next = newNode;
                this.first = newNode;
            } else {
                newNode.previous = this.first.previous;
                this.first.previous.next = newNode;
                this.first.previous = newNode;
                newNode.next = this.first;
            }
            this.length += 1;
        }

        public void delete(Node node) {
            Node predecessor = node.previous;
            Node successor = node.next;
            predecessor.next = successor;
            successor.previous = predecessor;
            if (node == this.first) {
                this.first = successor;
            }
            this.length -= 1;
        }

    }

    class Node {
        int playerId;
        Node next;
        Node previous;
    }

public class Josephus {

    public static void main(String args[]) {
        if (args.length != 2) {
            throw new RuntimeException("Invalid number of arguments!");
        }
        int PLAYER_COUNT = Integer.parseInt(args[0]);
        int HOP_COUNT = Integer.parseInt(args[1]);

        List players = new List();
        for (int playerId=1 ; playerId<=PLAYER_COUNT ; playerId++) {
            players.append(playerId);
        }

        Node playerWithTheBall = players.first;
        while (players.length > 1) {
            for (int counter=0 ; counter<HOP_COUNT ; counter++) {
                playerWithTheBall = playerWithTheBall.next;
            }
            players.delete(playerWithTheBall);
            System.out.print(playerWithTheBall.playerId + " ");
            playerWithTheBall = playerWithTheBall.next;
        }
        System.out.println();
        System.out.println("Winner: " + players.first.playerId);
    }


}

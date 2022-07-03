import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Stack;


interface VertexOperator<V> {

    void applyTo(V vertex);

}

public class Graph<T> {
    private HashMap<T, HashSet<T>> vertices;


    public Graph() {
        this.vertices = new HashMap<T, HashSet<T>>();
    }


    public List<T> neighboursOf(T vertex) {
        return new ArrayList<T>(vertices.get(vertex));
    }


    public Graph<T> create(T vertex) {
        var adjacencyList = new HashSet<T>();
        this.vertices.put(vertex, adjacencyList);
        return this;
    }


    public Graph<T> connect(T source, T target) {
        this.vertices.get(source).add(target);
        return this;
    }


    public Graph<T> interconnect(T source, T target) {
        this.connect(source, target);
        this.connect(target, source);
        return this;
    }

    public void applyDepthFirst(T start, VertexOperator<T> operator) {
        final Stack<T> next = new Stack<T>();
        final var visited = new HashSet<T>();

        next.push(start);
        while (!next.isEmpty()) {
            var current = next.pop();
            if (!visited.contains(current)) {
                operator.applyTo(current);
                visited.add(current);
                for (T eachNeighbour: neighboursOf(current) ){
                    next.push(eachNeighbour);
                }
            }
        }
    }

    public void applyBreadthFirst(T start, VertexOperator<T> operator) {
        final Queue<T> next = new LinkedList<T>();
        final var visited = new HashSet<T>();

        next.add(start);
        while (!next.isEmpty()) {
            var current = next.remove();
            if (!visited.contains(current)) {
                operator.applyTo(current);
                visited.add(current);
                for (T eachNeighbour: neighboursOf(current) ){
                    next.add(eachNeighbour);
                }
            }
        }
    }

    public static void main(String args[]) {
        var buddies = new Graph<String>();
        buddies.create("Denis")
            .create("Erik")
            .create("Frank")
            .create("John")
            .create("Lisa")
            .create("Mary")
            .create("Olive")
            .create("Peter")
            .create("Thomas");

        buddies.interconnect("Denis", "Frank");
        buddies.interconnect("Denis", "Olive");
        buddies.interconnect("Olive", "Mary");
        buddies.interconnect("Olive", "Erik");
        buddies.interconnect("Mary", "Peter");
        buddies.interconnect("Mary", "John");
        buddies.interconnect("John", "Lisa");
        buddies.interconnect("Lisa", "Frank");
        buddies.interconnect("Thomas", "Frank");


        var showName = new VertexOperator<String> () {
                public void applyTo(String vertex) {
                    System.out.println(vertex);
                }
            };

        buddies.applyDepthFirst("Denis", showName);
        System.out.println("---");
        buddies.applyBreadthFirst("Denis", showName);
    }


}

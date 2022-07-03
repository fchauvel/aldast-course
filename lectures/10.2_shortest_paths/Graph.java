import java.util.Set;
import java.util.Map;
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


    private static class Link<T> {
        T source;
        T end;
        float weight;'>

        Link(T source, T end, float weight) {
            this.source = source;
            this.end = end;
            this.weight = weight;
        }

    }


    private HashMap<T, HashMap<T, Link<T>>> vertices;


    public Graph() {
        this.vertices = new HashMap<T, HashMap<T, Link<T>>>();
    }


    public Set<Link<T>> allEdges() {
        var allEdges = new HashSet<Link<T>>();
        for (var eachSourceVertex: vertices.keySet()) {
            var outgoingEdges = vertices.get(eachSourceVertex).values();
            allEdges.addAll(outgoingEdges);
        }
        return allEdges;
    }

    public List<T> neighboursOf(T vertex) {
        return new ArrayList<T>(vertices.get(vertex).keySet());
    }

    public float weightBetween(T source, T target) {
        return vertices.get(source).get(target).weight;
    }

    public Graph<T> create(T vertex) {
        var neighbours = new HashMap<T, Link<T>>();
        this.vertices.put(vertex, neighbours);
        return this;
    }


    public Graph<T> connect(T source, T target, float weight) {
        var newEdge = new Link(source, target, weight);
        this.vertices.get(source).put(target, newEdge);
        return this;
    }


    public Graph<T> interconnect(T source, T target, float weight) {
        this.connect(source, target, weight);
        this.connect(target, source, weight);
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


    public Map<T, T> dijkstra(T source) {
        var weights = new HashMap<T, Float>();
        var path = new HashMap<T, T>();

        initialize(weights, path);

        var unknowns = new LinkedList<T>(vertices.keySet());
        while (!unknowns.isEmpty()) {
            var current = minimumWeight(unknowns, weights);
            for (var anyNeighbour: neighboursOf(current)) {
                if (unknowns.contains(anyNeighbour)) {
                    adjust(weights, path, current, anyNeighbour)
                }
            }
            unknowns.remove(current);
        }

        return path;
    }

    private void initialize(HashMap<T, Float> weights,
                            HashMap<T, T> path) {
        for (var anyVertex: vertices.keySet()) {
            weights.put(anyVertex, Float.POSITIVE_INFINITY);
            if (anyVertex == source) {
                weights.put(source, 0f);
            }
        }
    }

    private void adjust(HashMap<T, Float> weights,
                        HashMap<T, T> path
                        T current,
                        T neighbour) {
        var newWeight = weights.get(current) + weightBetween(current, neighbour);
        if (newWeight < weights.get(neighbour)) {
            weights.put(neighbour, newWeight);
            path.put(neighbour, current);
        }
    }

    private T minimumWeight(List<T> candidates, Map<T, Float> distances) {
        if (candidates.size() == 1) {
            return candidates.get(0);
        }
        var minimum = candidates.get(0);
        for(int index=0 ; index<candidates.size() ; index++) {
            var current = candidates.get(index);
            if (distances.get(current) < distances.get(minimum)) {
                minimum = current;
            }
        }
        return minimum;
    }


    public Map<T,T> bellmanFord(T source) throws NegativeCycleFound {
        var weights = new HashMap<T, Float>();
        var paths = new HashMap<T, T>();

        initialize(weights, paths);

        final var edges = allEdges();
        for (int i = 1 ; i<vertices.size() ; i++) {
            for (var anyEdge: edges) {
                propagate(weights, paths, anyEdge);
            }
        }

        ensureNoNegativeCycle(edges, weights);

        return paths;
    }

    void propagate(Map<T, Float> weights, Map<T, T> paths, Edge<T> edge) {
        var newWeight = weights.get(edge.source) + edge.weight;
        if (newWeight < weights.get(edge.end)) {
            weights.put(edge.end, newWeight);
            paths.put(edge.end, edge.source);
        }
    }

    void ensureNoNegativeCycle(List<Edge<T>> edges, Map<T, Float> weights) {
        for (var anyEdge: edges) {
            var newWeight = weights.get(anyEdge.source)
                + anyEdge.weight;
            if (newWeight < weights.get(anyEdge.end)) {
                throw new NegativeCycleFound();
            }
        }
    }


    Map<Pair<T>, Float> floydWarhsall() {
        var matrix = new HashMap<Pair<T>, Float>();

        initialize(matrix);
        for(var anyVertex: vertices()) {
            for (var anySource: vertices()) {
                for (var anyTarget: vertices()) {
                    adjust(matrix, anyVertex, anySource, anyTarget);
                }
            }

        }

        return matrix;
    }

    void initialize(Map<Pair<T>, Float> matrix) {
        for (var anySource: vertices) {
            for (var anyTarget: vertices) {
                matrix.put(new Pair<T>(anySource, anyTarget),
                           Float.POSITIVE_INFINITY);

            }
        }
        for(var eachEdge: allEdges()) {
            matrix.put(new Pair<T,T>(eachEdge.start, eachEdge.end),
                       eachEdge.weight);
        }
        for (var anyVertex: vertices) {
            matrix.put(new Pair<T>(anyVertex, anyVertex),
                       0);
        }
    }

    void adjust(Map<Pair<T>, Float> matrix,
                T givenVertex, T source, T target) {
        var newWeight = matrix.get(new Pair<T>(source, givenVertex)) +
            matrix.get(new Pair<T>(givenVertex, target));
        if (newWeight < matrix.get(new Pair<T>(source, target))) {
            matrix.put(new Pair<T>(source, target),
                       newWeight);
        }
    }




    public static <T> List<T> shortestPathTo(T target, Map<T, T> predecessors) {
        var path = new LinkedList<T>();
        var current = target;
        while (predecessors.containsKey(current)) {
            path.addFirst(current);
            current = predecessors.get(current);
        }
        path.addFirst(current);
        return path;
    }


    public static void main(String args[]) throws Exception {
        var buddies = new Graph<String>();
        buddies.create("A")
            .create("B")
            .create("C")
            .create("D");

        buddies.connect("A", "B", 8);
        buddies.connect("A", "C", 2);
        buddies.connect("C", "D", 2);
        buddies.connect("B", "C", -7);

        var friends = buddies.bellmanFord("A");
        System.out.println(shortestPathTo("D", friends));

    }


}


class NegativeCycleFound extends Exception {};

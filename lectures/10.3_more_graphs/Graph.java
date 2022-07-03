import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;


class Edge<T> {

    T start;
    T end;
    int capacity;
    int flow;

    public Edge(T start, T end, int capacity) {
        this.start = start;
        this.end = end;
        this.capacity = capacity;
        this.flow = 0;
    }

    public boolean hasFlow() {
        return flow > 0;
    }

    public int residual() {
        return capacity - flow;
    }

    public boolean hasResidual() {
        return residual() > 0;
    }

    public void reserve(int desiredFlow) {
        if (flow + desiredFlow > capacity) {
            throw new IllegalArgumentException("Reserve exceeds capacity!");
        }
        flow += desiredFlow;
    }

}


class Path<T> {

    Map<T, Edge<T>> edges;

    public Path() {
        this.edges = new HashMap<T, Edge<T>>();
    }

    public int minimumFlow() {
        int minimum = Integer.MAX_VALUE;
        for (var anyEdge: edges.values()) {
            if (minimum > anyEdge.residual()) {
                minimum = anyEdge.residual();
            }
        }
        return minimum;
    }

    public void add(Edge<T> givenEdge) {
        edges.put(givenEdge.end,
                  givenEdge);
    }


    public String between(T start, T end) {
        String path = end.toString();
        T current = end;
        while (edges.containsKey(current)) {
            var edge = edges.get(current);
            path = edge.start + "--(" + edge.flow + "/" + edge.capacity + ")->" + path;
            current = edge.start;
        }
        return path;
    }

}


public class Graph<T> {

    private Map<T, Map<T, Edge<T>>> vertices;


    public Graph() {
        this.vertices = new HashMap<T, Map<T, Edge<T>>>();
    }


    public List<Edge<T>> allEdges() {
        var edges = new LinkedList<Edge<T>>();
        for (var anyVertex: vertices.keySet()) {
            for (var eachEdge: edgesFrom(anyVertex)) {
                edges.add(eachEdge);
            }
        }
        return edges;
    }

    public List<Edge<T>> edgesFrom(T start) {
        return new ArrayList<Edge<T>>(vertices.get(start).values());
    }

    public Edge<T> findEdge(T start, T end) {
        return vertices.get(start).get(end);
    }


    public void createVertex(T vertex) {
        if (!vertices.containsKey(vertex)) {
            this.vertices.put(vertex, new HashMap<T, Edge<T>>());
        }
    }


    public int totalFlow() {
        int total = 0;
        for (var anyEdge: allEdges()) {
            total += anyEdge.flow;
        }
        return total;
    }


    public void connect(T start, T end, int capacity) {
        createVertex(start);
        createVertex(end);
        this.vertices.get(start).put(end, new Edge(start, end, capacity));
    }


    public Path<T> searchAugmentingPath(T source, T sink) {
        var path = new Path<T>();
        var visited = new HashSet<T>();
        var nextVertices = new LinkedList<T>();
        nextVertices.add(source);
        while (!nextVertices.isEmpty()) {
            var current = nextVertices.remove(0);
            visited.add(current);
            for(var eachEdge: edgesFrom(current)) {
                if (eachEdge.hasResidual()
                    && !visited.contains(eachEdge.end)) {
                    path.add(eachEdge);
                    nextVertices.add(eachEdge.end);
                    if (eachEdge.end == sink) {
                        return path;
                    }
                }
            }
        }
        return null;
    }

    public void augmentWith(Path<T> path) {
        int minimumFlow = path.minimumFlow();
        for (var anyEdge: path.edges.values()) {
            var edge = findEdge(anyEdge.start, anyEdge.end);
            edge.reserve(minimumFlow);
        }
    }


    public Graph<T> newZeroFlow() {
        var graph = new Graph();
        for (var eachEdge: allEdges()) {
            graph.connect(eachEdge.start, eachEdge.end, eachEdge.capacity);
        }
        return graph;
    }


    public Graph<T> maximumFlowBetween(T source, T sink) {
        var flow = newZeroFlow();
        var path = flow.searchAugmentingPath(source, sink);
        while (path != null) {
            flow.augmentWith(path);
            path = flow.searchAugmentingPath(source, sink);
        }
        return flow;
    }

    public void showFlow() {
        for (var anyVertex: vertices.keySet()) {
            for (var anyEdge: edgesFrom(anyVertex)) {
                if (anyEdge.hasFlow()) {
                    System.out.println(anyEdge.start + " -> " +
                                       anyEdge.end + " F=" + anyEdge.flow);
                }
            }
        }

    }


    public static void main(String[] args) {
        var graph = new Graph<String>();
        graph.connect("Bergen", "Alesund", 5);
        graph.connect("Alesund", "Trondheim", 12);
        graph.connect("Alesund", "Lillehammer", 12);
        graph.connect("Trondheim", "Lillehammer", 4);
        graph.connect("Lillehammer", "Hamar", 12);
        graph.connect("Bergen", "Kristiansand", 8);
        graph.connect("Kristiansand", "Oslo", 14);
        graph.connect("Oslo", "Bergen", 20);
        graph.connect("Oslo", "Lillehammer", 10);
        graph.connect("Oslo", "Hamar", 18);


        var maxFlow = graph.maximumFlowBetween("Bergen", "Hamar");

        System.out.println("Max flow:");
        maxFlow.showFlow();
    }

}

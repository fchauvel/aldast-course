import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

class Link {

    String source;
    String target;
    int weight;

    public Link(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

}



class PathSet {

    static class Entry {
        String predecessor;
        int totalDistance;

        public Entry(String predecessor, int distance) {
            this.predecessor = predecessor;
            this.totalDistance = distance;
        }

    }

    private Map<String, Entry> entries;


    public PathSet(Graph graph, String source) {
        this.entries = new HashMap<String, Entry>();
        for (var eachNode: graph.allNodes()) {
            var newEntry = new Entry(eachNode, Integer.MAX_VALUE);
            if (eachNode == source) {
                newEntry.totalDistance = 0;
            }

            this.entries.put(eachNode, newEntry);
        }
    }


    public void adjust(Link givenLink) {
        var oldDistance = shortestDistanceTo(givenLink.target);
        var newDistance = shortestDistanceTo(givenLink.source)
            + givenLink.weight;

        if (newDistance < oldDistance) {
            record(givenLink, newDistance);
        }
    }

    int shortestDistanceTo(String target) {
        return entries.get(target).totalDistance;
    }


    void record(Link givenLink, int newDistance) {
        var entry = entries.get(givenLink.target);
        if (entry == null) {
            entry = new Entry(givenLink.source, 0);
            entries.put(givenLink.target, entry);
        }
        entry.predecessor = givenLink.source;
        entry.totalDistance = newDistance;
    }


    String findBest(List<String> candidates) {
        String best = candidates.get(0);
        int minimumDistance = entries.get(best).totalDistance;
        for (var eachNode: entries.keySet()) {
            if (candidates.contains(eachNode)) {
                var eachEntry = entries.get(eachNode);
                if (eachEntry.totalDistance < minimumDistance) {
                    minimumDistance = eachEntry.totalDistance;
                    best = eachNode;
                }
            }
        }
        return best;
    }


    void formatPathTo(String target) {
        var entry = entries.get(target);
        if (entry == null) {
            System.out.println("Not reachable");
        }
        var path = target;
        while (entry.predecessor != null) {
            path = entry.predecessor + "->" + path;
            entry = entries.get(entry.predecessor);
        }
        System.out.println(path);
    }


}


public class Graph {

    private Map<String, List<Link>> nodes;

    public Graph() {
        this.nodes = new HashMap<String, List<Link>>();
    }

    public int nodeCount() {
        return this.nodes.size();
    }


    public Set<String> allNodes () {
        return this.nodes.keySet();
    }


    public int linkCount() {
        int linkCount = 0;
        for (var eachNode: nodes.keySet()){
            var outgoingLinks = nodes.get(eachNode);
            linkCount += outgoingLinks.size();
        }
        return linkCount;
    }

    public void connect(String source, String target, int weight) {
        var newLink = new Link(source, target, weight);
        var outgoingLinks = nodes.get(source);
        if (outgoingLinks == null) {
            outgoingLinks = new LinkedList<Link>();
            this.nodes.put(source, outgoingLinks);
        }
        outgoingLinks.add(newLink);
        if (!nodes.containsKey(target)) {
            nodes.put(target, new LinkedList<Link>());
        }
    }

    public void interconnect(String source, String target, int weight) {
        connect(source, target, weight);
        connect(target, source, weight);
    }

    public List<Link> outgoingLinksFrom(String givenSource) {
        if (nodes.containsKey(givenSource)) {
            return nodes.get(givenSource);
        }
        return new LinkedList<Link>();
    }

    public void printDepthFirst(String givenSource) {
        var visitedNodes = new HashSet<String>();
        var nodesToProcess = new Stack<String>();
        nodesToProcess.push(givenSource);
        while (!nodesToProcess.isEmpty()) {
            var currentNode = nodesToProcess.pop();
            if (visitedNodes.contains(currentNode)) continue;
            System.out.print(currentNode + " ");
            visitedNodes.add(currentNode);
            for (var eachLink: outgoingLinksFrom(currentNode)) {
                nodesToProcess.push(eachLink.target);
            }
        }
        System.out.println();
    }


    public void printBreadthFirst(String givenSource) {
        var visitedNodes = new HashSet<String>();
        var nodesToProcess = new LinkedList<String>();
        nodesToProcess.add(givenSource);
        while (!nodesToProcess.isEmpty()) {
            var currentNode = nodesToProcess.remove(0);
            if (visitedNodes.contains(currentNode)) continue;
            System.out.print(currentNode + " ");
            visitedNodes.add(currentNode);
            for (var eachLink: outgoingLinksFrom(currentNode)) {
                nodesToProcess.add(eachLink.target);
            }
        }
        System.out.println();
    }


    public void printClosestFirst(String givenSource) {
        var visitedNodes = new HashSet<String>();
        var nodesToProcess = new LinkedList<String>();
        nodesToProcess.add(givenSource);
        while (!nodesToProcess.isEmpty()) {
            var currentNode = nodesToProcess.remove(0);
            if (visitedNodes.contains(currentNode)) continue;
            System.out.print(currentNode + " ");
            visitedNodes.add(currentNode);
            for (var eachLink: outgoingLinksFrom(currentNode)) {
                nodesToProcess.add(eachLink.target);
            }
        }
        System.out.println();
    }


    public PathSet shortestPath(String source) {
        var paths = new PathSet(this, source);
        var visited = new HashSet<String>();
        var queue = new LinkedList();
        while (!queue.isEmpty()) {
            var node = paths.findBest(queue);
            if (!visited.contains(node)) {
                visited.add(node);
                for (var anyOutgoingLink: outgoingLinksFrom(node)) {
                    paths.adjust(anyOutgoingLink);
                    queue.add(anyOutgoingLink.target);
                }
            }
        }
        return paths;
    }


    public static void main(String[] args) {
        System.out.println("Ready!");
        var graph = new Graph();
        graph.interconnect("Aalesund", "Modle", 82);
        graph.interconnect("Modle", "Trondheim", 220);
        graph.interconnect("Roros", "Trondheim", 154);
        graph.interconnect("Hamar", "Roros", 260);
        graph.interconnect("Oppdal", "Hamar", 290);
        graph.interconnect("Oslo", "Oppdal", 400);
        graph.interconnect("Oppdal", "Aalesund", 290 );
        graph.interconnect("Aalesund", "Bergen", 425);

        if (graph.nodeCount() != 8) {
            System.out.println("Problem");
        }

        if (graph.linkCount() != 16) {
            System.out.println("Broken link count");
        }

        if (graph.outgoingLinksFrom("Aalesund").size() != 3) {
            System.out.println("Broken outgoing links");
        }

        graph.printDepthFirst("Aalesund");
        //
        graph.printBreadthFirst("Aalesund");

        var paths = graph.shortestPath("Oslo");
        paths.formatPathTo("Trondheim");

    }

}

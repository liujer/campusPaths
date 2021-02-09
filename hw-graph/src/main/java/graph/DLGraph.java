package graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Represents a directed labeled graph, where nodes are connected
// by one-way edges and have weights associated with them
// - No two nodes store the same data
// - There can be any number of edges between two nodes
// - No 2 edges with the same parent and child nodes will have the same edge label
/*
 * Abstraction function:
 *  dlGraph is a map that represents a directed labeled graph, where
 *  every key (String) represents a starting vertex, and every value (dlEdge)
 *  represents a list of destination vertices. Every edge between vertices have associated
 *  labels, which is contained in the dlEdge object
 * Rep. Invariant:
 *  Every key (start node) is not null and every list of edges associated with
 *  the keys are not null, and the edges in the list are not null.
 *  Additionally, the graph itself is not null

 */
public class DLGraph {
    // Fields
    private Map <String, List<DLEdge>> dlGraph;
    private final static boolean DEBUG = true;

    private class DLEdge {
        private final String dest;
        private final String label;

        public DLEdge(String dest, String label) {
            this.dest = dest;
            this.label = label;
        }

        public String getDest() {
            return dest;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * @spec.effects  Constructs an empty directed labeled graph.
     */
    public DLGraph() {
        dlGraph = new HashMap<>();
        checkRep();
    }

    /**
     * Adds a node to the graph
     * @param data Represents the data contained in the node
     * @return false if node already exists in graph, true if added to graph
     * @throws IllegalArgumentException if data == null
     * @spec.effects  a node with data is added to the graph
     */
    public boolean addNode(String data) {
        checkRep();
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (dlGraph.containsKey(data)) {
            return false;
        }
        dlGraph.put(data, new ArrayList<DLEdge>());
        checkRep();
        return true;
    }

    /**
     * Adds an edge connecting two nodes to the graph
     * @param start Represents the starting point of the edge
     * @param end Represents the destination of the edge, or the node
     *            that the edge is pointing to
     * @param label Represents the label associated with the edge
     * @spec.requires start and end must be existing nodes in the graph
     * and there is not already an identical edge existing
     * between start and end, label != null
     * @spec.effects An edge from start pointing to end is be added to the graph
     * with a corresponding label
     */
    public void addEdge(String start, String end, String label) {
        checkRep();
        List<DLEdge> temp = dlGraph.get(start);
        temp.add(new DLEdge(end, label));
        checkRep();
    }

    /**
     * Returns all nodes in the graph
     * @return list of all nodes contained in the graph
     */
    public List<String> listNodes() {
        checkRep();
        return new ArrayList<String>(dlGraph.keySet());
    }

    /**
     * Returns all children of a particular node
     * @param data Represents the data of the parent node
     * @return a list of nodes where there is an edge directed
     * from the parent node
     * @throws IllegalArgumentException if node does not exist in
     * the graph or data == null
     */
    public List<String> listChildren(String data)
    throws IllegalArgumentException {
        checkRep();
        if (!nodeExists(data) || data == null) {
            throw new IllegalArgumentException();
        }
        List<DLEdge> temp = dlGraph.get(data);
        List<String> result = new ArrayList<>();
        for (DLEdge edge : temp) {
            result.add(edge.getDest());
        }
        checkRep();
        return result;
    }

    /**
     * Checks whether or not a node exists in the graph
     * @param data Represents the data of the node to look for
     * @spec.requires data != null
     * @return true if node is found in the graph
     */
    public boolean nodeExists(String data) {
        checkRep();
        return dlGraph.containsKey(data);
    }

    /**
     * Checks whether or not a particular edge exists in the graph
     * @param start Represents the starting node of the edge
     * @param end Represents the destination node of the edge
     * @return true if edge exists between start and end
     * @spec.requires start and end must be nodes in the graph
     */
    public boolean edgeExists(String start, String end) {
        checkRep();
        List<DLEdge> temp = dlGraph.get(start);
        for (DLEdge edge : temp) {
            if (edge.getDest().equals(end)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the a list of labels of a given edge
     * @param start Represents the starting node of the edge
     * @param end Represents the destination node of the edge
     * @return label of edge corresponding to start and end as a String
     * @throws IllegalArgumentException if edge does not exist
     */
    public List<String> getLabel(String start, String end)
    throws IllegalArgumentException {
        checkRep();
        if (!edgeExists(start, end)) {
            throw new IllegalArgumentException("Edge does not exist");
        }
        List<String> result = new ArrayList<>();
        List<DLEdge> temp = dlGraph.get(start);
        for (DLEdge edge : temp) {
            if (edge.getDest().equals(end)) {
                result.add(edge.getLabel());
            }
        }
        checkRep();
        return result;
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        if (DEBUG) {
            assert (dlGraph != null);

            Set<String> keys = dlGraph.keySet();
            for (String start : keys) {
                assert (start != null) : "null starting node";
                List<DLEdge> temp = dlGraph.get(start);
                assert (temp != null) : "null list of edges of starting node";
                for (DLEdge edge : temp) {
                    assert (edge != null) : "null ending node";
                }
            }
        }
    }

}

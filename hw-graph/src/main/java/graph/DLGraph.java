package graph;
import java.util.*;

// Represents a directed labeled graph, where nodes are connected
// by one-way edges and have weights associated with them
// - No two nodes store the same data
// - There can be any number of edges between two nodes
// - No 2 edges with the same parent and child nodes will have the same edge label

public class DLGraph<N, E> {
    // N represents what data type the nodes will represent
    // E represents what data type a label between edges will represent
    // Fields
    private final Map <N, List<DLEdge>> dlgraph;
    private final static boolean DEBUG = false;

    private class DLEdge {
        private final N dest;
        private final E label;
        public DLEdge(N dest, E label) {
            this.dest = dest;
            this.label = label;
        }
        public N getDest() {
            return dest;
        }
        public E getLabel() {
            return label;
        }
    }
    /*
    Abstraction function:
        dlgraph represents a directed labeled graph, with nodes (strings)
        as the keys and lists of edges as the values. Every edge in the list
        contains a destination node and a corresponding label.
    Rep. Invariant:
        Nodes, list of edges, and edges themselves are not null.
        Additionally, there are no duplicate edges such that the
        start, end, and label are all the same.

    */

    /**
     * @spec.effects  Constructs an empty directed labeled graph.
     */
    public DLGraph() {
        dlgraph = new HashMap<>();
        checkRep();
    }

    /**
     * Adds a node to the graph
     * @param data Represents the data contained in the node
     * @return false if data is null or data already exists
     * in graph, true if data is inserted
     * @spec.effects  a node with data is added to the graph
     */
    public boolean addNode(N data) {
        checkRep();
        if (data == null || dlgraph.containsKey(data)) {
            return false;
        }
        dlgraph.put(data, new ArrayList<>());
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
     * and there is not already an identical edge existing between start and end.
     * Additionally, none of the parameters are null
     * @spec.effects An edge from start pointing to end is be added to the graph
     * with a corresponding label
     */
    public void addEdge(N start, N end, E label) {
        checkRep();
        List<DLEdge> temp = dlgraph.get(start);
        temp.add(new DLEdge(end, label));
        checkRep();
    }

    /**
     * Adds an edge connecting two nodes to the graph in both directions
     * @param nodeOne Represents the one of the nodes in the edge
     * @param nodeTwo Represents the other node in the edge
     * @param label Represents the label associated with the edge
     * @spec.requires start and end must be existing nodes in the graph
     * and there is not already an identical edge existing between start and end
     * such that the labels and start/end nodes are identical. Additionally,
     * none of the parameters are null.
     * @spec.effects An edge from start pointing to end and an edge
     * pointing from end to start is added to the graph with
     * a corresponding label
     */
    public void addUndirectedEdge(N nodeOne, N nodeTwo, E label) {
        checkRep();
        List<DLEdge> nodeOneEdges = dlgraph.get(nodeOne);
        List<DLEdge> nodeTwoEdges = dlgraph.get(nodeTwo);
        nodeOneEdges.add(new DLEdge(nodeTwo, label));
        nodeTwoEdges.add(new DLEdge(nodeOne, label));
        checkRep();
    }


    /**
     * Returns all nodes in the graph
     * @return list of nodes contained in the graph
     * in order of the node data
     */
    public List<N> listNodes() {
        checkRep();
        return new ArrayList<>(dlgraph.keySet());
    }

    /**
     * Returns all children of a particular node
     * @param data Represents the data of the parent node
     * @return a list of nodes where there is an edge directed
     * from the parent node in the graph
     * @spec.requires data exists in graph as a node and data
     * is not null
     */
    public List<N> listChildren(N data) {
        checkRep();
        List<N> result = new ArrayList<>();
        for (DLEdge edge : dlgraph.get(data)) {
            if (!result.contains(edge.getDest())) {
                result.add(edge.getDest());
            }
        }
        checkRep();
        return result;
    }

    /**
     * Checks whether or not a node exists in the graph
     * @param data Represents the data of the node to look for
     * @return true if node is found in the graph
     */
    public boolean nodeExists(N data) {
        checkRep();
        return dlgraph.containsKey(data);
    }

    /**
     * Checks whether or not a particular edge exists in the graph
     * @param start Represents the starting node of the edge
     * @param end Represents the destination node of the edge
     * @return true if edge exists between start and end
     * @spec.requires start and end must be nodes in the graph
     */
    public boolean edgeExists(N start, N end) {
        checkRep();
        for (DLEdge edge : dlgraph.get(start)) {
            if (edge.getDest().equals(end)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the label of a given edge
     * @param start Represents the starting node of the edge
     * @param end Represents the destination node of the edge
     * @return list of labels corresponding to start and end as a String
     * @spec.requires start and end must be nodes in the graph, and start
     * and end must not be null
     */
    public List<E> getLabels(N start, N end) {
        checkRep();
        List<E> result = new ArrayList<>();
        for (DLEdge edge : dlgraph.get(start)) {
            if (edge.getDest().equals(end)) {
                result.add(edge.getLabel());
            }
        }
        checkRep();
        return result;
    }

    private void checkRep() {
        if (DEBUG) {
            assert (dlgraph != null) : "graph is null";
            for (N start: dlgraph.keySet()) {
                assert (start != null) : "node is null";
                List<DLEdge> edges = dlgraph.get(start);
                assert (edges != null) : "list of edges in " + start + " is null";
                for (DLEdge edge: edges) {
                    assert (edge != null) : "edge is null";
                }
                for (int i = 0; i < edges.size() - 1; i++) {
                    for (int j = i + 1; j < edges.size(); j++) {
                        DLEdge init = edges.get(i);
                        DLEdge compare = edges.get(j);
                        if (init.getDest().equals(compare.getDest())) {
                            assert (!init.getLabel().equals(compare.getLabel())) : "duplicate edges";
                        }
                    }
                }
            }
        }

    }

}

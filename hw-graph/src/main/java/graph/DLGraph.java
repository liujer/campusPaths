package graph;
import java.util.List;

// Represents a directed labeled graph, where nodes are connected
// by one-way edges and have weights associated with them
// - No two nodes store the same data
// - There can be any number of edges between two nodes
// - No 2 edges with the same parent and child nodes will have the same edge label

public class DLGraph {
    // Fields

    /**
     * @spec.effects  Constructs an empty directed labeled graph.
     */
    public void DLGraph() {
        throw new RuntimeException("Not implemented yet");
    };

    /**
     * Adds a node to the graph
     * @param data Represents the data contained in the node
     * @throws IllegalArgumentException if node data already exists in graph
     * @spec.effects  a node with data is added to the graph
     */
    public void addNode(String data) {
        throw new RuntimeException("Not implemented yet");
    };

    /**
     * Adds an edge connecting two nodes to the graph
     * @param start Represents the starting point of the edge
     * @param end Represents the destination of the edge, or the node
     *            that the edge is pointing to
     * @param label Represents the label associated with the edge
     * @spec.requires start and end must be existing nodes in the graph
     * and there is not already an identical edge existing between start and end
     * @spec.effects An edge from start pointing to end is be added to the graph
     * with a corresponding label
     */
    public void addEdge(String start, String end, String label) {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Returns all nodes in the graph
     * @return list of nodes contained in the graph
     * in order of the node data
     */
    public List<String> listNodes() {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Returns all children of a particular node
     * @param data Represents the data of the parent node
     * @return a list of nodes where there is an edge directed
     * from the parent node in the graph in order of node data
     * @throws IllegalArgumentException if node does not exist in
     * the graph
     */
    public List<String> listChildren(String data) {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Checks whether or not a node exists in the graph
     * @param data Represents the data of the node to look for
     * @return true if node is found in the graph
     */
    public boolean nodeExists(String data) {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Returns the label of a given edge
     * @param start Represents the starting node of the edge
     * @param end Represents the destination node of the edge
     * @return label of edge corresponding to start and end as a Striing
     * @throws IllegalArgumentException if edge does not exist
     */
    public String getLabel(String start, String end) {
        throw new RuntimeException("Not implemented yet");
    }

}

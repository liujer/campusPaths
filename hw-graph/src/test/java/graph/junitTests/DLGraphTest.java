package graph.junitTests;

import graph.DLGraph;
import org.junit.Test;
import static org.junit.Assert.*;

public class DLGraphTest {

    // two node graph with one edge
    private DLGraph twoNodeGraph;

    public DLGraphTest() {
        twoNodeGraph = new DLGraph();
        twoNodeGraph.addNode("one");
        twoNodeGraph.addNode("two");
        twoNodeGraph.addEdge("one", "two", "e12");
    }
    @Test
    public void testEdgeExists() {
        assertTrue(twoNodeGraph.edgeExists("one", "two"));
    }

    @Test
    public void testGetLabel() {
        assertEquals("e12", twoNodeGraph.getLabel("one", "two"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMissingLabel() {
        twoNodeGraph.getLabel("two", "one");
    }

    @Test
    public void testNodeExists() {
        assertTrue(twoNodeGraph.nodeExists("one"));
        assertFalse(twoNodeGraph.nodeExists("One"));
        assertTrue(twoNodeGraph.nodeExists("two"));
        assertFalse(twoNodeGraph.nodeExists("Two"));
    }
}

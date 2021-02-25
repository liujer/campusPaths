package pathfinder;

import graph.DLGraph;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * Utility class for CampusMap for finding shortest path in a graph
 */
public class CampusMapUtility {

    // An abstraction function/rep invariant would go here

    /**
     * Returns the shortest path in a graph based on weight of each edge
     * @param graph represents the graph the path is to be
     * searched in with generic node type and Double edge weights/labels
     * @param start represents the starting point in the path
     * @param dest represents the destination in the path
     * @param <T> represents the data type the nodes will be in the graph
     * @spec.requires all weights (Doubles) are nonnegative
     * @return Path that represents shortest path from start to dest
     */
    public static <T> Path<T> dijkstra(DLGraph<T, Double> graph, T start, T dest) {
        Queue<Path<T>> active = new PriorityQueue<>(Comparator.comparingDouble(Path::getCost));
        Path<T> initialPath = new Path<>(start);
        active.add(initialPath);
        List<T> finished = new ArrayList<>();

        while (!active.isEmpty()) {
            // minPath is current lowest cost path
            Path<T> minPath = active.remove();
            T minDest = minPath.getEnd();

            if (minDest.equals(dest)) { // found path
                return minPath;
            }

            if (finished.contains(minDest)) { // already checked node
                continue;
            }

            // MinPath not found yet
            for (T edge : graph.listChildren(minDest)) {
                // add a path to test later (distance value to all child nodes)
                if (!finished.contains(edge)) {
                    List<Double> costs = graph.getLabels(minDest, edge);
                    Collections.sort(costs);
                    Path<T> newPath = minPath.extend(edge, costs.get(0));
                    active.add(newPath);
                }
            }
            // node is checked, add to finished
            finished.add(minDest);
        }
        // if loop terminates, a path was not found
        return null;
    }
}

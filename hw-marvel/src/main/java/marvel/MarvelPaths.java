package marvel;

import graph.DLGraph;
import java.util.*;

/**
 * MarvelPaths helps a client determine the shortest connection
 * between two heroes based on shared books from a file/list of
 * heroes and their corresponding books
 */
public class MarvelPaths {

    private final static String marvelFile = "marvel.tsv";

    // Abs. function and rep. invariant would go here
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DLGraph<String, String> marvelGraph = new DLGraph<>();
        MarvelPaths.loadIntoGraph(marvelGraph, marvelFile);

        System.out.println("Enter the starting point/hero name: ");
        String start = scanner.nextLine().toUpperCase();
        System.out.println("Enter the destination point/hero name: ");
        String end = scanner.nextLine().toUpperCase();

        if (marvelGraph.nodeExists(start) && marvelGraph.nodeExists(end)) {
            List<String> path = MarvelPaths.findPath(marvelGraph, start, end);
            System.out.println("Path from " + start + " to " + end + ":");
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));
                if (i < path.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        } else {
            if (!marvelGraph.nodeExists(start)) {
                System.out.println(start + " does not exist in marvel");
            }

            if (!marvelGraph.nodeExists(end)) {
                System.out.println(end + " does not exist in marvel");
            }
        }

    }
    /**
     * Helper method for loading contents of a file into a DLGraph
     * @param trackedBooks map of books -> list of corresponding heroes
     * @param elt element to be added to the map
     * @spec.effects  contents of elt are added to the map
     */
    public static void addCharacter(Map<String, List<String>> trackedBooks, MarvelModel elt) {
        if (trackedBooks.containsKey(elt.getBook())) {
            List<String> temp = trackedBooks.get(elt.getBook());
            temp.add(elt.getHero());
        } else {
            List<String> temp = new ArrayList<>();
            temp.add(elt.getHero());
            trackedBooks.put(elt.getBook(), temp);
        }
    }

    /**
     * Loads contents from a tsv file into a DLGraph
     * @param marvelGraph graph in which data is to be loaded into
     * @param fileName tsv file that contains hero/book data
     * @spec.requires marvelGraph != null, file contains two columns of data
     * labeled hero and book
     * @spec.effects contents in file will be put into the graph.
     * every hero with the same book as another hero will have edges connecting
     * both ways between the heroes with the label as the same book
     */
    public static void loadIntoGraph(DLGraph<String, String> marvelGraph, String fileName) {
        Iterator<MarvelModel> modelItr = MarvelParser.parseData(fileName);
        Map<String, List<String>> trackedBooks = new HashMap<>();
        while (modelItr.hasNext()) {
            MarvelModel elt = modelItr.next();
            marvelGraph.addNode(elt.getHero());
            addCharacter(trackedBooks, elt);

            List<String> chars = trackedBooks.get(elt.getBook());
            for (String character : chars) {
                if (!character.equals(elt.getHero())) {
                    marvelGraph.addNode(character);
                    marvelGraph.addUndirectedEdge(character, elt.getHero(), elt.getBook());
                }
            }
        }
    }

    /**
     * Finds the shortest path between start and end in a graph
     * @param graph Graph used to find path in
     * @param start Starting node name
     * @param dest Destination node name
     * @return a list of Strings/node names in order of visiting in
     * shortest path
     */
    public static List<String> findPath(DLGraph<String, String> graph, String start, String dest) {
        LinkedList<String> nodeQueue = new LinkedList<>();
        Map<String, List<String>> nodesToPaths = new HashMap<>();
        nodeQueue.add(start);
        List<String> firstPath = new ArrayList<>();
        firstPath.add(start);
        nodesToPaths.put(start, firstPath);
        while(!nodeQueue.isEmpty()) {
            String temp = nodeQueue.poll();
            if (temp.equals(dest)) {
                return nodesToPaths.get(temp);
            }
            List<String> edges = graph.listChildren(temp);
            Collections.sort(edges);
            for (String s : edges) {
                if (!nodesToPaths.containsKey(s)) {
                    List<String> newPath = new ArrayList<>(nodesToPaths.get(temp));
                    newPath.add(s);
                    nodesToPaths.put(s, newPath);
                    nodeQueue.add(s);
                }
            }
        }
        return null;
    }
}

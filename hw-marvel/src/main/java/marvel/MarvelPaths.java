package marvel;

import graph.DLGraph;
import java.util.*;

public class MarvelPaths {

    private static void addCharacter(Map<String, List<String>> trackedBooks, MarvelModel elt) {
        if (trackedBooks.containsKey(elt.getBook())) {
            List<String> temp = trackedBooks.get(elt.getBook());
            temp.add(elt.getHero());
        } else {
            List<String> temp = new ArrayList<>();
            temp.add(elt.getHero());
            trackedBooks.put(elt.getBook(), temp);
        }
    }
    public static void loadIntoGraph(DLGraph marvelGraph, String fileName) {
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

    public static List<String> findPath(DLGraph graph, String start, String dest) {
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

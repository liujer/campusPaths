/*
 * Copyright (C) 2021 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.scriptTestRunner;

import graph.DLGraph;
import pathfinder.CampusMap;
import pathfinder.CampusMapUtility;
import pathfinder.datastructures.*;


import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private Map<String, DLGraph<String, Double>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new MarvelTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests()
            throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch (command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new DLGraph<>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        DLGraph<String, Double> temp = graphs.get(graphName);
        if (temp.addNode(nodeName)) {
            output.println("added node " + nodeName + " to " + graphName);
        } else {
            output.println("Could not add node " + nodeName + " to " + graphName);
        }
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, Double.valueOf(edgeLabel));
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {
        DLGraph<String, Double> temp = graphs.get(graphName);
        temp.addEdge(parentName, childName, edgeLabel);

        output.println("added edge " + String.format("%.3f", edgeLabel) + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        DLGraph<String, Double> temp = graphs.get(graphName);
        List<String> result = temp.listNodes();
        Collections.sort(result);
        output.print("ListNodes of " + graphName + " output:");
        for (String s : result) {
            output.print(" " + s);
        }
        output.println();
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        DLGraph<String, Double> temp = graphs.get(graphName);
        List<String> result = temp.listChildren(parentName);
        Collections.sort(result);
        output.print("the children of " + parentName +" in " + graphName + " are:");
        for (String s : result) {
            List<Double> labels = temp.getLabels(parentName, s);
            Collections.sort(labels);
            for (Double label : labels) {
                String tempLabel = String.format("(%.3f)", label);
                output.print(" " + s + tempLabel);
            }
        }
        output.println();
    }

    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String node_a = arguments.get(1);
        String node_b = arguments.get(2);

        findPath(graphName, node_a, node_b);
    }

    private void findPath(String graphName, String node_a, String node_b) {
        DLGraph<String, Double> temp = graphs.get(graphName);
        if (temp.nodeExists(node_a) && temp.nodeExists(node_b)) {
            Path<String> result = CampusMapUtility.dijkstra(temp, node_a, node_b);
            output.println("path from " + node_a + " to " + node_b + ":");
            if (result == null) {
                output.println("no path found");
            } else {
                Iterator<Path<String>.Segment> itr = result.iterator();
                while (itr.hasNext()) {
                    Path<String>.Segment currSegment = itr.next();
                    String cost = String.format(" with weight %.3f", currSegment.getCost());
                    output.println(currSegment.getStart() + " to " + currSegment.getEnd()
                    + cost);
                }
                output.println("total cost: " + String.format("%.3f", result.getCost()));
            }
        } else {
            if (!temp.nodeExists(node_a)) {
                output.println("unknown node " + node_a);
            }
            if (!temp.nodeExists(node_b)) {
                output.println("unknown node " + node_b);
            }
        }


    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}

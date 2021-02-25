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

package pathfinder;

import graph.DLGraph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

/**
 * Represents a map of campus, including a graph of points on the map along
 * with their distances between points
 */
public class CampusMap implements ModelAPI {

    private final Map<String, CampusBuilding> shortToBuilding; // Maps abrev. names to their buildings
    private final DLGraph<Point, Double> campusGraph; // Maps points with Doubles as weights

    // File Names
    private final static String BUILDINGS_FILE = "campus_buildings.tsv";
    private final static String PATHS_FILE = "campus_paths.tsv";

    // Debug status
    private final static boolean DEBUG = false;

    /*
        Abstraction function:
        - campusGraph stores nodes as points with an X and Y coordinate
        (doubles), mapping to each other with doubles as labels/weights
        representing the distance between the two points
        i.e
            (1,2) -> (2, 1) Weight: 1.414214
            (1,2) -> (1, 2) Weight : 0.000
        - shortToBuilding maps abbreviated names (Strings) to their corresponding
        buildings (CampusBuilding objects).

        Rep. Invariant:
        - Distances are non-negative
        - Nodes/Edges in campusGraph are not null
        - Keys/Values in shortToBuilding are not null
        - ShortName in shortToBuilding matches short name in value pair


     */

    public CampusMap() {
        shortToBuilding = new HashMap<>();
        campusGraph = new DLGraph<>();

        List<CampusBuilding> allBuildings = CampusPathsParser.parseCampusBuildings(BUILDINGS_FILE);
        List<CampusPath> paths = CampusPathsParser.parseCampusPaths(PATHS_FILE);

        for (CampusBuilding building : allBuildings) {
            shortToBuilding.put(building.getShortName(), building);
        }

        for (CampusPath path : paths) {
            Point first = new Point(path.getX1(), path.getY1());
            Point second = new Point(path.getX2(), path.getY2());
            campusGraph.addNode(first);
            campusGraph.addNode(second);
            campusGraph.addEdge(first, second, path.getDistance());
        }
        checkRep();
    }

    @Override
    public boolean shortNameExists(String shortName) {
        checkRep();
        boolean contains = shortToBuilding.containsKey(shortName);
        checkRep();
        return contains;
    }

    @Override
    public String longNameForShort(String shortName) {
        checkRep();
        if (!shortNameExists(shortName)) {
            throw new IllegalArgumentException("Short name does not exist in map");
        }
        String longName = shortToBuilding.get(shortName).getLongName();
        checkRep();
        return longName;
    }

    @Override
    public Map<String, String> buildingNames() {
        checkRep();
        Map<String, String> result = new HashMap<>();
        for (String shortName : shortToBuilding.keySet()) {
            result.put(shortName, shortToBuilding.get(shortName).getLongName());
        }
        checkRep();
        return result;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        checkRep();
        if (startShortName == null || endShortName == null) {
            throw new IllegalArgumentException("null arguments");
        }

        if (!shortNameExists(startShortName) || !shortNameExists(endShortName)) {
            throw new IllegalArgumentException("not all names exist in map");
        }
        CampusBuilding startBuilding = shortToBuilding.get(startShortName);
        Point startPoint = new Point(startBuilding.getX(), startBuilding.getY());

        CampusBuilding destBuilding = shortToBuilding.get(endShortName);
        Point endPoint = new Point(destBuilding.getX(), destBuilding.getY());
        Path<Point> result = CampusMapUtility.dijkstra(campusGraph, startPoint, endPoint);
        checkRep();
        return result;

    }

    /**
     * Throws an exception if an assert is violated in representation
     */
    private void checkRep() {
        if (DEBUG) {
            for (Point parent : campusGraph.listNodes()) {
                assert (parent != null) : "Null node";
                for (Point child : campusGraph.listChildren(parent)) {
                    List<Double> labels = campusGraph.getLabels(parent, child);
                    for (Double dist : labels) {
                        assert (dist >= 0) : "Distance is negative";
                    }
                }
            }
            for (String shortName : shortToBuilding.keySet()) {
                assert (shortName != null) : "Null short name";
                CampusBuilding shortBuilding = shortToBuilding.get(shortName);
                assert (shortBuilding != null) : "Null building associated with short name";
                assert (shortName.equals(shortBuilding.getShortName())) :
                        "Names do not match in shortToBuilding";
            }
        }

    }


}

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

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import pathfinder.CampusMap;
import pathfinder.datastructures.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.lang.reflect.Type;

import static spark.Spark.*;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // TODO: Create all the Spark Java routes you need here.
        CampusMap campusMap = new CampusMap();
        Gson gson = new Gson();
        Type pathType = new TypeToken<Path<Point>>() {}.getType();
        String buildingNames = gson.toJson(campusMap.buildingNames());


        Spark.get("/findPath", (request, response) -> {
            String start = request.queryParams("start");
            String dest = request. queryParams("dest");
            try {
                Path<Point> shortestPath = campusMap.findShortestPath(start, dest);
                return gson.toJson(shortestPath, pathType);
            } catch (IllegalArgumentException e) {
                Spark.halt(400, e.getMessage());
            }

            return null;
        });

        Spark.get("/", (request, response) -> buildingNames);


    }

}

package fr.li212.codingame.tan.application;

import fr.li212.codingame.tan.ia.FindRoute;
import fr.li212.codingame.tan.ia.models.GeographicCoordinates;
import fr.li212.codingame.tan.ia.models.RouteToFind;
import fr.li212.codingame.tan.ia.models.Stop;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TestApplication {
    private final static String START_POINT = "StopArea:EINS";
    private final static String END_POINT = "StopArea:GALH";

    private final static File STOPS_FILE = new File("src/test/resources/stops.csv");
    private final static File LINKS_FILE = new File("src/test/resources/links");

    public static void main(final String[] args) throws IOException {
        final Map<String, Stop> allStops = new HashMap<>();
        List<String> stopLines = FileUtils.readLines(STOPS_FILE, "UTF-8");

        stopLines.forEach(stopName -> {
            final String[] splitLine = stopName.split(",");
            final String stopIdentifier = splitLine[0];
            final Stop currentStop = new Stop(
                    stopIdentifier,
                    splitLine[1].replace("\"", " ").trim(),
                    new GeographicCoordinates(
                            Double.parseDouble(splitLine[3]),
                            Double.parseDouble(splitLine[4])
                    )
            );
            allStops.put(stopIdentifier, currentStop);
        });

        final Map<Stop, Set<Stop>> routesByStop = new HashMap<>();
        List<String> linksLines = FileUtils.readLines(LINKS_FILE, "UTF-8");
        linksLines.forEach(route -> {
            final Stop startOfRoute = allStops.get("StopArea:" + route.split(" ")[0]);
            final Stop endOfRoute = allStops.get("StopArea:" + route.split(" ")[1]);
            routesByStop.putIfAbsent(startOfRoute, new HashSet<>());
            routesByStop.get(startOfRoute).add(endOfRoute);
        });

        routesByStop.forEach(Stop::populateNeighbours);
        final Stop goal = allStops.get(END_POINT);
        allStops.values().forEach(stop -> stop.populateGoal(goal));

        final RouteToFind routeToFind = new RouteToFind(allStops.get(START_POINT), goal);

        final FindRoute findRoute = new FindRoute();
        final List<Stop> result = findRoute.find(routeToFind);

        if (result.isEmpty()) {
            System.out.println("IMPOSSIBLE");
        } else {
            result.forEach(stop -> System.out.println(stop.getCompleteName()));
        }
    }
}

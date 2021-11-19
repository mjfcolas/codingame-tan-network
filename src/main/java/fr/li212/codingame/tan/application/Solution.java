package fr.li212.codingame.tan.application;

import fr.li212.codingame.tan.ia.FindRoute;
import fr.li212.codingame.tan.ia.models.GeographicCoordinates;
import fr.li212.codingame.tan.ia.models.RouteToFind;
import fr.li212.codingame.tan.ia.models.Stop;

import java.util.*;

class Solution {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final String startPoint = in.next();
        final String endPoint = in.next();
        final int N = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        final Map<String, Stop> allStops = new HashMap<>();
        for (int i = 0; i < N; i++) {
            final String stopName = in.nextLine();
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
        }
        final int M = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        final Map<Stop, Set<Stop>> routesByStop = new HashMap<>();
        for (int i = 0; i < M; i++) {
            final String route = in.nextLine();
            final Stop startOfRoute = allStops.get(route.split(" ")[0]);
            final Stop endOfRoute = allStops.get(route.split(" ")[1]);
            routesByStop.putIfAbsent(startOfRoute, new HashSet<>());
            routesByStop.get(startOfRoute).add(endOfRoute);
        }
        routesByStop.forEach(Stop::populateNeighbours);
        final Stop goal = allStops.get(endPoint);
        allStops.values().forEach(stop -> stop.populateGoal(goal));

        final RouteToFind routeToFind = new RouteToFind(allStops.get(startPoint), goal);

        final FindRoute findRoute = new FindRoute();
        final List<Stop> result = findRoute.find(routeToFind);

        if (result.isEmpty()) {
            System.out.println("IMPOSSIBLE");
        } else {
            result.forEach(stop -> System.out.println(stop.getCompleteName()));
        }
    }
}

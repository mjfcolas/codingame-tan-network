package fr.li212.codingame.tan.ia;

import fr.li212.codingame.tan.ia.models.RouteToFind;
import fr.li212.codingame.tan.ia.models.Stop;

import java.util.*;

public class FindRoute {

    public List<Stop> find(final RouteToFind routeToFind) {
        final Stop startStop = routeToFind.getStart();
        final Stop endStop = routeToFind.getEnd();
        final Queue<Stop> openQueue = new PriorityQueue<>();
        startStop.setDistanceFromStartToStop(0D);
        openQueue.add(startStop);
        startStop.setClosed();

        while (!openQueue.isEmpty()) {
            Stop currentNode = openQueue.remove();
            if (currentNode.equals(endStop)) {
                return computePath(currentNode);
            }
            final Collection<Stop> neighbours = currentNode.getNeighbours();
            for (Stop neighbour : neighbours) {
                this.manageNeighbour(neighbour, currentNode, openQueue);
            }

        }
        return Collections.emptyList();
    }

    private void manageNeighbour(final Stop neighbour, final Stop currentNode, final Queue<Stop> openQueue) {
        final double predictedDistanceToStartNode = currentNode.getDistanceFromStartToStop() + currentNode.getCoordinates().distance(neighbour.getCoordinates());
        if (!neighbour.isClosed() || (neighbour.getDistanceFromStartToStop() > predictedDistanceToStartNode)) {
            neighbour.setPredecessor(currentNode);
            neighbour.setDistanceFromStartToStop(predictedDistanceToStartNode);
            neighbour.setClosed();
            openQueue.add(neighbour);
        }
    }

    private List<Stop> computePath(final Stop endStop) {
        final List<Stop> path = new ArrayList<>();
        path.add(endStop);
        Stop currentNode = endStop;
        while (currentNode.getPredecessor() != null) {
            currentNode = currentNode.getPredecessor();
            path.add(currentNode);
        }
        Collections.reverse(path);
        return path;
    }
}

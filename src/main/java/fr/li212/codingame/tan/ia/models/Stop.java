package fr.li212.codingame.tan.ia.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Stop implements Comparable<Stop> {

    private final String id;
    private final String completeName;
    private final GeographicCoordinates coordinates;
    private final Set<Stop> neighbours;
    private Double distanceFromStartToStop = null;
    private Stop goal;
    private boolean isClosed = false;
    private Stop predecessor;

    public Stop(final String id, final String completeName, final GeographicCoordinates coordinates) {
        this.id = id;
        this.completeName = completeName;
        this.coordinates = coordinates;
        this.neighbours = new HashSet<>();
    }

    public void populateNeighbours(final Collection<Stop> neighbours) {
        this.neighbours.addAll(neighbours);
    }

    public double getBaseHeuristic() {
        return goal.getCoordinates().distance(this.coordinates);
    }

    public void populateGoal(final Stop goal) {
        this.goal = goal;
    }

    public double getHeuristic() {
        if (distanceFromStartToStop == null) {
            throw new IllegalStateException("No cost defined for this node. Impossible to compute its heuristic");
        }
        return this.getBaseHeuristic() + distanceFromStartToStop;
    }

    public String getCompleteName() {
        return completeName;
    }

    public GeographicCoordinates getCoordinates() {
        return coordinates;
    }

    public Set<Stop> getNeighbours() {
        return neighbours;
    }

    public void setPredecessor(final Stop predecessor) {
        this.predecessor = predecessor;
    }

    public Stop getPredecessor() {
        return predecessor;
    }


    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed() {
        isClosed = true;
    }

    public void setDistanceFromStartToStop(final Double distanceFromStartToStop) {
        this.distanceFromStartToStop = distanceFromStartToStop;
    }

    public Double getDistanceFromStartToStop() {
        return distanceFromStartToStop;
    }

    @Override
    public int compareTo(final Stop toCompare) {
        return Double.compare(this.getHeuristic(), toCompare.getHeuristic());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Stop stop = (Stop) o;
        return Objects.equals(id, stop.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Stop{" +
                "id='" + id + '\'' +
                ", completeName='" + completeName + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}

package fr.li212.codingame.tan.ia.models;

public class RouteToFind {
    private final Stop start;
    private final Stop end;

    public RouteToFind(final Stop start, final Stop end) {
        this.start = start;
        this.end = end;
    }

    public Stop getStart() {
        return start;
    }

    public Stop getEnd() {
        return end;
    }
}

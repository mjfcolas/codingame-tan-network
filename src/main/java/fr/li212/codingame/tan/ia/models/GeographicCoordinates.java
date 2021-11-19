package fr.li212.codingame.tan.ia.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GeographicCoordinates {

    private final Map<GeographicCoordinates, Double> distanceMemoizedMap = new HashMap<>();

    private final double latitude;
    private final double longitude;

    public GeographicCoordinates(final double latitude, final double longitude) {
        this.latitude = Math.toRadians(latitude);
        this.longitude = Math.toRadians(longitude);
    }

    public final double distance(final GeographicCoordinates target) {
        if(distanceMemoizedMap.containsKey(target)){
            return distanceMemoizedMap.get(target);
        }
        final double x = (target.longitude - this.longitude) * Math.cos(0.5 * (target.latitude + this.latitude));
        final double y = target.latitude - this.latitude;
        final double result = Math.sqrt(x * x + y * y) * 6371;
        distanceMemoizedMap.put(target, result);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GeographicCoordinates that = (GeographicCoordinates) o;
        return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "GeographicCoordinates{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

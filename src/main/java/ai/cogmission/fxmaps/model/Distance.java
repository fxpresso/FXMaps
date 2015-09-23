package ai.cogmission.fxmaps.model;


/**
 * The distance component for Directions API results.
 * 
 * @author cogmission
 *
 */
public class Distance {
    private com.google.maps.model.Distance distance;
    
    /**
     * Constructs a new Distance
     * @param distance
     */
    public Distance(com.google.maps.model.Distance distance) {
        this.distance = distance;
    }
    
    /**
     * This is the numeric distance, always in meters. This is intended to be used only in
     * algorithmic situations, e.g. sorting results by some user specified metric.
     */
    public long getMeters() {
        return distance.inMeters;
    }
    
    /**
     * This is the human friendly distance. This is rounded and in an appropriate unit for the
     * request. The units can be overridden with a request parameter.
     */
    public String getHumanReadable() {
        return distance.humanReadable;
    }
    
    @Override
    public String toString() {
        return distance.humanReadable;
    }
}

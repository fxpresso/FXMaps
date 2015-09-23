package ai.cogmission.fxmaps.model;


/**
 *  Abstraction of a point along a given route, along with
 *  a visualization of that point in the form of a {@link Marker}
 *  
 * @author cogmission
 *
 */
public class Waypoint {
    private LatLon latLon;
    private Marker marker;
    
    
    /**
     * Constructs a new {@code Waypoint}
     * @param latlon    latitude/longitude object
     * @param marker    map pointer
     */
    public Waypoint(LatLon latlon, Marker marker) {
        this.latLon = latlon;
        this.marker = marker;
    }
    
    /**
     * Returns the {@link LatLon} object.
     * @return a LatLon
     */
    public LatLon getLatLon() {
        return latLon;
    }
    
    /**
     * Returns a {@link Marker}
     * @return a Marker
     */
    public Marker getMarker() {
        return marker;
    }
    
}

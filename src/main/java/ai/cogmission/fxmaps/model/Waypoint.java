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
    
    private Polyline connection;
    
    
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
    
    /**
     * Adds the specified {@link Polyline} which connects 
     * (The line that proceeds this waypoint) this waypoint.
     * 
     * @param line
     */
    public void setConnection(Polyline line) {
        this.connection = line;
    }
    
    /**
     * Returns the {@link Polyline} which proceed this waypoint.
     * @return
     */
    public Polyline getConnection() {
        return connection;
    }
    
}

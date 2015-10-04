package ai.cogmission.fxmaps.model;


/**
 *  Abstraction of a point along a given route, along with
 *  a visualization of that point in the form of a {@link Marker}
 *  
 * @author cogmission
 *
 */
public class Waypoint implements MapObject {
    protected LatLon latLon;
    protected Marker marker;
    
    protected Polyline connection;
    
    
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
    
    /**
     * Overridden to return null;
     */
    @Override
    public com.lynden.gmapsfx.javascript.object.Marker convert() {
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((connection == null) ? 0 : connection.hashCode());
        result = prime * result + ((latLon == null) ? 0 : latLon.hashCode());
        result = prime * result + ((marker == null) ? 0 : marker.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        Waypoint other = (Waypoint)obj;
        if(connection == null) {
            if(other.connection != null)
                return false;
        } else if(!connection.equals(other.connection))
            return false;
        if(latLon == null) {
            if(other.latLon != null)
                return false;
        } else if(!latLon.equals(other.latLon))
            return false;
        if(marker == null) {
            if(other.marker != null)
                return false;
        } else if(!marker.equals(other.marker))
            return false;
        return true;
    }
    
}

package ai.cogmission.fxmaps.xml;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;


public class GPXPersistentMap {
    @SerializedName("trk")
    protected List<GPXTrack> tracks = new ArrayList<>();
    
    @SerializedName("rte")
    protected List<GPXRoute> routes = new ArrayList<>();
    
    @SerializedName("wpt")
    protected List<GPXWaypoint> waypoints = new ArrayList<>();
    
    @SerializedName("metadata")
    protected GPXMetaData metadata;
    
    /** Map name */
    protected String name;
    
    
    /**
     * Returns a list of {@link GPXTrack}s
     * @return  a list of {@link GPXTrack}s
     */
    public List<GPXTrack> getTracks() {
        return tracks;
    }
    
    /**
     * Returns a list of {@link GPXRoute}s
     * @return  a list of {@link GPXRoute}s
     */
    public List<GPXRoute> getRoutes() {
        return routes;
    }
    
    /**
     * Returns a list of {@link GPXWaypoint}s
     * @return  a list of {@link GPXWaypoint}s
     */
    public List<GPXWaypoint> getWaypoints() {
        return waypoints;
    }
    
    /**
     * Returns the map name
     * @return  the map name
     */
    public String getName() {
        if(metadata != null) {
            return this.name = metadata.name;
        }
        
        return "";
    }
}

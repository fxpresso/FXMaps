package ai.cogmission.fxmaps.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstraction of a given path which connects a series of {@link Waypoint}s.
 * 
 * @author cogmission
 * @see Waypoint
 */
public class Route extends ArrayList<Waypoint> {
    private static final long serialVersionUID = 1L;

    /** Constructs a new {@code Route} */
    public Route() {}
    
    /**
     * Returns the origin (starting) {@link Waypoint} or
     * null if this {@code Route} is empty. 
     * 
     * @return  the starting {@link Waypoint}
     */
    public Waypoint getOrigin() {
        if(size() < 1) return null;
        
        return get(0);
    }
    
    /**
     * Returns the destination (end point) {@link Waypoint} or
     * null if this {@code Route} is empty. Note, the returned
     * value may be the same as the origin if this route only 
     * contains 1 item.
     * 
     * @return  the end point {@link Waypoint}
     */
    public Waypoint getDestination() {
        if(size() < 1) return null;
        
        return get(size() - 1);
    }
    
    /**
     * Returns the interim {@link Waypoint}s of a given route. These
     * are defined as the waypoints which are not the origin nor 
     * destination waypoints.
     *  
     * @return
     */
    public List<Waypoint> getWaypoints() {
        if(size() < 2) return Collections.emptyList();
        
        List<Waypoint> retVal = new ArrayList<>(this);
        retVal.remove(0);
        retVal.remove(size() - 1);
        return retVal;
    }
}

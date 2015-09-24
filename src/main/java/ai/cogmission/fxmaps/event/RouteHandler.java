package ai.cogmission.fxmaps.event;

import ai.cogmission.fxmaps.model.Route;

/**
 * A handler to be installed to manage the activities surrounding
 * the adding of a {@link Route}.
 * 
 * @author cogmission
 */
public interface RouteHandler {
    /**
     * Called when a {@link Route} has been added to the {@link Map}
     * @param r     the added route
     */
    public void routeAdded(Route r);
    /**
     * Called when a {@link Route} has been removed from the {@link Map}
     * @param r     the route that was removed
     */
    public void routeRemoved(Route r);
    /**
     * Called when a {@link Waypoint} has been added to the specified {@link Route}
     * @param r     the {@code Route} to which a {@code Waypoint} was added.
     */
    public void waypointAdded(Route r);
    /**
     * Called when a {@link Waypoint} has been removed from the specified {@link Route}
     * 
     * @param r     the {@code Route} from which a {@code Waypoint} was removed. 
     */
    public void waypointRemoved(Route r);
}

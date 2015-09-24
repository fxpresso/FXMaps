package ai.cogmission.fxmaps.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class DirectionsRoute {
    private com.google.maps.model.DirectionsRoute route;
    
    public DirectionsRoute(com.google.maps.model.DirectionsRoute route) {
        this.route = route;
    }
    
    /**
     * Returns a short textual description for the route, suitable for naming and
     * disambiguating the route from alternatives.
     * 
     * @return  the summary
     */
    public String getSummary() {
        return route.summary;
    }
    
    /**
     * Returns information about a leg of the route, between two locations within the
     * given route. A separate leg will be present for each waypoint or destination specified. (A
     * route with no waypoints will contain exactly one leg within the legs array.)
     * 
     * @return  the list of {@link DirectionsLeg}s
     */
    public List<DirectionsLeg> getLegs() {
        return Arrays.stream(route.legs).map(l -> { return new DirectionsLeg(l); }).collect(Collectors.toList());
    }
    
    /**
     * Returns an array indicating the order of any waypoints in the
     * calculated route. This waypoints may be reordered if the request was passed
     * {@code optimize:true} within its {@code waypoints} parameter.
     *  
     * @return
     */
    public int[] getWaypointOrder() {
        return route.waypointOrder;
    }
    
    /**
     * Returns an object holding an array of encoded points that represent
     * an approximate (smoothed) path of the resulting directions.
     * 
     * @return  the smoothed path
     */
    public EncodedPolyline getOverviewPolyline() {
        return new EncodedPolyline(route.overviewPolyline);
    }
    
    /**
     * Returns the copyrights text to be displayed for this route. You must
     * handle and display this information yourself.
     * 
     * @return  a copyrights text
     */
    public String getCopyrights() {
        return route.copyrights;
    }
    
    /**
     * Returns information about the fare (that is, the ticket costs) on this route.
     * This property is only returned for transit directions, and only for routes where fare
     * information is available for all transit legs.
     * 
     * @return  fare information
     */
    public Fare getFare() {
        return new Fare(route.fare);
    }
    
    /**
     * an array of warnings to be displayed when showing these directions.
     * You must handle and display these warnings yourself.
     * 
     * @return  an array of warnings
     */
    public String[] getWarning() {
        return route.warnings;
    }
}
    
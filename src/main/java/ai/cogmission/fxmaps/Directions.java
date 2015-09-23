package ai.cogmission.fxmaps;

import java.util.stream.Collectors;

import ai.cogmission.fxmaps.model.DirectionsRoute;
import ai.cogmission.fxmaps.model.LatLon;
import ai.cogmission.fxmaps.model.Route;
import ai.cogmission.fxmaps.model.Waypoint;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;

/**
 * 
 * @author cogmission
 *
 */
public class Directions {
    DirectionsApiRequest request;
    
    /**
     * Creates a new {@code Directions} object instance.
     * 
     * @param key   the {@link ApiKey} to use with the application.
     * @throws  IllegalArgumentException when api key presents a problem.
     */
    private Directions(ApiKey key) {
        try {
            request = DirectionsApi.newRequest(key.context());
        }catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    /**
     * Creates and returns a new {@code Directions} object.
     * 
     * @param key   the {@link ApiKey} to use
     * @return  a new {@code Directions}
     * @throws IllegalArgumentException when api key presents a problem.
     */
    public static Directions get(ApiKey key) {
        return new Directions(key);
    }
    
    /**
     * Sets the origin lat/long
     * 
     * @param latLon    the {@link LatLon}
     * 
     * @return  this Directions.
     */
    public Directions origin(LatLon latLon) {
        request.origin(latLon.toLatLng());
        return this;
    }
    
    /**
     * Sets the destination lat/long
     * 
     * @param latLon    the {@link LatLon}
     * 
     * @return  this Directions.
     */
    public Directions destination(LatLon latLon) {
        request.destination(latLon.toLatLng());
        return this;
    }
    
    /**
     * Sets the interim {@link Waypoint}s.
     * 
     * @param route the {@link Route} containing the {@link Waypoint}s.
     * @return  this Directions.
     */
    public Directions waypoints(Route route) {
        if(route.getWaypoints().isEmpty()) return this;
        
        String[] waypoints = route.getWaypoints().stream()
            .map(wp -> wp.getLatLon().toLatLng().toString())
            .collect(Collectors.toList())
            .toArray(new String[0]);
        
        request.waypoints(waypoints);
        return this;
    }
    
    /**
     * Returns a {@link DirectionsRoute} object which contains a route
     * which adheres to a given system of roads or highways.
     * 
     * @return  a {@link DirectionsRoute} object
     * @throws Exception    if the route is not obtainable for some reason.
     */
    public DirectionsRoute await() throws Exception {
        com.google.maps.model.DirectionsRoute[] r = request.await();
        DirectionsRoute route = new DirectionsRoute(r[0]);
        
        return route;
    }
}

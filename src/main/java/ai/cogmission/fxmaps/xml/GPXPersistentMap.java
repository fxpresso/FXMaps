package ai.cogmission.fxmaps.xml;

import java.util.ArrayList;
import java.util.List;

import ai.cogmission.fxmaps.model.LatLon;
import ai.cogmission.fxmaps.model.Marker;
import ai.cogmission.fxmaps.model.MarkerOptions;
import ai.cogmission.fxmaps.model.MarkerType;
import ai.cogmission.fxmaps.model.PersistentMap;
import ai.cogmission.fxmaps.model.Polyline;
import ai.cogmission.fxmaps.model.PolylineOptions;
import ai.cogmission.fxmaps.model.Route;
import ai.cogmission.fxmaps.model.Waypoint;
import ai.cogmission.fxmaps.ui.MapPane;

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
    
    public static PersistentMap asFXMap(GPXPersistentMap gpxMap, GPXType renderType) {
        PersistentMap retVal = null;
        
        if(gpxMap == null || renderType == null) {
            throw new IllegalArgumentException(gpxMap == null ? "GPXPersistentMap was null." : "GPXType was null");
        }
            
        switch(renderType) {
            case TRACK: {
                validateTracks(gpxMap);
                
                retVal = new PersistentMap(gpxMap.getName());
                
                for(GPXTrack track : gpxMap.getTracks()) {
                    Route route = new Route(track.name);
                    for(GPXTrackPoint point : track.trackSegment.trackPoints) {
                        Waypoint wp = createWaypoint(new LatLon(point.lat, point.lon));
                        route.addWaypoint(wp);
                        if(route.size() > 1) {
                            connectWaypoints(route);
                        }
                    }
                    retVal.addRoute(route);
                }
                retVal.createUnderlying();
                return retVal;
            }
            case ROUTE: {
                validateRoutes(gpxMap);
                
                retVal = new PersistentMap(gpxMap.getName());
                
                for(GPXRoute gpxRoute : gpxMap.getRoutes()) {
                    Route route = new Route(gpxRoute.name);
                    for(GPXRoutePoint point : gpxRoute.routePoints) {
                        Waypoint wp = createWaypoint(new LatLon(point.lat, point.lon));
                        route.addWaypoint(wp);
                        if(route.size() > 1) {
                            connectWaypoints(route);
                        }
                    }
                    retVal.addRoute(route);
                }
                retVal.createUnderlying();
                return retVal;
            }
            case WAYPOINT: {
                validateWaypoints(gpxMap);
                
                retVal = new PersistentMap(gpxMap.getName());
                
                Route route = new Route(gpxMap.getName() + "_" + "WaypointRoute");
                for(GPXWaypoint point : gpxMap.getWaypoints()) {
                    Waypoint wp = createWaypoint(new LatLon(point.lat, point.lon));
                    route.addWaypoint(wp);
                    if(route.size() > 1) {
                        connectWaypoints(route);
                    }
                }
                retVal.addRoute(route);
                retVal.createUnderlying();
                return retVal;
            }
            default: {
                return null;
            }
        }
    }
    
    private static Waypoint createWaypoint(LatLon latLon) {
        MarkerOptions opts = new MarkerOptions()
            .position(latLon)
            .title("Waypoint")
            .icon(MarkerType.GREEN.nextPath())
            .visible(true);
        
        return new Waypoint(latLon, new Marker(opts));
    }
    
    private static void connectWaypoints(Route currentRoute) {
        Waypoint lastWaypoint = null;
        
        List<LatLon> l = new ArrayList<>();
        l.add(currentRoute.getWaypoint(currentRoute.size() - 2).getLatLon());
        l.add((lastWaypoint = currentRoute.getWaypoint(currentRoute.size() - 1)).getLatLon());
        
        Polyline poly = new Polyline(PolylineOptions.copy(MapPane.getDefaultPolylineOptions()).path(l));
     
        lastWaypoint.setConnection(poly);
     
        currentRoute.addLine(poly);
    }
    
    private static void validateTracks(GPXPersistentMap gpxMap) {
        if(gpxMap.getTracks() == null || gpxMap.getTracks().size() < 1 ||
            gpxMap.getTracks().get(0).trackSegment == null || 
                gpxMap.getTracks().get(0).trackSegment.trackPoints == null ||
                    gpxMap.getTracks().get(0).trackSegment.trackPoints.size() < 1) {
            throw new IllegalStateException("No track points to add to route.");
        }
    }
    
    private static void validateWaypoints(GPXPersistentMap gpxMap) {
        if(gpxMap.getWaypoints() == null || gpxMap.getWaypoints().size() < 1) {
            throw new IllegalStateException("No waypoints to add to route.");
        }
    }
    
    private static void validateRoutes(GPXPersistentMap gpxMap) {
        if(gpxMap.getRoutes() == null || gpxMap.getRoutes().size() < 1 ||
            gpxMap.getRoutes().get(0).routePoints == null || 
                gpxMap.getRoutes().get(0).routePoints.size() < 1) {
            throw new IllegalStateException("No route points to add to route.");
        }
    }
}

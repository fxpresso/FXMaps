package ai.cogmission.fxmaps.ui;

import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import ai.cogmission.fxmaps.event.MapEventHandler;
import ai.cogmission.fxmaps.event.MapEventType;
import ai.cogmission.fxmaps.event.MapInitializedListener;
import ai.cogmission.fxmaps.event.MapReadyListener;
import ai.cogmission.fxmaps.model.DirectionsRoute;
import ai.cogmission.fxmaps.model.LatLon;
import ai.cogmission.fxmaps.model.Location;
import ai.cogmission.fxmaps.model.Locator;
import ai.cogmission.fxmaps.model.MapObject;
import ai.cogmission.fxmaps.model.MapOptions;
import ai.cogmission.fxmaps.model.MapType;
import ai.cogmission.fxmaps.model.Marker;
import ai.cogmission.fxmaps.model.MarkerOptions;
import ai.cogmission.fxmaps.model.MarkerType;
import ai.cogmission.fxmaps.model.Route;
import ai.cogmission.fxmaps.model.RouteStore;
import ai.cogmission.fxmaps.model.Waypoint;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;

/**
 * Undecorated {@link Pane} extension which is specialized to contain a 
 * map view and an optional {@link DirectionsPane}.
 * 
 * @author cogmission
 *
 */
public class MapPane extends BorderPane implements Map {
    private static final MapOptions DEFAULT_OPTIONS = getDefaultOptions();
    private final MapEventHandler DEFAULT_MAPEVENT_HANDLER = getDefaultMapEventHandler();
    private boolean defaultMapEventHandlerInstalled = true;
    
    protected GoogleMapView mapComponent;
    protected GoogleMap googleMap;
    
    protected MapOptions userOptions;
    
    protected DirectionsPane directionsPane;
    
    protected Route currentRoute;
    protected RouteStore routeStore;
    
    protected boolean isRouteSimMode;
    
    /**
     * Constructs a new {@code MapPane}
     */
    public MapPane() {
        setPrefWidth(1000);
        setPrefHeight(780);
        
        mapComponent = new GoogleMapView(); 
        setCenter(mapComponent);
        
        directionsPane = new DirectionsPane();
        directionsPane.setPrefWidth(200);
        setDirectionsVisible(false);
        
        routeStore = new RouteStore();
    }
    
    /**
     * Creates and initializes child components to prepare the map
     * for immediate use. when the {@link Map} is initialized it is 
     * given two call backs which when called, indicate that the map
     * is ready for use. 
     * 
     * Before this method is called, any desired {@link MapOptions} must
     * be set on the map before calling {@code #initialize()} 
     */
    @Override
    public void initialize() {
        mapComponent.addMapInializedListener(this);
    }
    
    /**
     * Specifies the {@link MapOptions} to use. <em>Note</em> this must
     * be set prior to calling {@link #initialize()}
     * 
     * @param mapOptions    the {@code MapOptions} to use.
     */
    @Override
    public void setMapOptions(MapOptions options) {
        this.userOptions = options;
    }
    
    /**
     * Makes the right {@link DirectionsPane} visible or invisible.
     * @param b
     */
    @Override
    public void setDirectionsVisible(boolean b) {
        if(b) {
            if(getRight() == null) {
                setRight(directionsPane);
            }
        }else{
            setRight(null);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void mapInitialized() {
        
        mapComponent.addMapReadyListener(() -> {
            // This call will fail unless the map is completely ready.
            // Leaving this in for documentation on how to go from lat/lon
            // to pixel coordinates.
            //checkCenter(center);
            
            // Locates the user's aprox. location and centers the map there.
            try {
                String ip = Locator.getIp();
                Location l = Locator.getIPLocation(ip);
                googleMap.setCenter(new LatLong(l.getLatitude(), l.getLongitude()));
            }catch(Exception e) {
                e.printStackTrace();
            }
        });
        
        createGoogleMap();
        
        /** See {@link #removeDefaultMapEventHandler()} */
        if(defaultMapEventHandlerInstalled) {
            addMapEventHandler(MapEventType.CLICK, DEFAULT_MAPEVENT_HANDLER);
        }
    }
    
    /**
     * Removes the default handler which:
     * <ol>
     *     <li>Checks to see if "routeSimulationMode" is true (see {@link #setRouteSimulationMode(boolean)})
     *     <li> if routeSimulationMode is true, and the user left-clicked the map, a Waypoint will be added
     *     to either a route named "temp" or if {@link #setCurrentRoute} has been called with a valid Route, 
     *     it will be added to that current {@link Route}
     *     <li> if routeSimulationMode is false, nothing happens.
     * </ol>
     * 
     * <em>WARNING: Must be called before {@link Map#initialize()} is called or 
     * else this has no effect</em>
     */
    @Override
    public void removeDefaultMapEventHandler() {
        defaultMapEventHandlerInstalled = false;
    }
    
    /**
     * Sets the current {@link Route} to which {@link #addWaypoint(Waypoint)} will add a waypoint.
     * Routes may be created by calling {@link Map#createRoute(String)} with a unique name.
     * 
     * @param r        the {@code Route} make current.
     */
    public void setCurrentRoute(Route route) {
        this.currentRoute = route;
    }
    
    /**
     * Sets the flag indicating which mode the {@code Map} is currently in.
     * "Regular mode" is the mode where routes are loaded from external 
     * sources, and "Route Simulation Mode" is where the user can click on 
     * the map and create new routes.
     * 
     * @param b
     */
    public void setRouteSimulationMode(boolean b) {
        isRouteSimMode = b;
    }
    
    /**
     * Adds a {@link Node} acting as a toolbar
     * @param n a toolbar
     */
    public void addToolBar(Node n) {
        setTop(n);
    }
    
    /**
     * Returns a mutable {@link IntegerProperty} used to display
     * and change the zoom factor.
     * 
     * @return zoom {@link IntegerProperty}
     */
    @Override
    public IntegerProperty zoomProperty() {
        return googleMap.zoomProperty();
    }
    
    /**
     * Demonstrates how to go from lat/lon to pixel coordinates.
     * @param center
     */
    @SuppressWarnings("unused")
    private void checkCenter(LatLon center) {
        System.out.println("Testing fromLatLngToPoint using: " + center);
        Point2D p = googleMap.fromLatLngToPoint(center.toLatLong());
        System.out.println("Testing fromLatLngToPoint result: " + p);
        System.out.println("Testing fromLatLngToPoint expected: " + mapComponent.getWidth()/2 + ", " + mapComponent.getHeight()/2);
        System.out.println("type = "+ MarkerType.BROWN.iconPath());
    }

    @Override
    public void centerMapOnLocal() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addMapInializedListener(MapInitializedListener listener) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeMapInitializedListener(MapInitializedListener listener) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addMapReadyListener(MapReadyListener listener) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeReadyListener(MapReadyListener listener) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void addMarker(Marker marker) {
        googleMap.addMarker(marker.convert());
        String name = marker.convert().getVariableName();
        System.out.println("marker name = " + name);
        Object obj = googleMap.getJSObject().getMember(name);
        System.out.println("marker obj = " + obj);
        System.out.println("icon = " + marker.convert().getJSObject().getMember("icon"));
    }

    @Override
    public void removeMarker(Marker marker) {
        googleMap.removeMarker(marker.convert());
    }

    /**
     * Creates a {@link Waypoint} which is a combination of a 
     * {@link LatLon} and a {@link Marker}. 
     * 
     * @param latLon    the latitude/longitude position of the waypoint 
     * @return  the newly created {@code Waypoint}
     */
    @Override
    public Waypoint createWaypoint(LatLon latLon) {
        MarkerOptions opts = new MarkerOptions()
            .position(latLon)
            .title("Waypoint ")
            .icon(MarkerType.GREEN.nextPath())
            .visible(true);
        
        return new Waypoint(latLon, new Marker(opts));
    }

    /**
     * Adds a {@link Waypoint} to the map connecting it to any 
     * previously added {@code Waypoint}s by a connecting line,
     * as opposed to adding a {@link Marker} which doesn't add 
     * a line.
     * 
     * @param waypoint  the {@link Waypoint} to be added.
     * @see #addMarker(Marker)
     */
    @Override
    public void addWaypoint(Waypoint waypoint) {
        currentRoute.add(waypoint);
        addMarker(waypoint.getMarker());
        routeStore.store();
    }

    @Override
    public void removeWaypoint(Waypoint wayoint) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Adds a {@link Route} to this {@code Map}
     * @param route     the route to add
     */
    public void addRoute(Route route) {
        routeStore.addRoute(route);
        routeStore.store();
    }
    
    /**
     * Removes the specified {@link Route} from this {@code Map}
     * 
     * @param route     the route to remove
     */
    public void removeRoute(Route route) {
        
    }
    
    /**
     * Adds a list of {@link Route}s to this {@code Map}
     * 
     * @param routes    the list of routes to add
     */
    public void addRoutes(List<Route> routes) {
        
    }
    
    /**
     * Removes all {@link Route}s from this {@code Map}
     */
    public void removeAllRoutes() {
        
    }

    @Override
    public DirectionsRoute getSnappedDirectionsRoute(Route route) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Adds an EventHandler which can be notified of JavaScript events 
     * arising from the map within a given {@link WebView}
     * 
     * @param eventType     the Event Type to monitor
     * @param handler       the handler to be notified.
     */
    @Override
    public void addMapEventHandler(MapEventType eventType, MapEventHandler handler) {
        googleMap.addUIEventHandler(eventType.convert(), handler);
    }
    
    /**
     * Adds an EventHandler which can be notified of JavaScript events 
     * arising from the map object within a given {@link WebView}
     * 
     * @param mapObject     the {@link MapObject} event source
     * @param eventType     the Event Type to monitor
     * @param handler       the handler to be notified.
     */
    public void addObjectEventHandler(MapObject mapObject, MapEventType eventType, MapEventHandler handler) {
        googleMap.addUIEventHandler(mapObject.convert(), eventType.convert(), handler);
        
    }

    @Override
    public void addMapObject(MapObject mapObject) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public BooleanProperty routeSnappedProperty() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObjectProperty<ObservableValue<MapType>> mapTypeProperty() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObjectProperty<ObservableValue<LatLon>> clickProperty() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObjectProperty<ObservableValue<LatLon>> centerMapProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * Returns this JavaFX {@link Node} 
     */
    @Override
    public MapPane getNode() {
        return this;
    }
    
    /**
     * Returns the default {@link MapOptions}
     * @return  the default MapOptions
     */
    private static MapOptions getDefaultOptions() {
        MapOptions options = new MapOptions();
        options.mapMarker(true)
            .zoom(15)
            .overviewMapControl(false)
            .panControl(false)
            .rotateControl(false)
            .scaleControl(false)
            .streetViewControl(false)
            .zoomControl(false)
            .mapTypeControl(false)
            .mapType(MapType.ROADMAP);
        
        return options;
    }
    
    /**
     * Returns the default {@link MapEventHandler}
     * @return the default {@code MapEventHandler}
     */
    private MapEventHandler getDefaultMapEventHandler() {
        return (JSObject obj) -> {
            if(isRouteSimMode) {
                if(currentRoute == null) {
                    currentRoute = new Route("temp");
                    addRoute(currentRoute);
                }
                
                LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
                
                Waypoint waypoint = createWaypoint(new LatLon(ll.getLatitude(), ll.getLongitude()));
                addWaypoint(waypoint);
                
                System.out.println("clicked: " + ll.getLatitude() + ", " + ll.getLongitude());
            }
        };
    }
    
    /**
     * Creates the internal {@link GoogleMap} object
     */
    private void createGoogleMap() {
        googleMap = mapComponent.createMap(userOptions == null ? 
            DEFAULT_OPTIONS.convert() : userOptions.convert());
    }
}

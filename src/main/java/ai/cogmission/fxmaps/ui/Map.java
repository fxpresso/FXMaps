package ai.cogmission.fxmaps.ui;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.web.WebView;
import ai.cogmission.fxmaps.event.MapEventHandler;
import ai.cogmission.fxmaps.event.MapEventType;
import ai.cogmission.fxmaps.event.MapInitializedListener;
import ai.cogmission.fxmaps.event.MapReadyListener;
import ai.cogmission.fxmaps.model.DirectionsRoute;
import ai.cogmission.fxmaps.model.LatLon;
import ai.cogmission.fxmaps.model.MapObject;
import ai.cogmission.fxmaps.model.MapOptions;
import ai.cogmission.fxmaps.model.MapShape;
import ai.cogmission.fxmaps.model.MapShapeOptions;
import ai.cogmission.fxmaps.model.MapStore;
import ai.cogmission.fxmaps.model.MapType;
import ai.cogmission.fxmaps.model.Marker;
import ai.cogmission.fxmaps.model.Route;
import ai.cogmission.fxmaps.model.Waypoint;

import com.lynden.gmapsfx.MapComponentInitializedListener;


/**
 * Designates all public methods on {@code Map}s.
 * 
 * @author cogmission
 * @see MapPane
 */
public interface Map extends MapComponentInitializedListener {
    public static final double DEFAULT_WIDTH = 1000;
    public static final double DEFAULT_HEIGHT = 780;
    
    /**
     * Factory method to create and return a {@code Map} 
     * 
     * <p>
     * To create a MapPane:
     * <br><br>
     * 1. Map map = Map.create("My Map Name");
     * <br>--or--<br>
     * 2. MapPane map = Map.create("My Map Name");
     * <br><br>
     * 
     * To call JavaFX Node methods on the {@link MapPane} (such as setting width and height), use option 2.
     * or cast the {@code Map} to {@link MapPane} at some later time.
     * 
     * The return type may be typed as either a Map or MapPane.
     * 
     * @return  a {@code Map} 
     */
    @SuppressWarnings("unchecked")
    public static <T extends MapPane> T create() {
        MapPane map = new MapPane();
        map.getNode().setPrefWidth(Map.DEFAULT_WIDTH);
        map.getNode().setPrefHeight(Map.DEFAULT_HEIGHT);
        map.setDirectionsVisible(true);
        
        return (T)map;
    }
    
    public default void addMap(String mapName) {
        getMapStore().addMap(mapName);
    }
    
    /**
     * Returns this instance's {@link MapStore}
     * @return  the map store for this instance
     */
    public MapStore getMapStore(); 
    /**
     * 
     * @param name
     */
    /**
     * Creates and initializes child components to prepare the map
     * for immediate use. when the {@link Map} is initialized it is 
     * given two call backs which when called, indicate that the map
     * is ready for use. 
     * 
     * Before this method is called, any desired {@link MapOptions} must
     * be set on the map before calling {@code #initialize()} 
     */
    public void initialize();
    /**
     * Returns the {@link MapOptions} set on this {@code Map}
     * 
     * @return  this {@code Map}'s {@link MapOptions}
     */
    public MapOptions getMapOptions();
    /**
     * Specifies the {@link MapOptions} to use. <em>Note</em> this must
     * be set prior to calling {@link #initialize()}
     * 
     * @param mapOptions    the {@code MapOptions} to use.
     */
    public void setMapOptions(MapOptions mapOptions);
    /**
     * Returns the MapPane Node
     * @return
     */
    public MapPane getNode();
    /**
     * Adds a {@link Node} acting as a toolbar
     * @param n a toolbar
     */
    public void addToolBar(Node n);
    /**
     * Sets the flag indicating which mode the {@code Map} is currently in.
     * "Regular mode" is the mode where routes are loaded from external 
     * sources, and "Route Simulation Mode" is where the user can click on 
     * the map and create new routes.
     * 
     * @param b     true if in simulation mode, false if not
     */
    public void setRouteSimulationMode(boolean b);
    /**
     * Centers this {@code Map} on the user's current city location.
     */
    public void centerMapOnLocal();
    /**
     * Sets the center location of the map to the specified lat/lon 
     * coordinates.
     * 
     * @param ll    the lat/lon coordinates around which to center the map.
     */
    public void setCenter(LatLon ll);
    /**
     * Adds a {@link MapInitializedListener} to the {@code Map}
     * 
     * @param listener  the listener to add
     */
    public void addMapInializedListener(MapInitializedListener listener);
    /**
     * Removes the specified {@link MapInitializedListener} from the {@code Map}
     * 
     * @param listener  the listener to remove
     */
    public void removeMapInitializedListener(MapInitializedListener listener);
    /**
     * Adds a {@link MapReadyListener} to the {@code Map} which will be notified
     * when the {@link MapOptions} are applied to this {@code Map}
     * 
     * @param listener  the listener to add
     */
    public void addMapReadyListener(MapReadyListener listener);
    /**
     * Removes the specified {@link MapReadyListener} from this {@code Map}
     * 
     * @param listener  the listener to remove
     */
    public void removeReadyListener(MapReadyListener listener);
    /**
     * Displays a {@link Marker} on the {@code Map}, as opposed to adding
     * a {@link Waypoint} which adds a {@code Marker} and a connecting
     * line from any previous {@link Marker} along a given {@link Route}.
     * 
     * @param marker    the marker to add
     * @see Wayoint
     */
    public void displayMarker(Marker marker);
    /**
     * Removes the specified {@link Marker} from the {@code Map}, as opposed to removing
     * a {@link Waypoint} which removes a {@code Marker} and a connecting
     * line from any previous {@link Marker} along a given {@link Route}. 
     * 
     * This method only removes the marker from the display, it does nothing to the route.
     * 
     * @param marker    the marker to remove
     * @see Waypoint
     */
    public void clearMarker(Marker marker);
    /**
     * Adds the specified {@link MapShape} to this {@code Map}
     * 
     * @param shape     the {@code MapShape} to add
     */
    public void displayShape(MapShape shape);
    /**
     * Removes the specified {@link MapShape} from this {@code Map}
     * @param shape     the {@code MapShape} to remove
     */
    public void clearShape(MapShape shape);
    /**
     * Creates a {@link Waypoint} which is a combination of a 
     * {@link LatLon} and a {@link Marker}. 
     * 
     * @param latLon    the latitude/longitude position of the waypoint 
     * @return  the newly created {@code Waypoint}
     */
    public Waypoint createWaypoint(LatLon latLon);
    /**
     * Adds a {@link Waypoint} to the map connecting it to any 
     * previously added {@code Waypoint}s by a connecting line,
     * as opposed to adding a {@link Marker} which doesn't add 
     * a line.
     * 
     * @param waypoint  the {@link Waypoint} to be added.
     * @see #displayMarker(Marker)
     */
    public void addNewWaypoint(Waypoint waypoint);
    /**
     * Adds and Displays a {@link Waypoint}
     * <p>
     * Adds a {@link Waypoint} to the map connecting it to any 
     * previously added {@code Waypoint}s by a connecting line,
     * as opposed to adding a {@link Marker} which doesn't add 
     * a line.
     * </p><p>
     * Distinguished from {@link #displayWaypoint(Waypoint)} which
     * is used to add a persisted Waypoint that has already been
     * created, to the display.
     * 
     * @param waypoint          the {@link Waypoint} to be added.
     * @param polylineOptions   the subclass of {@link MapShapeOptions} containing desired
     *                  properties of the rendering operation.
     * @see #displayMarker(Marker)
     * @see #addNewWaypoint(Waypoint)
     */
    public <T extends MapShapeOptions<T>>void addNewWaypoint(Waypoint waypoint, T polylineOptions);
    /**
     * Adds a {@link Waypoint} from the {@link MapStore} when the specified
     * Waypoint is already part of a {@link Route} and already has connecting leg lines.
     * @param waypoint     the Waypoint to add
     */
    public void displayWaypoint(Waypoint point);
    /**
     * Removes the {@link Waypoint} from the map and its connecting line.
     * 
     * @param wayoint
     * @see #displayMarker(Marker)
     */
    public void removeWaypoint(Waypoint wayoint);
    /**
     * Creates a {@link Route} by the given name.
     * 
     * @param name      the name of the new Route
     * @return  the route which was added.
     * @throws RouteAlreadyExistsException  if a Route by the specified name already exists.
     */
    public static Route createRoute(String name) {
        Route r = new Route(name);
        return r;
    }
    
    /**
     * Adds a {@link Route} to this {@code Map}
     * @param route     the route to add
     */
    public void addRoute(Route route);
    /**
     * Removes the specified {@link Route} from this {@code Map}
     * 
     * @param route     the route to remove
     */
    public void removeRoute(Route route);
    /**
     * Selects the specified {@link Route}, designating it
     * to be the currently focused route.
     * 
     * @param route the {@code Route} to select.
     */
    public void selectRoute(Route route);
    /**
     * Adds a list of {@link Route}s to this {@code Map}
     * 
     * @param routes    the list of routes to add
     */
    public void displayRoutes(List<Route> routes);
    /**
     * Redraws the map
     */
    public void refresh();
    /**
     * Removes all {@link Route}s from this {@code Map}
     */
    public void clearMap();
    /**
     * Returns a Route consisting of arbitrary {@link Waypoint}s into a
     * {@link DirectionsRoute} which adheres to Streets and Highways. In
     * order to do this, a given route may have {@link Waypoint}s added to
     * it in order to "snap" it to a given street or highway.
     * 
     * @param route     a direct route which may not follow roads.
     * @return  a {@link DirectionsRoute} which adheres to roads.
     */
    public DirectionsRoute getSnappedDirectionsRoute(Route route);
    /**
     * Adds an EventHandler which can be notified of JavaScript events 
     * arising from the map object within a given {@link WebView}
     * 
     * @param mapObject     the {@link MapObject} event source
     * @param eventType     the Event Type to monitor
     * @param handler       the handler to be notified.
     */
    public void addObjectEventHandler(MapObject mapObject, MapEventType eventType, MapEventHandler handler);
    /**
     * Adds an EventHandler which can be notified of JavaScript events 
     * arising from the map object within a given {@link WebView}
     * 
     * @param mapObject     the {@link MapObject} event source
     * @param eventType     the Event Type to monitor
     * @param handler       the handler to be notified.
     */
    public void addMapEventHandler(MapEventType eventType, MapEventHandler handler);
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
     * <em>WARNING: To guarantee consistency, this MUST be called before {@link Map#initialize()} is called or 
     * else this has no effect</em>
     */
    public void removeDefaultMapEventHandler();
    /**
     * Sets the current {@link Route} to which {@link #addNewWaypoint(Waypoint)} will add a waypoint.
     * Routes may be created by calling {@link Map#createRoute(String)} with a unique name.
     * 
     * @param r        the {@code Route} make current.
     */
    public void setCurrentRoute(Route r);
    /**
     * Adds a {@link MapObject} JavaScript peer to the map object's DOM.
     * @param mapObject
     */
    public void addMapObject(MapObject mapObject);
    
    ///////////////////////////////////////
    //        Properties for control     //
    ///////////////////////////////////////
    /**
     * Sets the {@link DirectionsPane} visible flag.
     * @param b     true if visible, false if not
     */
    public void setDirectionsVisible(boolean b);
    /**
     * Returns the property tracking the "isSnapped" flag - which indicates that
     * route {@link Waypoint}s will adhere to known roads or highways.
     * 
     * @return  {@link BooleanProperty} controlling isSnapped flag.
     */
    public BooleanProperty routeSnappedProperty();
    /**
     * Returns a property which tracks the map zoom level.
     * 
     * @return  {@link IntegerProperty}
     */
    public IntegerProperty zoomProperty();
    /**
     * Returns a property which tracks the current center of the map in terms of
     * longitude and lattitude.
     * 
     * @return  {@link ObjectProperty} containing {@link LatLon} changes.
     */
    public ObjectProperty<ObservableValue<LatLon>> centerMapProperty();
    /**
     * Returns a property which tracks user setting of {@link MapType}
     * 
     * @return  {@link ObjectProperty} tracking {@link MapType}
     */
    public ObjectProperty<ObservableValue<MapType>> mapTypeProperty();
    /**
     * Returns a property which tracks the user's last click location in
     * terms of longitude and lattitude.
     *  
     * @return  {@link ObjectProperty} controlling click locations.
     */
    public ObjectProperty<ObservableValue<LatLon>> clickProperty();
    
}

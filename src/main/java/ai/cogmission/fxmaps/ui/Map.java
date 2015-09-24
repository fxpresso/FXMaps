package ai.cogmission.fxmaps.ui;

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
import ai.cogmission.fxmaps.model.MapType;
import ai.cogmission.fxmaps.model.Marker;
import ai.cogmission.fxmaps.model.Route;
import ai.cogmission.fxmaps.model.Waypoint;

import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.MapOptions;



public interface Map extends MapComponentInitializedListener {
    public static final double DEFAULT_WIDTH = 1000;
    public static final double DEFAULT_HEIGHT = 780;
    
    /**
     * Factory method to create and return a {@code Map} 
     * 
     * @return  a map
     */
    public static Map create() {
        MapPane map = new MapPane();
        map.getNode().setPrefWidth(Map.DEFAULT_WIDTH);
        map.getNode().setPrefHeight(Map.DEFAULT_HEIGHT);
        map.setDirectionsVisible(true);
        return map;
    }
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
     * Centers this {@code Map} on the user's current city location.
     */
    public void centerMapOnLocal();
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
     * Adds a {@link Marker} to the {@code Map}, as opposed to adding
     * a {@link Waypoint} which adds a {@code Marker} and a connecting
     * line from any previous {@link Marker} along a given {@link Route}.
     * 
     * @param marker    the marker to add
     * @see Wayoint
     */
    public void addMarker(Marker marker);
    /**
     * Removes the specified {@link Marker} from the {@code Map}, as opposed to removing
     * a {@link Waypoint} which removes a {@code Marker} and a connecting
     * line from any previous {@link Marker} along a given {@link Route}.
     * 
     * @param marker    the marker to remove
     * @see Waypoint
     */
    public void removeMarker(Marker marker);
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
     * @see #addMarker(Marker)
     */
    public void addWaypoint(Waypoint waypoint); // Was addRouteMarker()
    /**
     * Removes the {@link Waypoint} from the map and its connecting line.
     * 
     * @param wayoint
     * @see #addMarker(Marker)
     */
    public void removeWaypoint(Waypoint wayoint);
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
    public void addMapEventHandler(MapObject mapObject, MapEventType eventType, MapEventHandler handler);
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

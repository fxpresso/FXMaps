package ai.cogmission.fxmaps.ui;

import java.util.List;

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
import ai.cogmission.fxmaps.event.RouteAlreadyExistsException;
import ai.cogmission.fxmaps.model.DirectionsRoute;
import ai.cogmission.fxmaps.model.LatLon;
import ai.cogmission.fxmaps.model.Locator;
import ai.cogmission.fxmaps.model.MapObject;
import ai.cogmission.fxmaps.model.MapType;
import ai.cogmission.fxmaps.model.Marker;
import ai.cogmission.fxmaps.model.MarkerOptions;
import ai.cogmission.fxmaps.model.MarkerType;
import ai.cogmission.fxmaps.model.Route;
import ai.cogmission.fxmaps.model.Waypoint;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

/**
 * Undecorated {@link Pane} extension which is specialized to contain a 
 * map view and an optional {@link DirectionsPane}.
 * 
 * @author cogmission
 *
 */
public class MapPane extends BorderPane implements Map {
    protected GoogleMapView mapComponent;
    protected GoogleMap delegate;
    
    protected DirectionsPane directionsPane;
    
    protected Route currentRoute;
    protected boolean isRouteSimMode;
    
    /**
     * Constructs a new {@code MapPane}
     */
    public MapPane() {
        setPrefWidth(1000);
        setPrefHeight(780);
        
        mapComponent = new GoogleMapView();
        mapComponent.addMapInializedListener(this);
        
        directionsPane = new DirectionsPane();
        directionsPane.setPrefWidth(200);
        
        setCenter(mapComponent);
        
        setDirectionsVisible(false);
    }
    
    @Override
    public void addMarker(Marker marker) {
        delegate.addMarker(marker.convert());
        String name = marker.convert().getVariableName();
        System.out.println("marker name = " + name);
        Object obj = delegate.getJSObject().getMember(name);
        System.out.println("marker obj = " + obj);
        System.out.println("icon = " + marker.convert().getJSObject().getMember("icon"));
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
        LatLon center = new LatLon(41.91073, -87.71332000000001);
        mapComponent.addMapReadyListener(() -> {
            // This call will fail unless the map is completely ready.
            checkCenter(center);
            
            try {
                String ip = Locator.getIp();
                Locator.getIPLocation(ip);
                
            }catch(Exception e) {
                e.printStackTrace();
            }
        });
        
        MapOptions options = new MapOptions();
        options.center(center.toLatLong())
            .mapMarker(true)
            .zoom(15)
            .overviewMapControl(false)
            .panControl(false)
            .rotateControl(false)
            .scaleControl(false)
            .streetViewControl(false)
            .zoomControl(false)
            .mapTypeControl(false)
            .mapType(MapTypeIdEnum.ROADMAP);

        delegate = mapComponent.createMap(options);
        
        addMapEventHandler(MapEventType.CLICK, (JSObject obj) -> {
            if(isRouteSimMode) {
                LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
                LatLon newLL = null;
                MarkerOptions opts = new MarkerOptions()
                    .position(newLL = new LatLon(ll.getLatitude(), ll.getLongitude()))
                    .title("Waypoint ")
                    .icon(MarkerType.GREEN.nextPath())
                    .visible(true);
                
                System.out.println("clicked: " + ll.getLatitude() + ", " + ll.getLongitude());
            
                if(currentRoute == null) {
                    
                }
                Marker marker = new Marker(opts);
                currentRoute.add(new Waypoint(newLL, marker));
                addMarker(marker);
            }
        });
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
        return delegate.zoomProperty();
    }
    
    /**
     * 
     * @param center
     */
    private void checkCenter(LatLon center) {
        System.out.println("Testing fromLatLngToPoint using: " + center);
        Point2D p = delegate.fromLatLngToPoint(center.toLatLong());
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
    public void removeMarker(Marker marker) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Waypoint createWaypoint(LatLon latLon) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addWaypoint(Waypoint waypoint) {
        // TODO Auto-generated method stub
        
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
        delegate.addUIEventHandler(eventType.convert(), handler);
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
        delegate.addUIEventHandler(mapObject.convert(), eventType.convert(), handler);
        
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
    
    @Override
    public MapPane getNode() {
        return this;
    }
}

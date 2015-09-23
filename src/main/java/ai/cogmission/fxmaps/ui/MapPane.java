package ai.cogmission.fxmaps.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import netscape.javascript.JSObject;
import ai.cogmission.fxmaps.model.LatLon;
import ai.cogmission.fxmaps.model.Locator;
import ai.cogmission.fxmaps.model.Marker;
import ai.cogmission.fxmaps.model.MarkerOptions;
import ai.cogmission.fxmaps.model.MarkerType;
import ai.cogmission.fxmaps.model.Waypoint;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
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
public class MapPane extends BorderPane implements MapComponentInitializedListener {
    protected GoogleMapView mapComponent;
    protected GoogleMap map;
    
    protected DirectionsPane directionsPane;
    
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
    
    public void addMarker(Marker marker) {
        map.addMarker(marker.convert());
        Platform.runLater(() -> {
            String name = marker.convert().getVariableName();
            System.out.println("marker name = " + name);
            Object obj = map.getJSObject().getMember(name);
            System.out.println("marker obj = " + obj);
            System.out.println("icon = " + marker.convert().getJSObject().getMember("icon"));
        });
        
    }
    
    /**
     * Makes the right {@link DirectionsPane} visible or invisible.
     * @param b
     */
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

        map = mapComponent.createMap(options);
        
        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
            LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
            LatLon newLL = null;
            MarkerOptions opts = new MarkerOptions()
                .position(newLL = new LatLon(ll.getLatitude(), ll.getLongitude()))
                .title("Waypoint ")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            
            System.out.println("clicked: " + ll.getLatitude() + ", " + ll.getLongitude());
        
            Marker marker = new Marker(opts);
            route.add(new Waypoint(newLL, marker));
            addMarker(marker);
        });
    }
    List<Waypoint> route = new ArrayList<>();
    /**
     * Returns a mutable {@link IntegerProperty} used to display
     * and change the zoom factor.
     * 
     * @return zoom {@link IntegerProperty}
     */
    public IntegerProperty zoomProperty() {
        return map.zoomProperty();
    }
    
    /**
     * 
     * @param center
     */
    private void checkCenter(LatLon center) {
        System.out.println("Testing fromLatLngToPoint using: " + center);
        Point2D p = map.fromLatLngToPoint(center.toLatLong());
        System.out.println("Testing fromLatLngToPoint result: " + p);
        System.out.println("Testing fromLatLngToPoint expected: " + mapComponent.getWidth()/2 + ", " + mapComponent.getHeight()/2);
        System.out.println("type = "+ MarkerType.BROWN.iconPath());
    }
    
}

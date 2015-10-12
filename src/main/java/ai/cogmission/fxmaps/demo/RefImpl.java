package ai.cogmission.fxmaps.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import ai.cogmission.fxmaps.event.MapReadyListener;
import ai.cogmission.fxmaps.model.LatLon;
import ai.cogmission.fxmaps.model.MapOptions;
import ai.cogmission.fxmaps.model.MapType;
import ai.cogmission.fxmaps.model.MarkerType;
import ai.cogmission.fxmaps.model.PersistentMap;
import ai.cogmission.fxmaps.model.Route;
import ai.cogmission.fxmaps.ui.Map;

/**
 * Reference implementation for the FXMaps library.
 * 
 * @author cogmission
 *
 */
public class RefImpl extends Application {
    private Map map;
    
    private Stage primaryStage;
    
    private RefImplToolBar toolBar;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        
        createMapPane();
        createToolBar();
        Scene scene = new Scene(map.getNode(), Map.DEFAULT_WIDTH, Map.DEFAULT_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Create the {@link ToolBar} for controlling map parameters.
     */
    public void createToolBar() {
        toolBar = new RefImplToolBar(this, map);
        map.addToolBar(toolBar);
    }
    
    /**
     * Demonstrates how to set custom {@link MapOptions}
     */
    public void configureOptions() {
        LatLon center = new LatLon(41.91073, -87.71332000000001);
        MapOptions options = new MapOptions();
        options.mapMarker(true)
            .center(center)
            .zoom(15)
            .overviewMapControl(false)
            .panControl(false)
            .rotateControl(false)
            .scaleControl(false)
            .streetViewControl(false)
            .zoomControl(false)
            .mapTypeControl(false)
            .mapType(MapType.ROADMAP);
        
        map.setMapOptions(options);
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public void createMapPane() {
        map = Map.create();
        map.addMapReadyListener(getMapReadyListener());
        map.initialize();
    }
    
    public MapReadyListener getMapReadyListener() {
        return () -> {
            java.util.Map<String, PersistentMap> maps = map.getMapStore().getMaps();
            toolBar.addMaps(maps.keySet());
        };
    }
    
    public void clearMap() {
        PersistentMap pm = map.getMapStore().getMap(map.getMapStore().getSelectedMapName());
        if(pm != null && pm.getRoutes() != null && pm.getRoutes().size() > 0) {
            map.clearMap();
        }
    }
    
    /**
     * Called when the user hits the "Enter" key via the mapCombo.valueProperty;
     * or when the user clicks the "Add" button.
     * 
     * @param mapName   the name of the map to create or select
     */
    public void createOrSelectMap(String mapName) {
        map.clearMap();
        
        // Reset the marker letter sequence in preparation for
        // newly added waypoints
        MarkerType.reset();
        
        // Will add a new map only if one doesn't exist under name
        map.addMap(mapName);
        
        // Add only if not already in combo (toolbar handles that)
        toolBar.addMap(mapName);
        
        // Initialize combo to point to selected map
        toolBar.selectMap(mapName);
        
        // Display the selected map's routes
        PersistentMap pm = map.getMapStore().getMap(mapName);
        System.out.println("map " + map.getMapStore().getSelectedMapName() + " has " + pm.getRoutes().size() + " routes.");
        
        map.setOverlayVisible(false);
        
        toolBar.mapSelected(pm);
    }
    
    public void createOrSelectRoute(String routeName) {
        if(routeName == null) {
            // Analyze this later...
            System.out.println("createOrSelectRoute(): null routeName");
            return;
        }
        
        // Will add a new route only if one doesn't exist under name
        Route newRoute = new Route(routeName);
        map.addRoute(newRoute);
        
        // Set reference to the current route
        map.setCurrentRoute(newRoute);
        
        // Select and display the route
        toolBar.selectRoute(routeName);
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");
        launch(args);
    }
}

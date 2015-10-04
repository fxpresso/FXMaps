package ai.cogmission.fxmaps.demo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ai.cogmission.fxmaps.event.MapReadyListener;
import ai.cogmission.fxmaps.model.LatLon;
import ai.cogmission.fxmaps.model.MapOptions;
import ai.cogmission.fxmaps.model.MapType;
import ai.cogmission.fxmaps.model.PersistentMap;
import ai.cogmission.fxmaps.model.Route;
import ai.cogmission.fxmaps.model.Waypoint;
import ai.cogmission.fxmaps.ui.Map;

/**
 * Reference implementation for the FXMaps library.
 * 
 * @author cogmission
 *
 */
public class RefImpl extends Application {
    private Map map;
    
    private ToggleButton simulationBtn;
    private ToggleButton directionsBtn;
    private GridPane loadPane;
    private ComboBox<String> mapCombo;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        createMapPane();
        createToolBar();
        configureToolBar();
        Scene scene = new Scene(map.getNode(), Map.DEFAULT_WIDTH, Map.DEFAULT_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Create the {@link ToolBar} for controlling map parameters.
     */
    public void createToolBar() {
        ToolBar toolBar = new ToolBar(
            simulationBtn = new ToggleButton("Simulation Mode"),
            new Separator(),
            directionsBtn = new ToggleButton("Directions"),
            new Separator(),
            loadPane = getLoadControl()
        );
        
        map.addToolBar(toolBar);
    }
    
    /**
     * Add the ToolBar's action handlers etc.
     */
    public void configureToolBar() {
        simulationBtn.setOnAction(e -> map.setRouteSimulationMode(simulationBtn.isSelected()));
        directionsBtn.setOnAction(e -> map.setDirectionsVisible(directionsBtn.isSelected()));
        directionsBtn.setSelected(true);
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
    
    /**
     * Creates and returns the control for loading maps
     * @return  the control for loading maps
     */
    public GridPane getLoadControl() {
        GridPane gp = new GridPane();
        gp.setHgap(5);
        
        mapCombo = new ComboBox<>();
        mapCombo.setEditable(true);
        mapCombo.setPromptText("Type or select name.");
        mapCombo.valueProperty().addListener(getMapSelectionListener());
        
        Button add = new Button("Add");
        add.setOnAction(e -> createOrSelectMap(mapCombo.getSelectionModel().getSelectedItem()));
        Button del = new Button("Clear map");
        del.setOnAction(e -> clearMap());
        
        gp.add(mapCombo, 0, 0, 2, 1);
        gp.add(add, 2, 0);
        gp.add(del, 3, 0);
        
        return gp;
    }
    
    public void createMapPane() {
        map = Map.create();
        map.addMapReadyListener(getMapReadyListener());
        map.initialize();
    }
    
    public MapReadyListener getMapReadyListener() {
        return () -> {
            java.util.Map<String, PersistentMap> maps = map.getMapStore().getMaps();
            mapCombo.getItems().addAll(maps.keySet());
        };
    }
    
    public void deleteMap() {
        PersistentMap pm = map.getMapStore().getMap(map.getMapStore().getSelectedMapName());
        if(pm != null && pm.getRoutes() != null && pm.getRoutes().size() > 0) {
            map.clearMap();
            map.getMapStore().deleteMap(map.getMapStore().getSelectedMapName());
            map.getMapStore().store();
        }
    }
    
    public void clearMap() {
        PersistentMap pm = map.getMapStore().getMap(map.getMapStore().getSelectedMapName());
        if(pm != null && pm.getRoutes() != null && pm.getRoutes().size() > 0) {
            map.clearMap();
        }
    }
    
    public void createOrSelectMap(String mapName) {
        map.clearMap();
        
        // Will add a new map only if one doesn't exist under name
        map.addMap(mapName);
        
        // Add only if not already in combo
        if(!mapCombo.getItems().contains(mapName)) {
            mapCombo.getItems().add(mapName);
        }
        
        // Initialize combo to point to selected map
        mapCombo.getSelectionModel().select(mapName);
        
        // IMPORTANT: Point the MapStore to the desired map selection
        try {
            map.getMapStore().selectMap(mapName);
        }catch(Exception e) { 
            e.printStackTrace();
        }
        
        PersistentMap pm = map.getMapStore().getMap(mapName);
        System.out.println("map " + map.getMapStore().getSelectedMapName() + " has " + pm.getRoutes().size() + " routes: " + pm.getRoutes() );
        
        // Display the selected map's routes
        map.displayRoutes(pm.getRoutes());
    }
    
    public ChangeListener<String> getMapSelectionListener() {
        return (v, o, n) -> {
            createOrSelectMap(n);
        };
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

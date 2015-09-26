package ai.cogmission.fxmaps.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import ai.cogmission.fxmaps.model.LatLon;
import ai.cogmission.fxmaps.model.MapOptions;
import ai.cogmission.fxmaps.model.MapType;
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
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        map = Map.create();
        map.initialize();
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
            simulationBtn = new ToggleButton("Sim Mode"),
            new Separator(),
            directionsBtn = new ToggleButton("Directions")
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

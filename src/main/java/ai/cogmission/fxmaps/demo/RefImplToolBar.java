package ai.cogmission.fxmaps.demo;

import java.io.File;
import java.util.Collection;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ai.cogmission.fxmaps.model.PersistentMap;
import ai.cogmission.fxmaps.ui.Map;
import ai.cogmission.fxmaps.xml.GPXPersistentMap;
import ai.cogmission.fxmaps.xml.GPXReader;
import ai.cogmission.fxmaps.xml.GPXType;

/**
 * <p>
 * Example tool bar for the reference implementation
 * </p><p>
 * Shows how to create and interact with a tool bar in your own map
 * application
 * </p>
 * 
 * @author cogmission
 */
public class RefImplToolBar extends ToolBar {
    private RefImpl parent;
    
    private Map map;
    
    private ToggleButton simulationBtn;
    private ToggleButton directionsBtn;
    private GridPane loadPane;
    private ComboBox<String> mapCombo;
    
    public RefImplToolBar(RefImpl parent, Map map) {
        this.parent = parent;
        this.map = map;
        initializeToolBar();
        configureToolBar();
    }
    
    /**
     * Create the {@link ToolBar} for controlling map parameters.
     */
    public void initializeToolBar() {
        getItems().addAll(
            simulationBtn = new ToggleButton("Simulation Mode"),
            new Separator(),
            directionsBtn = new ToggleButton("Directions"),
            new Separator(),
            getGPXLoadControl(),
            new Separator(),
            loadPane = getLoadControl()
        );
    }
    
    public Button getGPXLoadControl() {
        Button load = new Button("Select GPX File");
        load.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open GPX File");
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("GPX Files", "*.gpx"),
                    new ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(parent.getPrimaryStage());
            if (selectedFile != null) {
                System.out.println("file = " + selectedFile);
                GPXReader reader = new GPXReader();
                try {
                    GPXPersistentMap gpxMap = reader.read(selectedFile.toURI().toURL());
                    PersistentMap mMap = GPXPersistentMap.asFXMap(gpxMap, GPXType.TRACK);
                    map.getMapStore().getMaps().put(mMap.getName(), mMap);
                    map.getMapStore().selectMap(mMap.getName());
                    parent.createOrSelectMap(mMap.getName());
                    map.setCenter(map.getMapStore().getMap(mMap.getName()).getRoutes().get(0).getOrigin().getLatLon());
                    map.refresh();
                } catch(Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        return load;
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
     * Creates and returns the control for loading maps
     * @return  the control for loading stored maps
     */
    public GridPane getLoadControl() {
        GridPane gp = new GridPane();
        gp.setHgap(5);
        
        mapCombo = new ComboBox<>();
        mapCombo.setEditable(true);
        mapCombo.setPromptText("Type or select name.");
        mapCombo.valueProperty().addListener(parent.getMapSelectionListener());
        
        Button add = new Button("Add");
        add.setOnAction(e -> parent.createOrSelectMap(mapCombo.getSelectionModel().getSelectedItem()));
        Button del = new Button("Clear map");
        del.setOnAction(e -> parent.clearMap());
        
        gp.add(mapCombo, 0, 0, 2, 1);
        gp.add(add, 2, 0);
        gp.add(del, 3, 0);
        
        return gp;
    }
    
    public void addMaps(Collection<String> mapNames) {
        mapCombo.getItems().addAll(mapNames);
    }
    
    public void addMap(String mapName) {
        if(!containsMap(mapName)) {
            mapCombo.getItems().add(mapName);
        }
    }
    
    public boolean containsMap(String name) {
        return mapCombo.getItems().contains(name);
    }
    
    public void selectMap(String name) {
        mapCombo.getSelectionModel().select(name);
        
        // IMPORTANT: Point the MapStore to the desired map selection
        try {
            map.getMapStore().selectMap(name);
        }catch(Exception e) { 
            e.printStackTrace();
        }
    }
}

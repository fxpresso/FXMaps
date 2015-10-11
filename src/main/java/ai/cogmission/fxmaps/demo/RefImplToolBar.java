package ai.cogmission.fxmaps.demo;

import java.io.File;
import java.util.Collection;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ai.cogmission.fxmaps.model.PersistentMap;
import ai.cogmission.fxmaps.ui.Map;
import ai.cogmission.fxmaps.xml.GPXPersistentMap;
import ai.cogmission.fxmaps.xml.GPXReader;
import ai.cogmission.fxmaps.xml.GPXType;
import fxpresso.tidbit.ui.Flyout;

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
    
    private ToggleButton directionsBtn;
    
    private ComboBox<String> mapCombo;
    private ComboBox<String> routeCombo;
    private Flyout mapFlyout;
    private Flyout routeFlyout;
    private ToggleButton mapChooser;
    private ToggleButton routeChooser;
    
    
    public RefImplToolBar(RefImpl parent, Map map) {
        this.parent = parent;
        this.map = map;
        initMapSelector();
        initRouteSelector();
        initializeToolBar();
        configureToolBar();
    }
    
    /**
     * Create the {@link ToolBar} for controlling map parameters.
     */
    public void initializeToolBar() {
        getItems().addAll(
            directionsBtn = new ToggleButton("Directions"),
            new Separator(),
            getGPXLoadControl(),
            new Separator(),
            mapFlyout = new Flyout(mapChooser = new ToggleButton("Create / Select Map"), getMapControl()),
            new Separator(),
            routeFlyout = new Flyout(routeChooser = new ToggleButton("Create / Select Route"), getRouteControl())
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
        directionsBtn.setOnAction(e -> map.setDirectionsVisible(directionsBtn.isSelected()));
        directionsBtn.setSelected(true);
        
        mapChooser.setOnAction(e -> {
            if(mapFlyout.flyoutShowing()) {
                mapFlyout.dismiss();
            }else{
                mapFlyout.flyout();
            }
        });
        
        routeChooser.setOnAction(e -> {
            if(routeFlyout.flyoutShowing()) {
                routeFlyout.dismiss();
            }else{
                routeFlyout.flyout();
            }
        });
    }
    
    /**
     * Initializes the control for choosing/creating maps
     */
    public void initMapSelector() {
        mapCombo = new ComboBox<>();
        mapCombo.setEditable(true);
        mapCombo.setPromptText("Type or select map name.");
        mapCombo.valueProperty().addListener(parent.getMapSelectionListener());
    }
    
    /**
     * Initializes the control for choosing/creating maps
     */
    public void initRouteSelector() {
        routeCombo = new ComboBox<>();
        routeCombo.setEditable(true);
        routeCombo.setPromptText("Type or select route name.");
        routeCombo.valueProperty().addListener(parent.getMapSelectionListener());
    }
    
    /**
     * Creates and returns the control for loading maps
     * @return  the control for loading stored maps
     */
    public GridPane getMapControl() {
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(5, 5, 5, 5));
        gp.setHgap(5);
        
        Label l = new Label("Select or enter map name:");
        l.setFont(Font.font(l.getFont().getFamily(), 12));
        l.setTextFill(Color.WHITE);
        Button add = new Button("Add");
        add.setOnAction(e -> parent.createOrSelectMap(mapCombo.getSelectionModel().getSelectedItem()));
        Button del = new Button("Clear map");
        del.setOnAction(e -> parent.clearMap());
        gp.add(l, 0, 0, 2, 1);
        gp.add(mapCombo, 0, 1, 2, 1);
        gp.add(add, 2, 1);
        gp.add(del, 3, 1);
        
        return gp;
    }
    
    /**
     * Creates and returns the control for loading maps
     * @return  the control for loading stored maps
     */
    public GridPane getRouteControl() {
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(5, 5, 5, 5));
        gp.setHgap(5);
        
        HBox rsMode = new HBox();
        CheckBox cb = new CheckBox("Add Waypoint");
        cb.setTextFill(Color.WHITE);
        cb.setOnAction(e -> {
            map.setRouteSimulationMode(cb.isSelected());
        });
        rsMode.getChildren().add(cb);
        Label l = new Label("Select or enter route name:");
        l.setFont(Font.font(l.getFont().getFamily(), 10));
        l.setTextFill(Color.WHITE);
        Button add = new Button("Add");
        //add.setOnAction();
        Button clr = new Button("Clear route");
        clr.setOnAction(e -> map.clearRoute(map.getRoute(routeCombo.getSelectionModel().getSelectedItem())));
        Button del = new Button("Delete route");
        del.setOnAction(e -> map.removeRoute(map.getRoute(routeCombo.getSelectionModel().getSelectedItem())));
        
        gp.add(l, 0, 0, 2, 1);
        gp.add(rsMode, 2, 0, 2, 1);
        gp.add(routeCombo, 0, 1, 2, 1);
        gp.add(add, 2, 1);
        gp.add(clr, 3, 1);
        gp.add(del, 4, 1);
        
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

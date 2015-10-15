package ai.cogmission.fxmaps.demo;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

import org.controlsfx.control.CheckComboBox;

import ai.cogmission.fxmaps.model.MarkerType;
import ai.cogmission.fxmaps.model.PersistentMap;
import ai.cogmission.fxmaps.model.Route;
import ai.cogmission.fxmaps.ui.Map;
import ai.cogmission.fxmaps.ui.Map.Mode;
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
    private CheckComboBox<String> routeCombo;
    private Flyout mapFlyout;
    private Flyout routeFlyout;
    private ToggleButton mapChooser;
    private ToggleButton routeChooser;
    private CheckBox addWaypointBox;
    private TextField newRouteField;
    
    private static final String DEFAULT_ROUTE_NAME = "temp (change me)";
    private ChangeListener<Flyout.Status> local;
    
    
    /**
     * Constructs a new {@code RefImplToolBar}
     * 
     * @param parent    the parent application
     * @param map       the instance of {@link Map}
     */
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
                
                map.setOverlayVisible(false);
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
            // Protect against clicks during animation and button getting out of sync
            if(mapChooser.isSelected() && mapFlyout.getFlyoutStatusProperty().get() == Flyout.Status.RUNNING) {
                mapChooser.setSelected(false);
                return;
            }else if(!mapChooser.isSelected() && mapFlyout.getFlyoutStatusProperty().get() == Flyout.Status.RUNNING) {
                mapChooser.setSelected(true);
                return;
            }
            
            if(mapFlyout.flyoutShowing()) {
                mapFlyout.dismiss();
            }else{
                mapFlyout.flyout();
                mapCombo.getEditor().requestFocus();
                mapCombo.getEditor().selectAll();
            }
        });
        
        routeChooser.setOnAction(e -> {
            // Protect against clicks during animation and button getting out of sync
            if(routeChooser.isSelected() && routeFlyout.getFlyoutStatusProperty().get() == Flyout.Status.RUNNING) {
                routeChooser.setSelected(false);
                return;
            }else if(!routeChooser.isSelected() && routeFlyout.getFlyoutStatusProperty().get() == Flyout.Status.RUNNING) {
                routeChooser.setSelected(true);
                return;
            }
            
            if(routeFlyout.flyoutShowing()) {
                routeFlyout.dismiss();
            }else{
                routeFlyout.flyout();
//                routeCombo.getEditor().requestFocus();
//                routeCombo.getEditor().selectAll();
            }
        });
    }
    
    /**
     * Initializes the control for choosing/creating maps
     */
    public void initMapSelector() {
        mapCombo = new ComboBox<>();
        mapCombo.setEditable(true);
        mapCombo.setPromptText("Type map name...");
        // Listen for "Enter" key presses
        mapCombo.valueProperty().addListener(getMapSelectionListener());
    }
    
    /**
     * Initializes the control for choosing/creating maps
     */
    public void initRouteSelector() {
        routeCombo = new CheckComboBox<>();
        // Make sure window resizes with combo box size changes due to display additions
        routeCombo.layoutBoundsProperty().addListener((v, o, n) -> {
            Window w = routeFlyout.getFlyoutContainer().getScene().getWindow();
            if(w != null) {
                w.setWidth(w.getWidth() + (n.getWidth() - o.getWidth()));
            }
        });
        
        //routeCombo.setEditable(true);
        //routeCombo.setPromptText("Type route name...");
        //routeCombo.valueProperty().addListener(getRouteSelectionListener());
    }
    
    /**
     * Creates and returns the control for loading maps
     * @return  the control for loading stored maps
     */
    public GridPane getMapControl() {
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(5, 5, 5, 5));
        gp.setHgap(5);
        gp.setVgap(5);
        
        Button gpxLoader = getGPXLoadControl();
        gpxLoader.setPrefHeight(15);
        
        Label l = new Label("Select or enter map name:");
        l.setFont(Font.font(l.getFont().getFamily(), 12));
        l.setTextFill(Color.WHITE);
        
        Button add = new Button("Add");
        add.disableProperty().set(true);
        add.setOnAction(e -> mapCombo.valueProperty().set(mapCombo.getEditor().getText()));
        
        Button clr = new Button("Clear map");
        clr.disableProperty().set(true);
        clr.setOnAction(e -> map.clearMap());
        
        Button del = new Button("Delete map");
        del.disableProperty().set(true);
        del.setOnAction(e -> {
            map.clearMap();
            map.deleteMap(mapCombo.getEditor().getText());
            mapCombo.getItems().remove(mapCombo.getEditor().getText());
            mapCombo.getSelectionModel().clearSelection();
            mapCombo.getEditor().clear();
            map.setMode(Mode.NORMAL);
            mapChooser.fire();
            map.setOverlayVisible(true);
        });
        
        mapCombo.getEditor().textProperty().addListener((v, o, n) -> {
            if(mapCombo.getEditor().getText() != null && mapCombo.getEditor().getText().length() > 0) {
                add.disableProperty().set(false);
                if(map.getMapStore().getMap(mapCombo.getEditor().getText()) != null) {
                    clr.disableProperty().set(false);
                    del.disableProperty().set(false);
                }else{
                    clr.disableProperty().set(true);
                    del.disableProperty().set(true);
                }
            }else{
                add.disableProperty().set(true);
                clr.disableProperty().set(true);
                del.disableProperty().set(true);
            }
        });
        
        gp.add(gpxLoader, 0, 0, 2, 1);
        gp.add(new Separator(), 0, 1, 2, 1);
        gp.add(l, 0, 2, 2, 1);
        gp.add(mapCombo, 0, 3, 3, 1);
        gp.add(add, 3, 3);
        gp.add(clr, 4, 3);
        gp.add(del, 5, 3);
        
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
        gp.setVgap(5);
        
        newRouteField = new TextField();
        newRouteField.setDisable(true);
        newRouteField.setOnAction(e -> {
            newRouteField.setDisable(true);
            String text = newRouteField.getText();
            newRouteField.clear();
            if(routeCombo.getItems().contains(text)) return;
            routeCombo.getItems().add(text);
            map.addRoute(new Route(text));
        });
        
        Button addNew = new Button("New");
        addNew.setOnAction(e -> {
            newRouteField.setDisable(false);
            newRouteField.requestFocus();
        });
        
        HBox rsMode = new HBox();
        addWaypointBox = new CheckBox("Add Waypoints");
        addWaypointBox.setTextFill(Color.WHITE);
        addWaypointBox.setOnAction(e -> {
            map.setMode(addWaypointBox.isSelected() ? Mode.ADD_WAYPOINTS : Mode.NORMAL);
        });
        rsMode.getChildren().add(addWaypointBox);
        Label l = new Label("Select or enter route name:");
        l.setFont(Font.font(l.getFont().getFamily(), 12));
        l.setTextFill(Color.WHITE);
        Button add = new Button("Add");
        add.setOnAction(e -> {
            map.eraseMap();
            List<String> items = routeCombo.getCheckModel().getCheckedItems();
            List<Route> routes = null;
            PersistentMap selectedMap = map.getMapStore().getMap(map.getMapStore().getSelectedMapName());
            if(items != null && items.size() > 0) {
                routes = items.stream().map(s -> selectedMap.getRoute(s)).collect(Collectors.toList());
            }else{
                return;
            }
            map.displayRoutes(routes);
        });
        Button clr = new Button("Clear route");
        //clr.setOnAction(e -> map.clearRoute(map.getRoute(routeCombo.getSelectionModel().getSelectedItem())));
        Button del = new Button("Delete route");
        //del.setOnAction(e -> map.removeRoute(map.getRoute(routeCombo.getSelectionModel().getSelectedItem())));
        
        gp.add(l, 0, 0, 2, 1);
        gp.add(newRouteField, 0, 1, 2, 1);
        gp.add(addNew, 2, 1, 1, 1);
        gp.add(new Separator(), 0, 2, 5, 1);
        gp.add(rsMode, 2, 0, 2, 1);
        gp.add(routeCombo, 0, 3, 2, 1);
        gp.add(add, 2, 3);
        gp.add(clr, 3, 3);
        gp.add(del, 4, 3);
        
        return gp;
    }
    
    public void mapSelected(PersistentMap persistentMap) {
        if(mapChooser.isSelected()) {
            mapFlyout.getFlyoutStatusProperty().addListener(local = (v, o, n) -> {
                
                if(persistentMap.getRoutes().isEmpty() && n == Flyout.Status.COMPLETE) {
                    
                    if(!routeFlyout.flyoutShowing()) {
                        mapFlyout.getFlyoutStatusProperty().removeListener(local);
                        Platform.runLater(() -> {
                            newRouteField.setText(DEFAULT_ROUTE_NAME);
                            newRouteField.requestFocus();
                            newRouteField.selectAll();
                        });
                        
                        routeChooser.fire();
                    }
                    
                    addWaypointBox.setSelected(true);
                    map.setMode(Mode.ADD_WAYPOINTS);
                }else if(n == Flyout.Status.COMPLETE){
                    mapFlyout.getFlyoutStatusProperty().removeListener(local);
                    routeCombo.getItems().clear();
                    
                    String[] sa = persistentMap.getRoutes().stream()
                        .map(r -> r.getName())
                        .filter(name -> !routeCombo.getItems().contains(name))
                        .toArray(String[]::new);
                    routeCombo.getItems().addAll(sa);
                    routeCombo.getCheckModel().check(0);
                    
                    map.refresh();
                 }
            });
            
            mapChooser.fire();            
        }
        
        map.refresh();
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
        routeCombo.getCheckModel().clearChecks();
        routeCombo.getItems().clear();
        
        mapCombo.getSelectionModel().select(name);
        
        // IMPORTANT: Point the MapStore to the desired map selection
        try {
            map.getMapStore().selectMap(name);
        }catch(Exception e) { 
            e.printStackTrace();
        }
    }
    
    public void clearMapSelection() {
        mapCombo.getSelectionModel().clearSelection();
        map.setOverlayVisible(true);
    }
    
    public void removeMapListing(String mapName) {
        mapCombo.getItems().remove(mapName);
    }
    
    /**
     * Returns the handler which responds to the map combo box selection.
     * 
     * @return  the map combo handler
     */
    public ChangeListener<String> getMapSelectionListener() {
        return (v, o, n) -> {
            if(n == null) return;
            parent.createOrSelectMap(n);
        };
    }
    
    /**
     * Adds the Route to the route combo in the toolbar
     * @param routeName the name of the route to add
     */
    public void addRoute(String routeName) {
        if(!containsRoute(routeName)) {
            routeCombo.getItems().add(routeName);
        }
    }
    
    /**
     * Returns a flag indicating whether the route exists in the combo model
     * @param name  the name to check
     * @return  true if it exists, false if not
     */
    public boolean containsRoute(String name) {
        return routeCombo.getItems().contains(name);
    }
    
    /**
     * Selects the route specified by name and changes the mode to 
     * {@link Map.Mode#ADD_WAYPOINTS}; then displays the route.
     * 
     * @param name  the name of the route to set as "currentRoute"
     */
    public void selectRoute(String name) {
        routeCombo.getCheckModel().check(name);
        
        // Display the route
        PersistentMap pm = map.getMapStore().getMap(map.getMapStore().getSelectedMapName());
        Route r = pm.getRoute(name);
        map.displayRoute(r);
        
        // Set the MarkerType index to the selected route's last marker letter
        if(r.getDestination() != null) {
            MarkerType.reset(pm.getRoute(name));
        }
    }
    
    /**
     * Returns the handler which responds to the route combo box selection.
     * 
     * @return  the map combo handler
     */
    public ChangeListener<String> getRouteSelectionListener() {
        return (v, o, n) -> {
            if(n == null) return;
            parent.createOrSelectRoute(n);
        };
    }
    
    
}

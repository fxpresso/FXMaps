package ai.cogmission.fxmaps.demo;

import java.io.File;
import java.util.Collection;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
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
    
    private Stage popup;
    private GridPane loadPane;
    private ComboBox<String> mapCombo;
    private ToggleButton flyout;
    
    private boolean shownOnce;
    
    public RefImplToolBar(RefImpl parent, Map map) {
        this.parent = parent;
        this.map = map;
        initMapSelector();
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
            flyout = new ToggleButton("Create / Select Map")
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
        flyout.setOnAction(e -> flyOut());
    }
    
    Pane pn;
    StackPane sp = new StackPane();
    public void flyOut() {
        if(!flyout.isSelected()) {
            System.out.println("got click");
            popup.hide();
            flyout.setSelected(false);
        }else{
            
            if(!shownOnce) {
                sp = new StackPane();
                sp.setPrefSize(RefImplToolBar.this.getWidth(), 100);
                
                pn = new Pane();
                pn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");
                loadPane = getLoadControl();
                pn.setManaged(false);
                pn.setVisible(true);
                pn.getChildren().add(loadPane);
                
                sp.getChildren().add(pn);
                sp.setLayoutX(0);
                sp.setLayoutY(0);
                sp.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0)");
                popup.setHeight(100);
                popup.setWidth(RefImplToolBar.this.getWidth());
                
                Scene popupScene = new Scene(sp, Color.TRANSPARENT);
                popup.initStyle(StageStyle.TRANSPARENT);
                popup.initOwner(parent.getPrimaryStage());
                popup.setScene(popupScene);
                popup.setOnShown(e -> {
                    if(!shownOnce) {
                        pn.resize(loadPane.getWidth(), loadPane.getHeight());
                        loadPane.relocate(0, pn.getHeight() - loadPane.getHeight());
                        pn.requestLayout();
                    }
                    
                    
                    Point2D fp = flyout.localToScene(0.0, RefImplToolBar.this.getLayoutBounds().getHeight() +
                        RefImplToolBar.this.getInsets().getBottom() + RefImplToolBar.this.getInsets().getTop());
                    pn.setLayoutX(fp.getX());
                    pn.setLayoutY(-pn.getHeight());
                    popup.setX(parent.getPrimaryStage().getX());
                    popup.setY(parent.getPrimaryStage().getY() + fp.getY());
                    shownOnce = true;
                    
                });
            }
            //popup.initModality(Modality.WINDOW_MODAL);
            
            
            popup.show();
            doFlyIn();
        }
        
    }
    
    Timeline tl = new Timeline();
    public void doFlyIn() {
        popup.show();
        tl.setCycleCount(1);
        double currentY = pn.getLayoutY() ;
//        Point2D fp = flyout.localToScene(0.0, RefImplToolBar.this.getLayoutBounds().getMaxY());
        double destY = currentY + pn.getHeight();//parent.getPrimaryStage().getY() + fp.getY() + flyout.getScene().getY();
        DoubleProperty y = new SimpleDoubleProperty(currentY);
        y.addListener((obs, oldY, newY) -> {
            pn.setLayoutY(newY.doubleValue());
            //Bounds b = RefImplToolBar.this.getLayoutBounds();
            //Rectangle visibleArea = new Rectangle(0, destY, b.getWidth(), b.getHeight());
            //pn.setClip(visibleArea);
        });
        
        
        
        
        Interpolator interpolator = Interpolator.SPLINE(0.5, 0.1, 0.1, 0.5);
        KeyValue keyValue = new KeyValue(y, destY, interpolator);
        //create a keyFrame with duration 4s
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), keyValue);
        //add the keyframe to the timeline
        tl.getKeyFrames().add(keyFrame);
        
        tl.play();
    }
    
    
    public void initMapSelector() {
        popup = new Stage();
        mapCombo = new ComboBox<>();
        mapCombo.setEditable(true);
        mapCombo.setPromptText("Type or select map name.");
        mapCombo.valueProperty().addListener(parent.getMapSelectionListener());
    }
    
    /**
     * Creates and returns the control for loading maps
     * @return  the control for loading stored maps
     */
    public GridPane getLoadControl() {
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(5, 5, 5, 5));
        gp.setHgap(5);
        
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

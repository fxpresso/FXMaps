package ai.cogmission.fxmaps.demo;

import java.io.File;
import java.util.Collection;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
import ai.cogmission.fxpresso.ui.Flyout;

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
    
    private Flyout flyover;
    private ToggleButton mapChooser;
    
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
            //flyout = new ToggleButton("Create / Select Map")
            flyover = new Flyout(mapChooser = new ToggleButton("Create / Select Map"), getLoadControl())
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
        //flyout.setOnAction(e -> flyOut());
        mapChooser.setOnAction(e -> {
            if(flyover.flyoutShowing()) {
                flyover.dismiss();
            }else{
                flyover.flyOut();
            }
        });
    }
    
    Pane userNodeContainer;
    StackPane clipContainer = new StackPane();
    public void flyOut() {
        if(!flyout.isSelected()) {
            System.out.println("got click");
            popup.hide();
            flyout.setSelected(false);
        }else{
            
            if(!shownOnce) {
                clipContainer = new StackPane();
                
                userNodeContainer = new Pane();
                userNodeContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");
                loadPane = getLoadControl();
                userNodeContainer.setManaged(false);
                userNodeContainer.setVisible(true);
                userNodeContainer.getChildren().add(loadPane);
                
                clipContainer.getChildren().add(userNodeContainer);
                clipContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0)");
                
                Scene popupScene = new Scene(clipContainer, Color.TRANSPARENT);
                popup.initStyle(StageStyle.TRANSPARENT);
                popup.initOwner(parent.getPrimaryStage());
                popup.setScene(popupScene);
                
                popup.setOnShown(e -> {
                    Point2D fp = flyout.localToScreen(0.0, 0.0);
                    
                    if(!shownOnce) {
                        userNodeContainer.resize(loadPane.getWidth(), loadPane.getHeight());
                        
                        clipContainer.setLayoutX(0);
                        clipContainer.setLayoutY(0);
                        userNodeContainer.setLayoutX(0);
                    }
                    
                    //System.out.println("flyout size = " + flyout.getLayoutBounds() + ", fp = " + fp);
                    clipContainer.resize(loadPane.getLayoutBounds().getWidth(), loadPane.getLayoutBounds().getHeight());
                    clipContainer.setVisible(true);
                    
                    userNodeContainer.setLayoutY(-userNodeContainer.getHeight());
                    
                    System.out.println("popup = " + popup.getX() + " ,  " + popup.getY() + ",  " + popup.getWidth() + ",  " + popup.getHeight());
                    System.out.println("pn = " + userNodeContainer.getLayoutX() + " ,  " + userNodeContainer.getLayoutY() + ",  " + userNodeContainer.getWidth() + ", " + userNodeContainer.getHeight());
                    System.out.println("sp = " + clipContainer.getLayoutX() + " ,  " + clipContainer.getLayoutY() + ",  " + clipContainer.getWidth() + ", " + clipContainer.getHeight());
                    System.out.println("loadpane = " + loadPane.getWidth() + ", " + loadPane.getHeight());
                    
                    clipContainer.requestLayout();
                    popup.setX(fp.getX());
                    popup.setY(fp.getY() + flyout.getHeight());
                    popup.setWidth(loadPane.getWidth());
                    popup.setHeight(loadPane.getHeight());
                    clipContainer.requestFocus();
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
        double currentY = userNodeContainer.getLayoutY() ;
        double destY = currentY + userNodeContainer.getHeight();
        DoubleProperty y = new SimpleDoubleProperty(currentY);
        y.addListener((obs, oldY, newY) -> {
            userNodeContainer.setLayoutY(newY.doubleValue());
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
        
        Label l = new Label("Select or enter map name:");
        l.setFont(Font.font(l.getFont().getFamily(), 10));
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

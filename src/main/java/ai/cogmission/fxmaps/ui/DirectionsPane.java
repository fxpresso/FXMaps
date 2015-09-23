package ai.cogmission.fxmaps.ui;

import java.util.List;

import ai.cogmission.fxmaps.model.DirectionsLeg;
import ai.cogmission.fxmaps.model.DirectionsStep;
import ai.cogmission.fxmaps.model.Waypoint;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Right pane which displays directions arranged in vertical panels for
 * showing information for each {@link Waypoint} and each {DirectionsLeg}
 * along a given {@link Route}
 * 
 * @author cogmission
 *
 */
public class DirectionsPane extends ScrollPane {
    /** Container for labels and panes arranged vertically in display */
    private VBox directionsBox;
    
    /**
     * Constructs a new {@code DirectionsPane}
     */
    public DirectionsPane() {
        directionsBox = new VBox();
        directionsBox.setPadding(new Insets(5,5,5,5));
        
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setStyle("-fx-background-fill: rgb(255,255,255);");
         
        directionsBox.maxWidthProperty().bind(widthProperty().subtract(10));
        
        setContent(directionsBox);
    }
    
    /**
     * Adds a notification located at the top of the vertical pane, which 
     * displays warning or other information about the current API state.
     * (i.e. if its in Beta etc.)
     * 
     * @param message   the message to display
     */
    public void addDirectionsBulletinPane(String message) {
        
    }
    
    /**
     * Adds a Pane to the vertical display which displays the street address
     * of the specified {@link Waypoint}
     * 
     * @param waypoint  the waypoint whose address is displayed.
     */
    public void addDirectionsLocationPane(Waypoint waypoint) {
        
    }
    
    /**
     * Removes the Pane displaying the location information.
     * 
     * @param waypoint  the waypoint whose location is to be removed.
     */
    public void removeDirectionsLocationPane(Waypoint waypoint) {
        
    }
    
    /**
     * Adds a Pane to display the steps involved in proceeding to the
     * next waypoint location. Delegates to the single {@link #addDirectionsLeg(DirectionsLeg)}
     * method which adds a given leg.
     * 
     * @param legs  a list of {@link DirectionsLeg}s to add
     */
    public void addDirectionsLegs(List<DirectionsLeg> legs) {
        
    }
    
    /**
     * Removes the specified list legs from the display by delegating to
     * the single {@link #removeDirectionsLeg(DirectionsLeg)} method.
     * 
     * @param legs  list of legs to remove
     */
    public void removeDirectionsLegs(List<DirectionsLeg> legs) {
        
    }
    
    /**
     * Adds the specified {@link DirectionsLeg} to this {@code DirectionsPane}.
     * 
     * @param leg   the leg to add
     */
    public void addDirectionsLeg(DirectionsLeg leg) {
        
    }
    
    /**
     * Removes the specified {@link DirectionsLeg} from this {@code DirectionsPane}.
     * 
     * @param leg   the leg to remove
     */
    public void removeDirectionsLeg(DirectionsLeg leg) {
        
    }
    
    /**
     * Adds a {@link DirectionsStepPane} to this vertical display. This step
     * pane is the pane which displays steps taken to implement a given leg.
     * 
     * @param step  a {@link DirectionsStep} object.
     */
    public void addDirectionsStepPane(DirectionsStep step) {
        
    }
    
    /**
     * Removes the {@link DirectionsStepPane} associated with the specified 
     * {@link DirectionsStep}
     * 
     * @param step  a {@link DirectionsStep} object.
     */
    public void removeDirectionsStepPane(DirectionsStep step) {
        
    }
}


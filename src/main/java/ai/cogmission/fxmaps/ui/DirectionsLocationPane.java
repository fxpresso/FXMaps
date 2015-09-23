package ai.cogmission.fxmaps.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Displays the address information for a given waypoint.
 * 
 * @author cogmission
 * @see Wayoint
 */
public class DirectionsLocationPane extends Label {
    /**
     * Creates a new {@code DirectionsLocationPane}
     * 
     * @param address
     * @param imageView
     */
    DirectionsLocationPane(String address, ImageView imageView) {
        super(address, imageView);
    
        setBackground(new Background(
            new BackgroundFill(Color.LIGHTGRAY,
                new CornerRadii(5), 
                null)));
        setWrapText(true);
        setPrefWidth(200);
        setPadding(new Insets(5,5,5,5));
        setBorder(new Border(new BorderStroke(
            Color.GRAY, new BorderStrokeStyle(null, null, null, 10, 0, null), 
                null, null)));
    }
}

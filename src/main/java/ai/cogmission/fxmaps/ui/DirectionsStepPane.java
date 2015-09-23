package ai.cogmission.fxmaps.ui;

import java.util.ArrayList;
import java.util.List;

import ai.cogmission.fxmaps.model.Direction;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


/**
 * The "action" portion of a given directions route is displayed
 * on one of these.
 * 
 * @author cogmission
 *
 */
public class DirectionsStepPane extends HBox {
    private static String imagePath = "http://metaware.us/images/markers/all_directions.png";
    private Direction dir;
    private String text;
    private ImageView view;
    private TextFlow textFlow;
    
    /**
     * Constructs a new {@code DirectionsStepPane}
     * 
     * @param d     {@link Direction} enum to determine icon
     * @param text  the actual directions
     */
    public DirectionsStepPane(Direction d, String text) {
        this.dir = d == null ? Direction.HEAD : d;
        this.text = text;
        view = getImageView(dir);
        textFlow = parseText();
        getChildren().addAll(view, textFlow);
    }
    
    /**
     * Returns an {@link ImageView} node capable of being displayed
     * in this {@code DirectionsStepPane}
     * 
     * @param d     the {@link Direction} enum indicating icon subsection
     * @return      the constructed {@link ImageView}
     */
    private ImageView getImageView(Direction d) {
        ImageView iv = new ImageView(imagePath);
        iv.setViewport(new Rectangle2D(0, d.index(), d.w(), d.h()));
        iv.fitWidthProperty().set(22);
        iv.fitHeightProperty().set(22);
        return iv;
    }
    
    /**
     * Parses the specified String to return the portion not containing
     * the destination statement. This is needed so that the main text
     * which when parsed, indicates the direction icon that will be used;
     * will be the only determiner of the associated directions icon. The
     * destination text has other directions which will confuse the parsing
     * which determines the icon. (i.e. The destination will be on the right).
     * 
     * @param text      the entire directions text returned by the Google-Maps API.
     * @return      the String minus the destination statement (if it exists).
     */
    private String excludeDestinationStatement(String text) {
        int idx = -1;
        if((idx = text.indexOf("Destination will be")) != -1) {
            text = text.substring(0, idx);
        }
        return text;
    }
    
    /**
     * Parses the text and returns a {@link TextFlow} node containing the proper
     * icon and the directions text which is used to display the 
     * directions information.
     *  
     * @return  the constructed {@link TextFlow}
     */
    private TextFlow parseText() {
        String testText = excludeDestinationStatement(text);
        
        TextFlow tf = new TextFlow();
        List<Text> tList = new ArrayList<Text>();
        int len = testText.length();
        int start = 0;
        int idx = 0;
        boolean isBold = false;
        
        while((idx = testText.indexOf("<", start)) != len) {
            if(idx == -1) {
                idx = len;
            }
            Text segment = new Text(testText.substring(start, idx));
            if(isBold) {
                segment.setFont(Font.font(
                    segment.getFont().getFamily(), FontWeight.BOLD, segment.getFont().getSize()));
                isBold = false;
            }
            tList.add(segment);
            
            start = testText.indexOf(">", idx) + 1;
            
            if(idx < len && text.substring(idx, idx + 2).indexOf("<b") != -1) {
                isBold = true;
            }
            
            if(idx == len) break;
        }
        
        if(text.length() != testText.length()) {
            tList.add(new Text(" " + text.indexOf("Destination")));
        }
        
        tf.getChildren().addAll(tList);
        
        return tf;
    }
}

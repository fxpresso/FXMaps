package ai.cogmission.fxmaps.event;

import javafx.scene.web.WebView;
import ai.cogmission.fxmaps.ui.Map;

import com.lynden.gmapsfx.javascript.object.MapOptions;

/**
 * For clients interested in notifications for map initialization.
 * Notifies listeners when the {@link Map} has been loaded into the {@link WebView},
 * as opposed to the {@link MapReadyListener} which is notified when the {@link MapOptions}
 * have been applied to the {@link Map} indicating the necessary members have been 
 * added to the underlying java script dom.
 * 
 * @author cogmission
 * @see MapReadyListener
 */
public interface MapInitializedListener {
    /**
     * Notifies map loaded by {@link WebView}
     */
    public void mapInitialized();
}

package ai.cogmission.fxmaps.event;

import netscape.javascript.JSObject;

import com.lynden.gmapsfx.javascript.event.UIEventHandler;

/**
 * Handles Google Maps UI events.
 * 
 * @author Geoff Capper
 * @author cogmission
 */
public interface MapEventHandler extends UIEventHandler {
    /**
     * @param obj This object may be either a MouseEvent or one of it's subclasses.
     */
    public void handle(JSObject obj);
}

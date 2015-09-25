package ai.cogmission.fxmaps.event;

import javafx.scene.web.WebView;

import com.lynden.gmapsfx.javascript.event.UIEventType;

/**
 * Event type for registering Javascript events on {@link WebView}
 * content passed to Google Maps APIs to process UI events.
 * 
 * @author cogmission
 */
public enum MapEventType {
    CLICK(UIEventType.click), 
    DBLCLICK(UIEventType.dblclick), 
    MOUSEMOVE(UIEventType.mousemove), 
    MOUSEUP(UIEventType.mouseup), 
    MOUSEDOWN(UIEventType.mousedown), 
    MOUSEOVER(UIEventType.mouseover), 
    MOUSEOUT(UIEventType.mouseout), 
    RIGHTCLICK(UIEventType.rightclick);
    
    UIEventType gmapsfxType;
    
    /**
     * Enum constructor
     * @param type  the GMapsFX event type
     */
    private MapEventType(UIEventType type) {
        this.gmapsfxType = type;
    }
    
    /**
     * Returns a GMapsFX event type.
     * @return  a GMapsFX event type.
     */
    public UIEventType convert() {
        return gmapsfxType;
    }
}

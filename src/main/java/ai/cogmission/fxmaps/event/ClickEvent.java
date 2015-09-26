package ai.cogmission.fxmaps.event;

import ai.cogmission.fxmaps.model.LatLon;


public class ClickEvent {
    private MapEventType type;
    private LatLon where;
    
    public LatLon where() {
        return where;
    }
    
    public MapEventType type() {
        return type;
    }
}

package ai.cogmission.fxmaps.model;

import ai.cogmission.fxmaps.event.MapEventHandler;
import ai.cogmission.fxmaps.ui.Map;

import com.lynden.gmapsfx.javascript.JavascriptObject;

/**
 * Indicates a type that can act as a source for a {@link MapEventHandler}
 * see {@link Map#addMapEventHandler(MapObject, ai.cogmission.fxmaps.event.MapEventType, MapEventHandler)}
 * 
 * @author cogmission
 */
public interface MapObject {
    public JavascriptObject convert();
    
    /**
     * Calls the inheritor to return the proper {@link JavascriptObject}
     * @return
     */
    public default JavascriptObject get() {
        return convert();
    }
}

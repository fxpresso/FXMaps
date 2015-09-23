package ai.cogmission.fxmaps.event;

/**
 * A MapReadyListener is called when the MapOptions have been loaded and the 
 * Javascript map object is set up ready to be accessed. While the MapType is 
 * null many methods will cause errors, so this listener is notified when 
 * projection_changed returns a non-null value, as this is expected to happen 
 * after the MapType is set.
 * 
 * @author cogmission
 *
 */
public interface MapReadyListener {
    public void mapRead();
}

package ai.cogmission.fxmaps.model;


/**
 * Stores display properties for a given {@link Marker}.
 * 
 * @author Rob Terpilowski
 * @author cogmission
 *
 */
public class MarkerOptions implements MapObject {
    private com.lynden.gmapsfx.javascript.object.MarkerOptions options;
    
    
    public MarkerOptions() {
        options = new com.lynden.gmapsfx.javascript.object.MarkerOptions();
    }
    
    public MarkerOptions position(LatLon ll) {
        options.position(ll.toLatLong());
        return this;
    }
    
    public MarkerOptions title(String title) {
        options.title(title);
        return this;
    }
    
    public MarkerOptions visible(Boolean visible) {
        options.visible(visible);
        return this;
    }
    
    public MarkerOptions icon(String iconPath) {
        options.icon(iconPath);
        return this;
    }
    
    public MarkerOptions animation(Animation animation) {
        options.animation(animation.convert());
        return this;
    }
    
    public com.lynden.gmapsfx.javascript.object.MarkerOptions convert() {
        return options;
    }
}

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
    
    private boolean isExternal;
    private LatLon position;
    private String title;
    private boolean isVisible;
    private String iconPath;
    private Animation animation = Animation.NULL;
    
    public MarkerOptions() {
        this(false);
    }

    public MarkerOptions(boolean isExternal) {
        this.isExternal = isExternal;
        if(!isExternal) {
            options = new com.lynden.gmapsfx.javascript.object.MarkerOptions();
        }
    }
    
    public boolean isExternal() {
        return isExternal;
    }
    
    public MarkerOptions position(LatLon ll) {
        this.position = ll;
        if(!isExternal) {
            options.position(ll.toLatLong());
        }
        return this;
    }
    
    public LatLon getPosition() {
        return position;
    }
    
    public MarkerOptions title(String title) {
        this.title = title;
        if(!isExternal) {
            options.title(title);
        }
        return this;
    }
    
    public String getTitle() {
        return title;
    }
    
    public MarkerOptions visible(Boolean visible) {
        this.isVisible = visible;
        if(!isExternal) {
            options.visible(visible);
        }
        return this;
    }
    
    public boolean isVisible() {
        return isVisible;
    }
    
    public MarkerOptions icon(String iconPath) {
        this.iconPath = iconPath;
        if(!isExternal) {
            options.icon(iconPath);
        }
        return this;
    }
    
    public String getIcon() {
        return iconPath;
    }
    
    public MarkerOptions animation(Animation animation) {
        this.animation = animation;
        if(!isExternal) {
            options.animation(animation.convert());
        }
        return this;
    }
    
    public Animation getAnimation() {
        return animation;
    }
    
    public void createUnderlying() {
        options = new com.lynden.gmapsfx.javascript.object.MarkerOptions()
            .title(title)
            .position(position.toLatLong())
            .visible(isVisible);
        if(animation != null) {
            options.animation(animation.convert());
        }
        if(iconPath != null) {
            options.icon(iconPath);
        }
    }
    
    public com.lynden.gmapsfx.javascript.object.MarkerOptions convert() {
        return options;
    }
}

package ai.cogmission.fxmaps.model;

import javafx.application.Platform;

import com.google.gson.annotations.SerializedName;


/**
 * Stores display properties for a given {@link Marker}.
 * 
 * @author Rob Terpilowski
 * @author cogmission
 *
 */
public class MarkerOptions implements MapObject {
    private com.lynden.gmapsfx.javascript.object.MarkerOptions options;
    private LatLon position;
    private String title;
    private boolean visible;
    @SerializedName("icon")
    private String iconPath;
    private Animation animation = Animation.NULL;
    
    public MarkerOptions() {
        if(Platform.isFxApplicationThread()) {
            options = new com.lynden.gmapsfx.javascript.object.MarkerOptions();
        }
    }

    public MarkerOptions position(LatLon ll) {
        this.position = ll;
        if(Platform.isFxApplicationThread()) {
            options.position(ll.toLatLong());
        }
        return this;
    }
    
    public LatLon getPosition() {
        return position;
    }
    
    public MarkerOptions title(String title) {
        this.title = title;
        if(Platform.isFxApplicationThread()) {
            options.title(title);
        }
        return this;
    }
    
    public String getTitle() {
        return title;
    }
    
    public MarkerOptions visible(Boolean visible) {
        this.visible = visible;
        if(Platform.isFxApplicationThread()) {
            options.visible(visible);
        }
        return this;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public MarkerOptions icon(String iconPath) {
        this.iconPath = iconPath;
        if(Platform.isFxApplicationThread()) {
            options.icon(iconPath);
        }
        return this;
    }
    
    public String getIcon() {
        return iconPath;
    }
    
    public MarkerOptions animation(Animation animation) {
        this.animation = animation;
        if(Platform.isFxApplicationThread()) {
            options.animation(animation.convert());
        }
        return this;
    }
    
    public Animation getAnimation() {
        return animation;
    }
    
    public void createUnderlying() {
        position = new LatLon(position.getLatitude(), position.getLongitude());
        
        options = new com.lynden.gmapsfx.javascript.object.MarkerOptions()
            .title(title)
            .position(position.toLatLong())
            .visible(visible);
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

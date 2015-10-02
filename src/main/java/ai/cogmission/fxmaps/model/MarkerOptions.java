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
        if(Platform.isFxApplicationThread()) {
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
    }
    
    public com.lynden.gmapsfx.javascript.object.MarkerOptions convert() {
        return options;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((iconPath == null) ? 0 : iconPath.substring(0, iconPath.indexOf("_")).hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        MarkerOptions other = (MarkerOptions)obj;
        if(iconPath == null) {
            if(other.iconPath != null)
                return false;
        } else if(!iconPath.substring(0, iconPath.indexOf("_")).equals(other.iconPath.substring(0, iconPath.indexOf("_"))))
            return false;
        if(position == null) {
            if(other.position != null)
                return false;
        } else if(!position.equals(other.position))
            return false;
        return true;
    }
}

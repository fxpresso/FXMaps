package ai.cogmission.fxmaps.model;

import javafx.application.Platform;

import com.google.gson.annotations.SerializedName;

/**
 * Map object pointer display
 * 
 * @author Rob Terpilowski
 * @author cogmission
 *
 */
public class Marker implements MapObject {
    private com.lynden.gmapsfx.javascript.object.Marker marker;
    
    private String title;
    private Animation animation = Animation.NULL;
    
    @SerializedName("markerOptions")
    private MarkerOptions options;
    
    public Marker(MarkerOptions options) {
        this.options = options;
        this.title = options.getTitle();
        if(Platform.isFxApplicationThread()) {
            this.marker = new com.lynden.gmapsfx.javascript.object.Marker(options.convert());
        }
    }
    
    /**
     * Sets the title of this Marker
     * @param title The Marker's new title
     */
    public void setTitle( String title ) {
        marker.setTitle(title);
        this.title = title;
    }
    
    /**
     * Returns the title of this {@code Marker}
     * @return
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets how the marker should be animated.  To clear the animation use Animation.NULL
     * @param animation The animation to use for this marker.
     */
    public void setAnimation(Animation animation) {
        marker.setAnimation(animation.convert());
    }
    
    /**
     * Returns the {@link Animation} to use.
     * @return
     */
    public Animation getAnimation() {
        return animation;
    }
    
    /**
     * Returns the {@link MarkerOptions}
     * @return
     */
    public MarkerOptions getMarkerOptions() {
        return options;
    }
    
    /**
     * Creates the underlying javascript peers
     */
    public void createUnderlying() {
        if(Platform.isFxApplicationThread()) {
            options.createUnderlying();
            this.marker = new com.lynden.gmapsfx.javascript.object.Marker(options.convert());
        }
    }
    
    /**
     * Internally used to convert from this model to a GMapsFX model.
     * @return
     */
    public com.lynden.gmapsfx.javascript.object.Marker convert() {
        return marker;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((options == null) ? 0 : options.hashCode());
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
        Marker other = (Marker)obj;
        if(options == null) {
            if(other.options != null)
                return false;
        } else if(!options.equals(other.options))
            return false;
        return true;
    }
    
}

package ai.cogmission.fxmaps.model;



/**
 * Specifies fundamental Map configurations for map controls (on the map).
 * 
 * @author cogmission
 */
public class MapOptions {
    protected LatLon center;
    protected MapType mapType;
    protected boolean mapMarker;
    protected boolean overviewMapControl;
    protected boolean panControl;
    protected boolean rotateControl;
    protected boolean scaleControl;
    protected boolean streetViewControl;
    protected int zoom;
    protected boolean zoomControl;
    protected boolean mapTypeControl;
    
    /**
     * Sets the latitude/longitude the map will initially
     * center around.
     * 
     * @param center    the center coordinates
     * @return  this MapOptions
     */
    public MapOptions center(LatLon center) {
        this.center = center;
        return this;
    }
    
    /**
     * Sets the flag indicating whether to include a google maps map marker
     * @param mapMarker     true if so, false if not
     * @return  this MapOptions
     */
    public MapOptions mapMarker(boolean mapMarker) {
        this.mapMarker = mapMarker;
        return this;
    }
    
    /**
     * Sets the {@link MapType} enum indicating the map type
     * @param mapType   the map type to use
     * @return  this MapOptions
     */
    public MapOptions mapType(MapType mapType) {
        this.mapType = mapType;
        return this;
    }
    
    /**
     * Sets the flag indicating whether to include a google maps overview control
     * @param overviewMapControl    true if so, false if not
     * @return  this MapOptions
     */
    public MapOptions overviewMapControl(boolean overviewMapControl) {
        this.overviewMapControl = overviewMapControl;
        return this;
    }
    
    /**
     * Sets the flag indicating whether to include a google maps pan control
     * @param panControl    true if so, false if not
     * @return  this MapOptions
     */
    public MapOptions panControl(boolean panControl) {
        this.panControl = panControl;
        return this;
    }
    
    /**
     * Sets the flag indicating whether to include a google maps rotate control
     * @param rotateControl true if so, false if not
     * @return  this MapOptions
     */
    public MapOptions rotateControl(boolean rotateControl) {
        this.rotateControl = rotateControl;
        return this;
    }
    
    /**
     * Sets the flag indicating whether to include a google maps scale control
     * @param scaleControl  true if so, false if not
     * @return  this MapOptions
     */
    public MapOptions scaleControl(boolean scaleControl) {
        this.scaleControl = scaleControl;
        return this;
    }
    
    /**
     * Sets the flag indicating whether to include a google maps street-view control
     * @param streetViewControl     true if so, false if not
     * @return  this MapOptions
     */
    public MapOptions streetViewControl(boolean streetViewControl) {
        this.streetViewControl = streetViewControl;
        return this;
    }
    
    /**
     * Sets the zoom level. Higher means more zoomed in.
     * default = 15
     * @param zoom  the zoom level      
     * @return  this MapOptions
     */
    public MapOptions zoom(int zoom) {
        this.zoom = zoom;
        return this;
    }
    
    /**
     * Sets the flag indicating whether to include a google maps zoom control
     * @param zoomControl   true if so, false if not
     * @return  this MapOptions
     */
    public MapOptions zoomControl(boolean zoomControl) {
        this.zoomControl = zoomControl;
        return this;
    }
    
    /**
     * Sets the flag indicating whether to include a google maps map-type control
     * @param mapTypeControl    true if so, false if not
     * @return  this MapOptions
     */
    public MapOptions mapTypeControl(boolean mapTypeControl) {
        this.mapTypeControl = mapTypeControl;
        return this;
    }
    
    /**
     * Returns a GMapsFX model MapOptions
     * @return  a GMapsFX model MapOptions
     */
    public com.lynden.gmapsfx.javascript.object.MapOptions convert() {
        com.lynden.gmapsfx.javascript.object.MapOptions ops = new com.lynden.gmapsfx.javascript.object.MapOptions()
            .mapMarker(mapMarker)
            .overviewMapControl(overviewMapControl)
            .panControl(panControl)
            .rotateControl(rotateControl)
            .scaleControl(scaleControl)
            .streetViewControl(streetViewControl)
            .zoom(zoom == 0 ? 15 : zoom)
            .zoomControl(zoomControl)
            .mapTypeControl(mapTypeControl);
        if(center != null) {
            ops.center(center.toLatLong());
        }
        if(mapType != null) {
            ops.mapType(mapType.convert());
        }
        
        return ops;
    }
}

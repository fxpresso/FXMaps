package ai.cogmission.fxmaps.model;

import javafx.application.Platform;


public class LatLonBounds {
    private LatLon sw;
    private LatLon ne;
    
    private com.lynden.gmapsfx.javascript.object.LatLongBounds bounds;
    
    
    /**
     * Constructs a new {@code LatLonBounds}
     * 
     * @param sw    the lat/lon of the south west corner
     * @param ne    the lat/lon of the north east corner
     */
    public LatLonBounds(LatLon sw, LatLon ne) {
        this.sw = sw;
        this.ne = ne;
        
        if(Platform.isFxApplicationThread()) {
            bounds = new com.lynden.gmapsfx.javascript.object.LatLongBounds(this.sw.toLatLong(), this.ne.toLatLong());
        }
    }
    
    /**
     * Returns the lat/lon of the north east corner
     * @return  the lat/lon of the north east corner
     */
    public LatLon getNorthEast() {
        return ne;
    }
    
    /**
     * Returns the lat/lon of the south west corner
     * @return  the lat/lon of the south west corner
     */
    public LatLon getSouthWest() {
        return sw;
    }
    
    /**
     * Returns converted {@link LatLon}
     * @return  converted {@link LatLon}
     */
    public com.lynden.gmapsfx.javascript.object.LatLongBounds convert() {
        return bounds;
    }
    
    /**
     * Creates the underlying representation
     */
    public void createUnderlying() {
        bounds = new com.lynden.gmapsfx.javascript.object.LatLongBounds(this.sw.toLatLong(), this.ne.toLatLong());
    }
    
    @Override
    public String toString() {
        return "LatLonBounds[NE:" + getNorthEast() + ", SW:" + getSouthWest() + "]";
    }
}

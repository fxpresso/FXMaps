package ai.cogmission.fxmaps.model;

import javafx.application.Platform;


public class Polyline {
    private com.lynden.gmapsfx.shapes.Polyline line;
    
    private PolylineOptions options;
    
    public Polyline(PolylineOptions options) {
        this.options = options;
        
        if(Platform.isFxApplicationThread()) {
            line = new com.lynden.gmapsfx.shapes.Polyline(options.convert());
        }
    }
    
    /**
     * Returns the {@link PolylineOptions}
     * @return
     */
    public PolylineOptions getOptions() {
        return options;
    }
    
    /**
     * Creates the GMapsFX model Polyline
     */
    public void createUnderlying() {
        if(options != null) {
            line = new com.lynden.gmapsfx.shapes.Polyline(options.convert());
        }
    }
    
    /**
     * Returns the GMapsFX model Polyline
     * @return  the underlying Polyline
     */
    public com.lynden.gmapsfx.shapes.Polyline convert() {
        return line;
    }
}

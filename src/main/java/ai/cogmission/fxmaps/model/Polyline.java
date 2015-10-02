package ai.cogmission.fxmaps.model;

import javafx.application.Platform;


public class Polyline extends MapShape {
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
            options.createUnderlying();
            if(Platform.isFxApplicationThread()) {
                line = new com.lynden.gmapsfx.shapes.Polyline(options.convert());
            }
        }
    }
    
    /**
     * Returns the GMapsFX model Polyline
     * @return  the underlying Polyline
     */
    public com.lynden.gmapsfx.shapes.Polyline convert() {
        return line;
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
        Polyline other = (Polyline)obj;
        if(options == null) {
            if(other.options != null)
                return false;
        } else if(!options.equals(other.options))
            return false;
        return true;
    }
}

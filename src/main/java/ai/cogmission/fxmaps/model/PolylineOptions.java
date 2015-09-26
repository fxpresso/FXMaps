package ai.cogmission.fxmaps.model;

import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Platform;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MVCArray;


public class PolylineOptions extends MapShapeOptions<PolylineOptions> {
    private com.lynden.gmapsfx.shapes.PolylineOptions options;
    private List<LatLon> path;
    
    
    public PolylineOptions() {
        if(Platform.isFxApplicationThread()) {
            options = new com.lynden.gmapsfx.shapes.PolylineOptions();
        }
    }
    
    
    /**
     * Sets the list of lat/lon points on this {@code PolylineOptions}
     * @param pathPoints    the list of points
     * @return  this PolylineOptions
     */
    public PolylineOptions path(List<LatLon> pathPoints) {
        this.path = pathPoints;
        
        if(Platform.isFxApplicationThread()) {
            LatLong[] ary = pathPoints.stream()
                .map(ll -> new LatLong(ll.getLatitude(), ll.getLongitude()))
                .collect(Collectors.toList())
                .toArray(new LatLong[0]);
            MVCArray a = new MVCArray(ary);
            options.path(a);
        }
        
        return this;
    }
    
    /**
     * Creates the underlying PolylineOptions
     */
    public void createUnderlying() {
        options = new com.lynden.gmapsfx.shapes.PolylineOptions();
        
        if(path != null) {
            LatLong[] ary = path.stream()
                .map(ll -> new LatLong(ll.getLatitude(), ll.getLongitude()))
                .collect(Collectors.toList())
                .toArray(new LatLong[0]);
            MVCArray a = new MVCArray(ary);
            options.path(a);
        }
    }
    
    /**
     * Returns the list of lat/lons
     * @return
     */
    public List<LatLon> getPath() {
        return path;
    }
    
    /**
     * Returns the GMapsFX model PolylineOptions
     * @return
     */
    public com.lynden.gmapsfx.shapes.PolylineOptions convert() {
        return options;
    }
}

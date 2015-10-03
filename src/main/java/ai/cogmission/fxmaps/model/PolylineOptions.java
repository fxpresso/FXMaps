package ai.cogmission.fxmaps.model;

import java.util.ArrayList;
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
     * Returns a copy of the specified {@code PolylineOptions}
     * 
     * @param p options to copy
     */
    public static PolylineOptions copy(PolylineOptions p) {
        PolylineOptions copy = new PolylineOptions();
        if(p.path != null) {
            copy.path = new ArrayList<>(p.path);
        }
        copy.strokeColor(p.getStrokeColor())
            .clickable(p.isClickable())
            .draggable(p.isDraggable())
            .editable(p.isEditable())
            .visible(p.isVisible())
            .strokeOpacity(p.getStrokeOpacity())
            .strokeWeight(p.getStrokeWeight())
            .zIndex(p.getZIndex())
            .geodesic(p.isGeodesic());
        return copy;
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
        if(Platform.isFxApplicationThread()) {
            options = new com.lynden.gmapsfx.shapes.PolylineOptions();
            
            if(path != null) {
                LatLong[] ary = path.stream()
                    .map(ll -> new LatLong(ll.getLatitude(), ll.getLongitude()))
                    .collect(Collectors.toList())
                    .toArray(new LatLong[0]);
                MVCArray a = new MVCArray(ary);
                options.path(a);
                
                if(getStrokeColor() != null) {
                    options.strokeColor(getStrokeColor());
                }
                options.clickable(isClickable());
                options.draggable(isDraggable());
                options.editable(isEditable());
                options.visible(isVisible());
                options.strokeOpacity(getStrokeOpacity());
                options.strokeWeight(getStrokeWeight());
                options.zIndex(getZIndex());
                options.geodesic(isGeodesic());
            }
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
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
        PolylineOptions other = (PolylineOptions)obj;
        if(path == null) {
            if(other.path != null)
                return false;
        } else if(!path.equals(other.path))
            return false;
        return true;
    }
}

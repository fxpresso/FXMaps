package ai.cogmission.fxmaps.model;

import javafx.application.Platform;

/**
 * Common rendering options for all subclasses of {@link MapShape}.
 * 
 * @author cogmission
 *
 * @param <T>   the subclass of {@link MapShape}
 */
public abstract class MapShapeOptions<T extends MapShapeOptions<T>> {
    private boolean clickable;
    private boolean draggable;
    private boolean editable;
    private boolean geodesic;
    private String strokeColor;
    private double strokeOpacity;
    private double strokeWeight;
    private boolean visible;
    private int zIndex;
    
    @SuppressWarnings("rawtypes")
    public abstract com.lynden.gmapsfx.shapes.MapShapeOptions convert();
    
    
    @SuppressWarnings("unchecked")
    public T clickable(boolean clickable) {
        this.clickable = clickable;
        if(Platform.isFxApplicationThread()) {
            convert().clickable(clickable);
        }
        
        return (T)this;
    }
    
    @SuppressWarnings("unchecked")
    public T draggable(boolean draggable) {
        this.draggable = draggable;
        if(Platform.isFxApplicationThread()) {
            convert().draggable(draggable);
        }
        return (T)this;
    }
    
    @SuppressWarnings("unchecked")
    public T editable(boolean editable) {
        this.editable = editable;
        if(Platform.isFxApplicationThread()) {
            convert().editable(editable);
        }
        return (T)this;
    }
    
    @SuppressWarnings("unchecked")
    public T geodesic(boolean geodesic) {
        this.geodesic = geodesic;
        if(Platform.isFxApplicationThread()) {
            convert().geodesic(geodesic);
        }
        return (T)this;
    }
    
    @SuppressWarnings("unchecked")
    public T visible(boolean visible) {
        this.visible = visible;
        if(Platform.isFxApplicationThread()) {
            convert().visible(visible);
        }
        return (T)this;
    }
    
    @SuppressWarnings("unchecked")
    public T strokeOpacity(double strokeOpacity) {
        this.strokeOpacity = strokeOpacity;
        if(Platform.isFxApplicationThread()) {
            convert().strokeOpacity(strokeOpacity);
        }
        return (T)this;
    }
    
    @SuppressWarnings("unchecked")
    public T strokeWeight(double strokeWeight) {
        this.strokeWeight = strokeWeight;
        if(Platform.isFxApplicationThread()) {
            convert().strokeWeight(strokeWeight);
        }
        return (T)this;
    }
    
    @SuppressWarnings("unchecked")
    public T strokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
        if(Platform.isFxApplicationThread()) {
            convert().strokeColor(strokeColor);
        }
        return (T)this;
    }
    
    @SuppressWarnings("unchecked")
    public T zIndex(int zIndex) {
        this.zIndex = zIndex;
        if(Platform.isFxApplicationThread()) {
            convert().zIndex(zIndex);
        }
        return (T)this;
    }

    
    public boolean isClickable() {
        return clickable;
    }

    
    public boolean isDraggable() {
        return draggable;
    }

    
    public boolean isEditable() {
        return editable;
    }

    
    public boolean isGeodesic() {
        return geodesic;
    }

    
    public String getStrokeColor() {
        return strokeColor;
    }

    
    public double getStrokeOpacity() {
        return strokeOpacity;
    }

    
    public double getStrokeWeight() {
        return strokeWeight;
    }

    
    public boolean isVisible() {
        return visible;
    }

    
    public int getZIndex() {
        return zIndex;
    }
}

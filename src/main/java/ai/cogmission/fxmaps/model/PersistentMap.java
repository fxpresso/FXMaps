package ai.cogmission.fxmaps.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.UnmodifiableArrayList;

import ai.cogmission.fxmaps.ui.MapPane;
import javafx.application.Platform;

/**
 * The persistent form of a given map. Maps are essentially
 * a collection of {@link Route}s with some local settings.
 * 
 * @author cogmission
 * @see Map
 * @see MapOptions
 */
public class PersistentMap {
    protected List<Route> routes = new ArrayList<>();
    protected MapOptions mapOptions;
    protected String name;
    protected double width;
    protected double height;
    
    
    /**
     * Constructs a new {@code PersistentMap}
     * @param name  the name associated with this map
     */
    public PersistentMap(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of this map
     * @return  the name of this map
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of this map
     * @param name  the name of this map
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Adds the specified {@link Route} to this store.
     * @param r     the route to add
     */
    public void addRoute(Route r) {
        if(!routes.contains(r)) {
            routes.add(r);
        }
    }
    
    /**
     * Removes the specified {@link Route} from this store.
     * 
     * @param r     the route to remove
     */
    public void removeRoute(Route r) {
        routes.remove(r);
    }
    
    /**
     * Returns an {@link UnmodifiableArrayList} of routes
     * @return  an unmodifiable list of routes
     */
    public List<Route> getRoutes() {
        return Collections.unmodifiableList(routes);
    }
    
    /**
     * Returns the {@link Route} with the specified name or null 
     * if none exists with that name.
     * 
     * @param name  the name of the route to return
     * @return  the Route with the specified name
     */
    public Route getRoute(String name) {
        return routes.stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
    }
    
    /**
     * Sets this {@link PersistentMap}'s list of routes
     * @param routes    the list of routes contained in this map
     */
    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
    
    /**
     * Returns the desired width of the component to render this map
     * @return  the     desired display width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the desired width of the component to render this map
     * @param  width     desired display width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Returns the desired height of the component to render this map
     * @return  the desired display height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the desired height of the component to render this map
     * @param  height    desired display height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Returns the options used to render this map
     * @return  the options used to render this map
     */
    public MapOptions getMapOptions() {
        return mapOptions;
    }

    /**
     * Sets the options used to render this map.
     * @param mapOptions    the options used to render this map.
     */
    public void setMapOptions(MapOptions mapOptions) {
        this.mapOptions = mapOptions;
    }
    
    /**
     * Reifies all underlying JavaScript peers. This method is
     * called from the GPXPersistentMap load process.
     */
    public void createUnderlying() {
        if(mapOptions == null) {
            mapOptions = MapPane.getDefaultMapOptions();
        }
        
        // If deserializing in non-headless mode, build javascript peers
        if(Platform.isFxApplicationThread()) {
            for(Route r : routes) {
                if(r.origin == null || r.origin.getMarker() == null) {
                    continue;
                }
                
                r.origin.getMarker().createUnderlying();
                r.destination.getMarker().createUnderlying();
                for(Waypoint wp : r.observableDelegate) {
                    wp.getMarker().createUnderlying();
                }
                
                for(Polyline line : r.lines) {
                    line.createUnderlying();
                }
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((routes == null) ? 0 : routes.hashCode());
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
        PersistentMap other = (PersistentMap)obj;
        if(name == null) {
            if(other.name != null)
                return false;
        } else if(!name.equals(other.name))
            return false;
        if(routes == null) {
            if(other.routes != null)
                return false;
        } else if(!routes.equals(other.routes))
            return false;
        return true;
    }
     
}

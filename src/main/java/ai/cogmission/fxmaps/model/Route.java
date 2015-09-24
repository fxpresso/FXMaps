package ai.cogmission.fxmaps.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Abstraction of a given path which connects a series of {@link Waypoint}s.
 * 
 * @author cogmission
 * @see Waypoint
 */
public class Route {
    private ObservableList<Waypoint> delegate = FXCollections.observableArrayList();
    
    private String name;

    /** Constructs a new {@code Route} */
    public Route(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of this route.
     * @return name the name of this route.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Adds a {@link Waypoint} to this list
     * @param w
     */
    public void add(Waypoint w) {
        delegate.add(w);
    }
    
    /**
     * Removes the specified {@link Waypoint} from this list.
     * @param w
     */
    public void remove(Waypoint w) {
        delegate.remove(w);
    }
    
    /**
     * Removes all {@link Waypoint}s from this list.
     */
    public void removeAll() {
        delegate.removeAll();
    }
    
    /**
     * Returns the element at the specified index or 
     * throws an {@link IndexOutOfBoundsException}
     * 
     * @param index     the index of the Waypoint to return.
     * @return  the element at the specified index.
     * @throws  IndexOutOfBoundsException if index > size - 1
     */
    public Waypoint get(int index) {
        return delegate.get(index);
    }
    
    /**
     * Adds the specified {@link Waypoint} to this list at the
     * specified index.
     * 
     * @param index     the index to add the specified Waypoint
     * @param w         the Waypoint to add
     */
    public void add(int index, Waypoint w) {
        delegate.add(index, w);
    }
    
    /**
     * Adds the {@link ListChangeListener} which will be notified 
     * of changes to this list.
     * 
     * @param l     the listener to add
     */
    public void addListener(ListChangeListener<Waypoint> l) {
        delegate.addListener(l);
    }
    
    /**
     * Removes the specified listener.
     * 
     * @param l     the listener to remove
     */
    public void removeListener(ListChangeListener<Waypoint> l) {
        delegate.removeListener(l);
    }
    
    /**
     * Replaces the element at the specified index with the 
     * specified {@link Waypoint}.
     * 
     * @param index     the index to set
     * @param w         the Waypoint to set at the specified index.
     */
    public void set(int index, Waypoint w) {
        delegate.set(index, w);
    }
    
    /**
     * Returns the origin (starting) {@link Waypoint} or
     * null if this {@code Route} is empty. 
     * 
     * @return  the starting {@link Waypoint}
     */
    public Waypoint getOrigin() {
        if(delegate.size() < 1) return null;
        
        return delegate.get(0);
    }
    
    /**
     * Returns the destination (end point) {@link Waypoint} or
     * null if this {@code Route} is empty. Note, the returned
     * value may be the same as the origin if this route only 
     * contains 1 item.
     * 
     * @return  the end point {@link Waypoint}
     */
    public Waypoint getDestination() {
        if(delegate.size() < 1) return null;
        
        return delegate.get(delegate.size() - 1);
    }
    
    /**
     * Returns the interim {@link Waypoint}s of a given route. These
     * are defined as the waypoints which are not the origin nor 
     * destination waypoints.
     *  
     * @return
     */
    public List<Waypoint> getWaypoints() {
        if(delegate.size() < 3) return Collections.emptyList();
        
        List<Waypoint> retVal = new ArrayList<>(delegate);
        retVal.remove(0);
        retVal.remove(retVal.size() - 1);
        return retVal;
    }
    
    @Override
    public String toString() {
        return "Route [name=" + name + ", origin=" + getOrigin() + ", destination=" + getDestination() + 
                ", all=" + this + ", interim=" + getWaypoints() + "]";
    }
}

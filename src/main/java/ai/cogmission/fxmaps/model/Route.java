package ai.cogmission.fxmaps.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import com.google.gson.annotations.SerializedName;

/**
 * Abstraction of a given path which connects a series of {@link Waypoint}s.
 * 
 * @author cogmission
 * @see Waypoint
 */
public class Route {
    
    private ObservableList<Waypoint> observableDelegate = FXCollections.observableArrayList();
    
    @SerializedName("waypoints")
    private List<Waypoint> delegate = new ArrayList<>();
    
    private List<Polyline> lines = new ArrayList<>();
    
    private String name;
    private Waypoint origin;
    private Waypoint destination;

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
        if(observableDelegate.isEmpty()) {
            origin = w;
        }
        destination = w;
        observableDelegate.add(w);
    }
    
    /**
     * Removes the specified {@link Waypoint} from this list.
     * @param w
     */
    public void remove(Waypoint w) {
        observableDelegate.remove(w);
    }
    
    /**
     * Removes all {@link Waypoint}s from this list.
     */
    public void removeAll() {
        observableDelegate.clear();
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
        return observableDelegate.get(index);
    }
    
    /**
     * Adds the specified {@link Waypoint} to this list at the
     * specified index.
     * 
     * @param index     the index to add the specified Waypoint
     * @param w         the Waypoint to add
     */
    public void add(int index, Waypoint w) {
        observableDelegate.add(index, w);
    }
    
    /**
     * Adds the {@link ListChangeListener} which will be notified 
     * of changes to this list.
     * 
     * @param l     the listener to add
     */
    public void addListener(ListChangeListener<Waypoint> l) {
        observableDelegate.addListener(l);
    }
    
    /**
     * Removes the specified listener.
     * 
     * @param l     the listener to remove
     */
    public void removeListener(ListChangeListener<Waypoint> l) {
        observableDelegate.removeListener(l);
    }
    
    /**
     * Replaces the element at the specified index with the 
     * specified {@link Waypoint}.
     * 
     * @param index     the index to set
     * @param w         the Waypoint to set at the specified index.
     */
    public void set(int index, Waypoint w) {
        observableDelegate.set(index, w);
    }
    
    /**
     * Returns the origin (starting) {@link Waypoint} or
     * null if this {@code Route} is empty. 
     * 
     * @return  the starting {@link Waypoint}
     */
    public Waypoint getOrigin() {
        return origin;
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
        return destination;
    }
    
    /**
     * Returns all the {@link Waypoint}s of a given route.
     *  
     * @return  a list of all {@link Waypoint}s
     */
    public List<Waypoint> getWaypoints() {
        return observableDelegate;
    }
    
    /**
     * Adds a {@link Polyline} to this {@code Route}
     * @param line
     */
    public void addLine(Polyline line) {
        lines.add(line);
    }
    
    /**
     * Returns the list of {@link Polylines} which make up the legs
     * between {@link Waypoints}
     * @return
     */
    public List<Polyline> getLines() {
        return lines;
    }
    
    /**
     * Returns the interim {@link Waypoint}s of a given route. These
     * are defined as the waypoints which are not the origin nor 
     * destination waypoints.
     *  
     * @return  a list of {@link Waypoint}s minus the start and end
     */
    public List<Waypoint> getInterimWaypoints() {
        if(observableDelegate.size() < 3) return Collections.emptyList();
        
        List<Waypoint> retVal = new ArrayList<>(observableDelegate);
        retVal.remove(0);
        retVal.remove(retVal.size() - 1);
        return retVal;  
    }
    
    /**
     * Called prior to serialization to load the serializable data structure.
     */
    public void preSerialize() {
        delegate.addAll(observableDelegate);
    }
    
    /**
     * Called following deserialization to load the observable list. The observable
     * list cannot be serialized using the current method, so we use a simple list
     * for serialization and then copy the data over, after deserialization.
     */
    public void postDeserialize() {
        observableDelegate = FXCollections.observableArrayList(delegate);
        delegate.clear();
        
        // If deserializing in non-headless mode, build javascript peers
        if(Platform.isFxApplicationThread()) {
            origin.getMarker().createUnderlying();
            destination.getMarker().createUnderlying();
            for(Waypoint wp : observableDelegate) {
                wp.getMarker().createUnderlying();
            }
        }
    }
    
    @Override
    public String toString() {
        return "Route [name=" + name + ", origin=" + getOrigin() + ", destination=" + getDestination() + 
                ", interim=" + getInterimWaypoints() + "]";
    }
}

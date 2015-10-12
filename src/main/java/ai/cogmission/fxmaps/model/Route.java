package ai.cogmission.fxmaps.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.MalformedJsonException;

/**
 * Abstraction of a given path which connects a series of {@link Waypoint}s.
 * 
 * @author cogmission
 * @see Waypoint
 */
public class Route {
    
    protected ObservableList<Waypoint> observableDelegate = FXCollections.observableArrayList();
    
    @SerializedName("waypoints")
    protected List<Waypoint> delegate = new ArrayList<>();
    
    protected List<Polyline> lines = new ArrayList<>();
    
    protected String name;
    protected Waypoint origin;
    protected Waypoint destination;
    
    protected boolean interimMarkersVisible;
    
    protected String id;
    

    /** Constructs a new {@code Route} */
    public Route(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }
    
    /**
     * Returns the name of this route.
     * @return name the name of this route.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns this {@code Route}'s UUID in String form.
     * @return  this {@code Route}'s UUID in String form.
     */
    public String getId() {
        return id;
    }
    
    /**
     * Adds a {@link Waypoint} to this list
     * @param w
     */
    public void addWaypoint(Waypoint w) {
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
    public void removeWaypoint(Waypoint w) {
        observableDelegate.remove(w);
    }
    
    /**
     * Removes all {@link Waypoint}s from this list.
     */
    public void removeAllWaypoints() {
        observableDelegate.clear();
        lines.clear();
    }
    
    /**
     * Returns the element at the specified index or 
     * throws an {@link IndexOutOfBoundsException}
     * 
     * @param index     the index of the Waypoint to return.
     * @return  the element at the specified index.
     * @throws  IndexOutOfBoundsException if index > size - 1
     */
    public Waypoint getWaypoint(int index) {
        return observableDelegate.get(index);
    }
    
    /**
     * Adds the specified {@link Waypoint} to this list at the
     * specified index.
     * 
     * @param index     the index to add the specified Waypoint
     * @param w         the Waypoint to add
     */
    public void addWaypoint(int index, Waypoint w) {
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
    public void setWaypoint(int index, Waypoint w) {
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
     * @param line  the line to add
     */
    public void addLine(Polyline line) {
        lines.add(line);
    }
    
    /**
     * Removes the specified {@link Polyline} from this {@code Route}
     * @param line  the line to remove
     */
    public void removeLine(Polyline line) {
        lines.remove(line);
    }
    
    /**
     * Returns the list of {@link Polylines} which make up the legs
     * between {@link Waypoints}
     * @return  the list of lines
     */
    public List<Polyline> getLines() {
        return lines;
    }
    
    /**
     * Returns the number of {@link Waypoint}s in this {@code Route}
     * @return  the number of Waypoints
     */
    public int size() {
        return observableDelegate.size();
    }
    
    /**
     * Returns the interim {@link Waypoint}s of a given route. These
     * are defined as the waypoints which are not the origin nor 
     * destination waypoints.
     *  
     * @return  a list of {@link Waypoint}s minus the start and end
     */
    public List<Waypoint> getInterimWaypoints() {
        if(observableDelegate == null || observableDelegate.size() < 3) return Collections.emptyList();
        
        List<Waypoint> retVal = new ArrayList<>(observableDelegate);
        retVal.remove(0);
        retVal.remove(retVal.size() - 1);
        return retVal;  
    }
    
    public boolean getInterimMarkersVisible() {
        return interimMarkersVisible;
    }
    
    public void interimMarkersVisible(boolean b) {
        this.interimMarkersVisible = b;
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
    public void postDeserialize() throws MalformedJsonException {
        observableDelegate = FXCollections.observableArrayList(delegate);
        delegate.clear();
        
        // If deserializing in non-headless mode, build javascript peers
        if(Platform.isFxApplicationThread()) {
            if(origin == null || origin.getMarker() == null) {
                throw new MalformedJsonException("Route had malformed origin");
            }
            
            origin.getMarker().createUnderlying();
            destination.getMarker().createUnderlying();
            for(Waypoint wp : observableDelegate) {
                wp.getMarker().createUnderlying();
            }
            
            for(Polyline line : lines) {
                line.createUnderlying();
            }
        }
    }
    
    /**
     * Compares the specified {@code Route} with this Route using
     * only their paths.
     * 
     * @param other     the other route to compare
     * @return  true if both route paths are equal, false if not.
     */
    public boolean pathEquals(Route other) {
        if(observableDelegate == null) {
            if(other.observableDelegate != null)
                return false;
        } else if(!observableDelegate.equals(other.observableDelegate))
            return false;
        
        return true;
    }
    
    @Override
    public String toString() {
        return "Route [name=" + name + ", origin=" + getOrigin() + ", destination=" + getDestination() + 
                ", interim=" + getInterimWaypoints() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((destination == null) ? 0 : destination.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lines == null) ? 0 : lines.hashCode());
        result = prime * result + ((observableDelegate == null) ? 0 : observableDelegate.hashCode());
        result = prime * result + ((origin == null) ? 0 : origin.hashCode());
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
        Route other = (Route)obj;
        if(destination == null) {
            if(other.destination != null)
                return false;
        } else if(!destination.equals(other.destination))
            return false;
        if(id == null) {
            if(other.id != null)
                return false;
        } else if(!id.equals(other.id))
            return false;
        if(lines == null) {
            if(other.lines != null)
                return false;
        } else if(!lines.equals(other.lines))
            return false;
        if(observableDelegate == null) {
            if(other.observableDelegate != null)
                return false;
        } else if(!observableDelegate.equals(other.observableDelegate))
            return false;
        if(origin == null) {
            if(other.origin != null)
                return false;
        } else if(!origin.equals(other.origin))
            return false;
        return true;
    }

    
}

package ai.cogmission.fxmaps.model;

import java.util.EnumMap;

/**
 * This {@code MarkerType} enum contains functionality to "increment" the
 * next letter of the next {@link Marker} icon whose path will be returned.
 * Each marker color maintains its own letter index, so incrementing only 
 * occurs for that color marker.
 * 
 * @author cogmission
 *
 */
public enum MarkerType {
    BLUE("http://metaware.us/images/markers/blue_Marker"), 
    GREEN("http://metaware.us/images/markers/green_Marker"),
    RED("http://metaware.us/images/markers/red_Marker"),
    YELLOW("http://metaware.us/images/markers/yellow_Marker"),
    BROWN("http://metaware.us/images/markers/brown_Marker"),
    DARKGREEN("http://metaware.us/images/markers/darkgreen_Marker"),
    ORANGE("http://metaware.us/images/markers/orange_Marker"),
    PALEBLUE("http://metaware.us/images/markers/paleblue_Marker"),
    PINK("http://metaware.us/images/markers/pink_Marker"),
    PURPLE("http://metaware.us/images/markers/purple_Marker");
    
    private String path;
    
    private static EnumMap<MarkerType, Character> cursors;
    static {
        cursors = new EnumMap<MarkerType, Character>(MarkerType.class);
        for(MarkerType mt : values()) {
            cursors.put(mt, new Character((char)('A' - 1)));
        }
    }
    
    private MarkerType(String path) {
        this.path = path;
    }
    
    /**
     * Returns the current char indexed by this {@code MarkerType}
     * See the explanation for the "increment" functionality in the class
     * java doc.
     * 
     * @return  the currently indexed char for which a path is maintained
     * to its corresponding icon.
     */
    public char chr() {
        return cursors.get(this);
    }
    
    /**
     * Returns the next char in the sequence (from 'A' to 'Z') for a 
     * particular {@code MarkerType} (distinguished by color).
     * 
     * @return
     */
    public char next() {
        char retVal = cursors.get(this);
        retVal = retVal == 'Z' ? 'A' - 1 : retVal;
        retVal++;
        cursors.put(this, retVal);
        return retVal;
    }
    
    /**
     * This {@code MarkerType} enum contains functionality to "increment" the
     * next letter of the next {@link Marker} icon whose path will be returned.
     * Each marker color maintains its own letter index, so incrementing only 
     * occurs for that color marker.
     * </p><p>
     * This method resets the indexes for all marker colors
     */
    public static void reset() {
        for(MarkerType mt : values()) {
            cursors.put(mt, new Character((char)('A' - 1)));
        }
    }
    
    /**
     * Resets this {@code MarkerType}'s character index to the last one
     * in the specified {@link Route} so that new waypoints can continue
     * where the route left off.
     * 
     * @param r     the Route we're resetting to
     */
    public static void reset(Route r) {
        Waypoint dest = r.getDestination();
        String desc = dest.getMarker().getMarkerOptions().getIcon();
        MarkerType color = valueOf(desc.substring(desc.lastIndexOf("/") + 1, desc.indexOf("_")).toUpperCase());
        Character chr = desc.substring(desc.lastIndexOf(".") - 1, desc.lastIndexOf(".")).toUpperCase().charAt(0);
        cursors.put(color, chr);
    }
    
    public String iconPath() {
        if(chr() == 'A' - 1) next();
        
        return path.concat(Character.toString(chr()).concat(".png"));
    }
    
    public String nextPath() {
        return path.concat(Character.toString(next()).concat(".png"));
    }
}

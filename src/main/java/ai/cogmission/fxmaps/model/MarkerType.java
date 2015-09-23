package ai.cogmission.fxmaps.model;

import java.util.EnumMap;


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
    
    public char chr() {
        return cursors.get(this);
    }
    
    public char next() {
        char retVal = cursors.get(this);
        retVal = retVal == 'Z' ? 'A' - 1 : retVal;
        retVal++;
        cursors.put(this, retVal);
        return retVal;
    }
    
    public String iconPath() {
        if(chr() == 'A' - 1) next();
        
        String retVal = path.concat(Character.toString(chr()).concat(".png"));
        
        return retVal;
    }
    
    public String nextPath() {
        String retVal = path.concat(Character.toString(next()).concat(".png"));
        System.out.println("retVal = " + retVal);
        return retVal;
    }
}

package ai.cogmission.fxmaps.model;


/**
 * You may specify the transportation mode to use for calculating directions. Directions are
 * calculating as driving directions by default.
 *
 * @see <a href="https://developers.google
 * .com/maps/documentation/directions/#TravelModes">Directions API travel modes</a>
 * @see <a href="https://developers.google
 * .com/maps/documentation/distancematrix/#RequestParameters">Distance Matrix API travel modes</a>
 * 
 * @author cogmission
 */
public enum TravelMode {
    DRIVING(com.google.maps.model.TravelMode.DRIVING), 
    WALKING(com.google.maps.model.TravelMode.WALKING), 
    BICYCLING(com.google.maps.model.TravelMode.BICYCLING), 
    TRANSIT(com.google.maps.model.TravelMode.TRANSIT),
    UNKNOWN(com.google.maps.model.TravelMode.UNKNOWN);
    
    private com.google.maps.model.TravelMode googTravelMode;
    
    private TravelMode(com.google.maps.model.TravelMode mode) {
        this.googTravelMode = mode;
    }
    
    @Override
    public String toString() {
        return googTravelMode.toString();
    }
    
    public String toUrlValue() {
        return googTravelMode.toUrlValue();
    }
    
    public com.google.maps.model.TravelMode convert() {
        return googTravelMode;
    }
}

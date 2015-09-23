package ai.cogmission.fxmaps.model;

/**
 * The stop/station
 * 
 * <p>See <a href="https://developers.google.com/maps/documentation/directions/#TransitDetails">
 * Transit details</a> for more detail.
 *  
 * @author cogmission
 */
public class StopDetails {
    com.google.maps.model.StopDetails stopDetails;
    
    public StopDetails(com.google.maps.model.StopDetails stopDetails) {
        this.stopDetails = stopDetails;
    }
    
    /**
     * The name of the transit station/stop. eg. "Union Square".
     */
    public String getName() {
        return stopDetails.name;
    }
    
    /**
     * The name of the transit station/stop. eg. "Union Square".
     */
    public LatLon getLocations() {
        return new LatLon(stopDetails.location.lat, stopDetails.location.lng);
    }
}

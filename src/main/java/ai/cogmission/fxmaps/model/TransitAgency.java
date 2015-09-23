package ai.cogmission.fxmaps.model;

/**
 * The operator of a line.
 *
 * <p>See <a href="https://developers.google.com/maps/documentation/directions/#TransitDetails">
 * Transit details</a> for more detail.
 * 
 * @author cogmission
 */
public class TransitAgency {
    private com.google.maps.model.TransitAgency transitAgency;
    
    public TransitAgency(com.google.maps.model.TransitAgency agency) {
        this.transitAgency = agency;
    }
    
    /**
     * {@code name} contains the name of the transit agency.
     */
    public String getName() {
        return transitAgency.name;
    }
    
    /**
     * {@code url} contains the URL for the transit agency.
     */
    public String getUrl() {
        return transitAgency.url;
    }
    
    /**
     * {@code phone} contains the phone number of the transit agency.
     */
    public String getPhone() {
        return transitAgency.phone;
    }
}

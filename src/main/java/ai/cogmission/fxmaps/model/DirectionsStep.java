package ai.cogmission.fxmaps.model;

import java.util.Arrays;


public class DirectionsStep {
    private com.google.maps.model.DirectionsStep step;
    private Distance distance;
    private Duration duration;
    private LatLon startLocation;
    private LatLon endLocation;
    
    /**
     * Constructs a new {@code DirectionsStep}
     * @param step
     */
    public DirectionsStep(com.google.maps.model.DirectionsStep step) {
        this.step = step;
    }
    
    /**
     * Returns the formatted instructions for this step.
     * 
     * @return  the formatted instructions for this step.
     */
    public String getHtmlInstructions() {
        return step.htmlInstructions;
    }
    
    /**
     * Returns the distance covered by this step
     * @return  the distance covered by this step
     */
    public Distance getDistance() {
        return distance;
    }
    
    /**
     * Returns the typical time required to perform this step.
     * @return  the typical time required to perform this step.
     */
    public Duration getDuration() {
        return duration;
    }
    
    /**
     * Returns the location starting point of this step.
     * @return
     */
    public LatLon getStartLocation() {
        return startLocation;
    }
    
    /**
     * Returns the location end point of this step.
     * @return
     */
    public LatLon getEndLocation() {
        return endLocation;
    }
    
    /**
     * detailed directions for walking or driving steps in transit
     * directions. Substeps are only available when travelMode is set to "transit".
     * 
     * @return  the polyline
     */
    public EncodedPolyline getPolyline() {
        return new EncodedPolyline(step.polyline);
    }
    
    /**
     * Returns the travel mode of this step. See
     * <a href="https://developers.google.com/maps/documentation/directions/#TravelModes">Travel
     * Modes</a> for more detail.
     * 
     * @return  the travel mode of this step.
     */
    public TravelMode getTravelMode() {
        return Arrays.stream(TravelMode.values())
            .filter(n -> n.name().equals(step.travelMode.name()))
            .findFirst()
            .get();
    }
    
    /**
     * Returns transit specific information. This field is only returned with
     * travel_mode is set to "transit".
     * See <a href="https://developers.google.com/maps/documentation/directions/#TransitDetails">
     * Transit Details</a> for more detail.
     * 
     * @return  transit specific information
     */
    public TransitDetails getTransitDetails() {
        return new TransitDetails(step.transitDetails);
    }
}

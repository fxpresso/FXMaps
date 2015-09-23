package ai.cogmission.fxmaps.model;

import org.joda.time.DateTime;


public class TransitDetails {
    private com.google.maps.model.TransitDetails details;
    
    public TransitDetails(com.google.maps.model.TransitDetails details) {
        this.details = details;
    }
    
    public StopDetails getArrivalStop() {
        return new StopDetails(details.arrivalStop);
    }
    
    public StopDetails getDepartureStop() {
        return new StopDetails(details.departureStop);
    }
    
    public DateTime getArrivalTime() {
        return details.arrivalTime;
    }
    
    public DateTime getDepartureTime() {
        return details.departureTime;
    }
    
    public String getHeadSign() {
        return details.headsign;
    }
    
    /**
     * Returns the expected number of seconds between departures from the same stop
     * at this time. For example, with a headway value of 600, you would expect a ten minute wait if
     * you should miss your bus.
     * 
     * @return the expected number of seconds between departures from the same stop
     */
    public long getHeadway() {
        return details.headway;
    }
    
    /**
     * Returns the number of stops in this step, counting the arrival stop, but not
     * the departure stop. For example, if your directions involve leaving from Stop A, passing
     * through stops B and C, and arriving at stop D, {@code numStops} will return 3.
     * 
     * @return the number of stops
     */
    public int getNumStops() {
        return details.numStops;
    }
    
    /**
     * Returns information about the transit line used in this step.
     * 
     * @return information about the transit line used in this step.
     */
    public TransitLine getTransitLine() {
        return new TransitLine(details.line);
    }
}

package ai.cogmission.fxmaps.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;


public class DirectionsLeg {
    private com.google.maps.model.DirectionsLeg leg;
    
    public DirectionsLeg(com.google.maps.model.DirectionsLeg leg) {
        this.leg = leg;
    }
    
    /**
     * Returns an array of steps denoting information about each separate step of the
     * leg of the journey.
     * 
     * @return  an array of steps
     */
    public List<DirectionsStep> getSteps() {
        return Arrays.stream(leg.steps)
            .map(s -> { return new DirectionsStep(s); })
            .collect(Collectors.toList());
    }
    
    /**
     * Returns the total distance covered by this leg.
     * @return  the total distance covered by this leg.
     */
    public Distance getDistance() {
        return new Distance(leg.distance);
    }
    
    /**
     * Returns the total duration of this leg
     * @return  the total duration of this leg
     */
    public Duration getDuration() {
        return new Duration(leg.duration);
    }
    
    /**
     * Returns the total duration of this leg, taking into account current
     * traffic conditions. The duration in traffic will only be returned if all of the following are
     * true:
     * <ol>
     *   <li>The directions request includes a departureTime parameter set to a value within a few
     *       minutes of the current time.</li>
     *   <li>The request includes a valid Maps for Work client and signature parameter.</li>
     *   <li>Traffic conditions are available for the requested route.</li>
     *   <li>The directions request does not include stopover waypoints.</li>
     * </ol>
     * 
     * @return the total duration of this leg, taking into account current
     * traffic conditions.
     */
    public Duration getDurationInTraffic() {
        return new Duration(leg.durationInTraffic);
    }
    
    /**
     * Returns the estimated time of arrival for this leg. This property is only
     * returned for transit directions.
     * 
     * @return  the estimated time of arrival
     */
    public DateTime getArrivalTime() {
        return leg.arrivalTime;
    }
    
    /**
     * Returns the estimated time of departure for this leg. The departureTime
     * is only available for transit directions.
     * 
     * @return  the estimated time of departure
     */
    public DateTime getDepartureTime() {
        return leg.departureTime;
    }
    
    /**
     * Returns the latitude/longitude coordinates of the origin of this leg.
     * Because the Directions API calculates directions between locations by using the nearest
     * transportation option (usually a road) at the start and end points, startLocation may be
     * different than the provided origin of this leg if, for example, a road is not near the origin.
     * 
     * @return  the start latitude/longitude
     */
    public LatLon getStartLocation() {
        return new LatLon(leg.startLocation.lat, leg.startLocation.lng);
    }
    
    /**
     * Returns the latitude/longitude coordinates of the given destination of
     * this leg. Because the Directions API calculates directions between locations by using the
     * nearest transportation option (usually a road) at the start and end points, endLocation may be
     * different than the provided destination of this leg if, for example, a road is not near the
     * destination.
     * 
     * @return  the end latitude/longitude
     */
    public LatLon getEndLocation() {
        return new LatLon(leg.endLocation.lat, leg.endLocation.lng);
    }
    
    /**
     * Returns the human-readable address (typically a street address) reflecting
     * the start location of this leg.
     * 
     * @return  the human-readable start address 
     */
    public String getStartAddress() {
        return leg.startAddress;
    }
    
    /**
     * Returns the human-readable address (typically a street address) reflecting
     * the end location of this leg.
     * 
     * @return  the human-readable end address
     */
    public String getEndAddress() {
        return leg.endAddress;
    }
}

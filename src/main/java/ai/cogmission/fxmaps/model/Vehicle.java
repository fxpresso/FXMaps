package ai.cogmission.fxmaps.model;

import java.util.Arrays;

/**
 * The vehicle used on a line.
 * <p/>
 * <p>See <a href="https://developers.google.com/maps/documentation/directions/#TransitDetails">
 * Transit details</a> for more detail.
 * 
 * @author cogmission
 */
public class Vehicle {
    private com.google.maps.model.Vehicle vehicle;
    
    public Vehicle(com.google.maps.model.Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public String getName() {
        return vehicle.name;
    }
    
    /**
     * {@code type} contains the type of vehicle that runs on this line. See the
     * {@link com.google.maps.model.VehicleType VehicleType} documentation for a complete list of
     * supported values.
     */
    public VehicleType getType() {
        return Arrays.stream(VehicleType.values())
            .filter(n -> n.name().equals(vehicle.type.name()))
            .findFirst()
            .get();
    }
    
    /**
     * {@code icon} contains the URL for an icon associated with this vehicle type.
     */
    public String getIcon() {
        return vehicle.icon;
    }
}

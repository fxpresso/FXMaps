package ai.cogmission.fxmaps.model;

import java.util.Arrays;
import java.util.stream.Collectors;


public class TransitLine {
    private com.google.maps.model.TransitLine transitLine;
    
    public TransitLine(com.google.maps.model.TransitLine line) {
        this.transitLine = line;
    }
    
    public String getName() {
        return transitLine.name;
    }
    
    public String getShortName() {
        return transitLine.shortName;
    }
    
    public String getColor() {
        return transitLine.color;
    }
    
    public TransitAgency[] getAgencies() {
        return Arrays.stream(transitLine.agencies)
            .map(a -> { return new TransitAgency(a); })
            .collect(Collectors.toList()).toArray(new TransitAgency[0]);
    }
    
    public String getUrl() {
        return transitLine.url;
    }
    
    public String getIcon() {
        return transitLine.icon;
    }
    
    public String getTextColor() {
        return transitLine.textColor;
    }
    
    public Vehicle getVehicle() {
        return new Vehicle(transitLine.vehicle);
    }
}

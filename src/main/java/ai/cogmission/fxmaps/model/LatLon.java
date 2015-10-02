package ai.cogmission.fxmaps.model;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;

import com.google.maps.model.LatLng;
import com.lynden.gmapsfx.javascript.object.LatLong;

/**
 * Container for latitude and longitude information.
 * @author cogmission
 *
 */
public class LatLon {
    private double latitude;
    private double longitude;
    
    private LatLong latLong;
    
    
    
    /**
     * Constructs a new {@code LatLon}, specifying whether this object
     * is being created in "test mode" (i.e. without a {@link WebEngine})
     * 
     * @param latitude      the latitude
     * @param longitude     the longitude
     */
    public LatLon(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        if(Platform.isFxApplicationThread()) {
            this.latLong = new com.lynden.gmapsfx.javascript.object.LatLong(latitude, longitude);
        }
    }
    
    /**
     * Returns the string identifier used to reference the
     * javascript object entity.
     * 
     * @return the variable name string identifier
     */
    public String getVariableName() {
        if(latLong == null && Platform.isFxApplicationThread()) {
            latLong = new com.lynden.gmapsfx.javascript.object.LatLong(latitude, longitude);
            return latLong.getVariableName();
        }
        return "None";
    }
    
    /**
     * Returns the latitude 
     * @return  the latitude (double)
     */
    public double getLatitude() {
        return latitude;
    }
    
    /**
     * Returns the longitude (double)
     * @return  the longitude (double)
     */
    public double getLongitude() {
        return longitude;
    }
    
    /**
     * Used internally to convert from this to a GMapsFX
     * model.
     * 
     * @return  the GMapsFX version of a lat long.
     */
    public LatLong toLatLong() {
        return latLong;
    }
    
    /**
     * Used internally to convert from this to a Google Maps
     * Services model.
     * 
     * @return  the Google Maps Service version of a lat long.
     */
    public LatLng toLatLng() {
        return new LatLng(latitude, longitude);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = prime * result + (int)(temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = prime * result + (int)(temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        LatLon other = (LatLon)obj;
        if(Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
            return false;
        if(Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
            return false;
        return true;
    }
}
